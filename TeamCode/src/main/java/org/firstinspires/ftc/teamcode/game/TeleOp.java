package org.firstinspires.ftc.teamcode.game;

import static org.firstinspires.ftc.teamcode.util.Constants.controlHubLogoFacingDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.controlHubUSBFacingDirection;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.Commands.DriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveSubsystem;

public class TeleOp extends OpMode {
    MecanumDriveSubsystem mecanumDriveSubsystem;
    DriveCommand driveCommand;
    IMU imu;
    GamepadEx gamepadEx;

    @Override
    public void init () {
        imu = hardwareMap.get(IMU.class,"imu");
        new RevHubOrientationOnRobot(controlHubLogoFacingDirection, controlHubUSBFacingDirection);
        gamepadEx = new GamepadEx(gamepad1);
        mecanumDriveSubsystem = new MecanumDriveSubsystem(hardwareMap,imu,telemetry);
        driveCommand = new DriveCommand(gamepadEx,mecanumDriveSubsystem);
        mecanumDriveSubsystem.setDefaultCommand(driveCommand);
    }

    @Override
    public void loop () {
        CommandScheduler.getInstance().run();
        gamepadEx.readButtons();
        telemetry.update();
    }

    @Override
    public void stop () {
        CommandScheduler.getInstance().cancelAll();
        CommandScheduler.getInstance().reset();
    }
}
