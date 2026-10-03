package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionUp;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

public class ShooterCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private final ShooterSubsystem shooterSubsystem;

    public ShooterCommand(IntakeSubsystem intakeSubsystem, ShooterSubsystem shooterSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.shooterSubsystem = shooterSubsystem;

        addRequirements(intakeSubsystem, shooterSubsystem);
    }

    @Override
    public void initialize() {
        intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionDown);
        intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionDown);
        intakeSubsystem.setMotorPower(intakeMotorPower);
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionUp);
        intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionDown);
        intakeSubsystem.setMotorPower(0);
    }
}
