package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionUp;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeFloorCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;

    public IntakeFloorCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;

        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        intakeSubsystem.setMotorPower(intakeMotorPower);
        intakeSubsystem.setFlowerPivotPosition(intakeFlowerPivotPositionUp);
        intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionUp);
        intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionUp);
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setMotorPower(0);
    }
}
