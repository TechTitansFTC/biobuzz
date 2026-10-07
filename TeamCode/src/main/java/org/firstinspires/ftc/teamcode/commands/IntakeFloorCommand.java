package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeChangeTime;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeReset;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionUp;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.util.Constants;

public class IntakeFloorCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private final ElapsedTime intakeTimer;

    public IntakeFloorCommand(IntakeSubsystem intakeSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.intakeTimer = new ElapsedTime();

        addRequirements(intakeSubsystem);
    }

    @Override
    public void initialize() {
        if (!intakeReset) {
            intakeSubsystem.setFlowerPivotPosition(intakeFlowerPivotPositionUp);
            intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionUp);
            intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionUp);
            intakeTimer.reset();
        }
    }

    @Override
    public void execute() {
        if (intakeReset) {
            intakeSubsystem.setMotorPower(intakeMotorPower);
            return;
        }
        if (intakeTimer.seconds() >= intakeChangeTime) {
            Constants.intakeReset = true;
        }
    }

    @Override
    public void end(boolean interrupted) {
        intakeSubsystem.setMotorPower(0);
    }
}
