package org.firstinspires.ftc.teamcode.game;

import static org.firstinspires.ftc.teamcode.util.Constants.controlHubLogoFacingDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.controlHubUSBFacingDirection;

import com.bylazar.telemetry.JoinedTelemetry;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.internal.network.RobotCoreCommandList;
import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeFloorCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeFlowerCommand;
import org.firstinspires.ftc.teamcode.commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp Main")
public class TeleOp extends OpMode {
    MecanumDriveSubsystem mecanumDriveSubsystem;
    IntakeSubsystem intakeSubsystem;
    ShooterSubsystem shooterSubsystem;
    DriveCommand driveCommand;
    IntakeFloorCommand intakeFloorCommand; // intakes from garden/floor with no flower pivot
    IntakeFlowerCommand intakeFlowerCommand; // intakes from flower with flower pivot
    Command toggleFlywheelCommand;
    ShooterCommand shooterCommand;
    IMU imu;
    GamepadEx gamepadEx;

    @Override
    public void init () {
        // Send telemetry to both the Driver Hub and Panels (so values can be graphed while tuning)
        telemetry = new JoinedTelemetry(PanelsTelemetry.INSTANCE.getFtcTelemetry(), telemetry);

        // Objects
        imu = hardwareMap.get(IMU.class,"imu");
        imu.initialize(new IMU.Parameters(new RevHubOrientationOnRobot(controlHubLogoFacingDirection, controlHubUSBFacingDirection)));
        gamepadEx = new GamepadEx(gamepad1);

        // Subsystems
        mecanumDriveSubsystem = new MecanumDriveSubsystem(hardwareMap,imu,telemetry);
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry);

        // Commands
        driveCommand = new DriveCommand(gamepadEx,mecanumDriveSubsystem);
        intakeFloorCommand = new IntakeFloorCommand(intakeSubsystem);
        intakeFlowerCommand = new IntakeFlowerCommand(intakeSubsystem);
        shooterCommand = new ShooterCommand(intakeSubsystem, shooterSubsystem);
        toggleFlywheelCommand = ShooterCommand.toggleFlywheel(shooterSubsystem);

        // Bindings
        mecanumDriveSubsystem.setDefaultCommand(driveCommand);
        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                        .whenPressed(toggleFlywheelCommand);
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> mecanumDriveSubsystem.resetIMU());
        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                        .whenHeld(intakeFloorCommand);
        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenHeld(intakeFlowerCommand);
        gamepadEx.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(shooterCommand);

    }

    @Override
    public void loop () {
        shooterSubsystem.setIdle(intakeFloorCommand.isScheduled() || intakeFlowerCommand.isScheduled());
        CommandScheduler.getInstance().run();
        telemetry.update();
        gamepadEx.readButtons();
    }

    @Override
    public void stop () {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().reset();
    }
}
