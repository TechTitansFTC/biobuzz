package org.firstinspires.ftc.teamcode.util;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import javax.tools.ForwardingFileObject;

@Configurable
public class Constants {
    // Drive
    public static RevHubOrientationOnRobot.LogoFacingDirection controlHubLogoFacingDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static RevHubOrientationOnRobot.UsbFacingDirection controlHubUSBFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.LEFT;

    // Intake
    public static Servo.Direction intakeWheelPivotServoLeftDirection = Servo.Direction.FORWARD;
    public static Servo.Direction intakeWheelPivotServoRightDirection = Servo.Direction.REVERSE;
    public static Servo.Direction intakeRampPivotServoLeftDirection = Servo.Direction.FORWARD;
    public static Servo.Direction intakeRampPivotServoRightDirection = Servo.Direction.REVERSE;
    public static Servo.Direction intakeFlowerPivotServoLeftDirection = Servo.Direction.FORWARD;
    public static Servo.Direction intakeFlowerPivotServoRightDirection = Servo.Direction.REVERSE;
    public static DcMotor.Direction intakeMotorDirection = DcMotor.Direction.REVERSE;

    public static double intakeWheelPivotPositionOpen = 0.3;
    public static double intakeWheelPivotPositionClosed = 0.4;
//    public static double intake

}
