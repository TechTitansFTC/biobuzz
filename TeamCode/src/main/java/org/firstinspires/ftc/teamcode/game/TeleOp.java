package org.firstinspires.ftc.teamcode.game;

import static org.firstinspires.ftc.teamcode.util.Constants.controlHubLogoFacingDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.controlHubUSBFacingDirection;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.LimelightAlignCommand;
import org.firstinspires.ftc.teamcode.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveSubsystem;

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp Main")
public class TeleOp extends OpMode {
    MecanumDriveSubsystem mecanumDriveSubsystem;
    LimelightSubsystem limelightSubsystem;
    DriveCommand driveCommand;
    LimelightAlignCommand limelightAlignCommand;
    IMU imu;
    GamepadEx gamepadEx;
    @Override
    public void init() {
        // Objects
        imu = hardwareMap.get(IMU.class,"imu");
        new RevHubOrientationOnRobot(controlHubLogoFacingDirection, controlHubUSBFacingDirection);
        gamepadEx = new GamepadEx(gamepad1);

        // Subsystems
        mecanumDriveSubsystem = new MecanumDriveSubsystem(hardwareMap,imu,telemetry);
        limelightSubsystem = new LimelightSubsystem(hardwareMap, telemetry);

        // Commands
        driveCommand = new DriveCommand(gamepadEx,mecanumDriveSubsystem);
        limelightAlignCommand = new LimelightAlignCommand(mecanumDriveSubsystem, limelightSubsystem);

        // Bindins
        mecanumDriveSubsystem.setDefaultCommand(driveCommand);
        gamepadEx.getGamepadButton(GamepadKeys.Button.A).whenPressed(limelightAlignCommand);
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> mecanumDriveSubsystem.resetIMU());
    }

    @Override
    public void loop () {
        CommandScheduler.getInstance().run();
        gamepadEx.readButtons();
    }

    @Override
    public void stop () {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().reset();
    }
}
