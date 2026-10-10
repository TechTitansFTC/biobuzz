package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeChangeTime;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.shooterSpinUpTimeout;
import static org.firstinspires.ftc.teamcode.util.Constants.transferWaitTime;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.command.InstantCommand;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.util.Constants;

public class ShooterCommand extends CommandBase {
    public static Command toggleFlywheel(ShooterSubsystem shooterSubsystem) {
        return new InstantCommand(shooterSubsystem::toggle, shooterSubsystem);
    }

    private final IntakeSubsystem intakeSubsystem;
    private final ShooterSubsystem shooterSubsystem;
    private enum ShootingCommandRoutine {setIntakePositions, shooterMotor, transfer, end}
    private ShootingCommandRoutine currentState;
    private final ElapsedTime shooterTimer;

    public ShooterCommand(IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.shooterSubsystem = shooterSubsystem;
        this.shooterTimer = new ElapsedTime();

        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        currentState = ShootingCommandRoutine.setIntakePositions;
        shooterTimer.reset();
    }

    @Override
    public void execute() {
        switch (currentState) {
            case setIntakePositions:
                intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionDown);
                intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionDown);
                if (shooterTimer.seconds() >= intakeChangeTime) {
                    currentState = ShootingCommandRoutine.shooterMotor;
                    shooterTimer.reset();
                }
                break;
            case shooterMotor:
                if (!shooterSubsystem.isEnabled()) {
                    currentState = ShootingCommandRoutine.end; // flywheel toggled off with B, don't feed
                } else if (shooterSubsystem.atSpeed() || shooterTimer.seconds() >= shooterSpinUpTimeout) {
                    currentState = ShootingCommandRoutine.transfer;
                    shooterTimer.reset();
                }
                break;
            case transfer:
                intakeSubsystem.setMotorPower(intakeMotorPower);
                if (shooterTimer.seconds() >= transferWaitTime) {
                    currentState = ShootingCommandRoutine.end;
                    shooterTimer.reset();
                }
                break;
            case end:
                intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionUp);
                intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionUp);
                intakeSubsystem.setMotorPower(0);
                Constants.intakeReset = true;
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return currentState == ShootingCommandRoutine.end;
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionUp);
        intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionUp);
        intakeSubsystem.setMotorPower(0);
        Constants.intakeReset = true;
    }
}
