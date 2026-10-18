package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.limelightAlignD;
import static org.firstinspires.ftc.teamcode.util.Constants.limelightAlignI;
import static org.firstinspires.ftc.teamcode.util.Constants.limelightAlignP;
import static org.firstinspires.ftc.teamcode.util.Constants.slowModeFactor;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.util.PIDController;

public class DriveCommand extends CommandBase {
    private final MecanumDriveSubsystem drive;
    private final GamepadEx gamepad;
    String team;
    private boolean fieldCentric;
    private final PIDController alignController;
    private final ElapsedTime integralTimer;
    private double previousTime;
    private final LimelightSubsystem limelightSubsystem;
    public DriveCommand(GamepadEx gamepad, MecanumDriveSubsystem drive, LimelightSubsystem limelightSubsystem) {
        this.drive = drive;
        this.gamepad = gamepad;
        this.limelightSubsystem = limelightSubsystem;
        fieldCentric = true;
        this.alignController = new PIDController(limelightAlignP, limelightAlignI, limelightAlignD);
        integralTimer = new ElapsedTime();
        previousTime = integralTimer.seconds();
        addRequirements(drive);
    }

    @Override
    public void initialize() {
        integralTimer.reset();
        alignController.setTarget(0);
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

        if (gamepad.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER) >= 0.5) {
            if (!limelightSubsystem.hasTarget()) return;
            double current = limelightSubsystem.getTx();
            double correctionPower = alignController.calculateOutput(current, integralTimer.seconds() - previousTime);
            previousTime = integralTimer.seconds();
            rotation = correctionPower;
        }

        if (gamepad.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) >= 0.5) {
            x *= slowModeFactor;
            y *= slowModeFactor;
            rotation *= slowModeFactor;
        }

        if (fieldCentric) {
            drive.fieldCentricDrive(x, y, rotation);
            return;
        }

        drive.robotCentricDrive(x, y, rotation);

    }

    @Override
    public void end(boolean interrupted) { drive.robotCentricDrive(0, 0, 0); }
}
