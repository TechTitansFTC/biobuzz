package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorPower;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

public class IntakeFlowerCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;

    public IntakeFlowerCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;

        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        intakeSubsystem.setMotorPower(intakeMotorPower);
        intakeSubsystem.setFlowerPivotPosition(intakeFlowerPivotPositionDown);
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setMotorPower(0);
        intakeSubsystem.setFlowerPivotPosition(intakeFlowerPivotPositionUp);
    }
}
