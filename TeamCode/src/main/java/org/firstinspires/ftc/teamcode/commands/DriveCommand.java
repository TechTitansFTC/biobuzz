package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveSubsystem;

public class DriveCommand extends CommandBase {
    private final MecanumDriveSubsystem drive;
    private final GamepadEx gamepad;
    String team;
    private boolean fieldCentric;
    public DriveCommand(GamepadEx gamepad, MecanumDriveSubsystem drive) {
        this.drive = drive;
        this.gamepad = gamepad;
        fieldCentric = true;
        addRequirements(drive);
    }

    @Override
    public void execute() {
        double x = gamepad.getLeftX();
        double y = gamepad.getLeftY();
        double rotation = gamepad.getRightX();

        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                        .whenPressed(() -> fieldCentric = false);
        gamepad.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> fieldCentric = true);

        if (fieldCentric) {
            drive.fieldCentricDrive(x, y, rotation);
            return;
        }
        drive.robotCentricDrive(x, y, rotation);
    }

    @Override
    public void end(boolean interrupted) { drive.robotCentricDrive(0, 0, 0); }
}
