package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeChangeTime;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.shooterMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.shooterMotorWaitTime;
import static org.firstinspires.ftc.teamcode.util.Constants.transferWaitTime;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.util.Constants;

public class ShooterCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private enum ShootingCommandRoutine {setIntakePositions, shooterMotor, transfer, end}
    private ShootingCommandRoutine currentState;
    private final ElapsedTime shooterTimer;

    public ShooterCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
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
                intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionDown);
                intakeSubsystem.setMotorPower(0);
                Constants.intakeReset = false;
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
        intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionDown);
        intakeSubsystem.setMotorPower(0);
        Constants.intakeReset = false;
    }
}
