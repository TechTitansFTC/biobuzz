package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.reverseIntakeMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.reverseIntakeMotorTime;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class ReverseIntakeCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private final ElapsedTime reverseIntakeTime;

    public ReverseIntakeCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        reverseIntakeTime = new ElapsedTime();
    }

    @Override
    public void initialize() {
        reverseIntakeTime.reset();
        intakeSubsystem.setMotorPower(reverseIntakeMotorPower);
    }

    @Override
    public boolean isFinished() {
        return reverseIntakeTime.seconds() >= reverseIntakeMotorTime;
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setMotorPower(0);
    }
}
