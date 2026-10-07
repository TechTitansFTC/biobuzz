package org.firstinspires.ftc.teamcode.util;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Configurable
public class Constants {

    // Define the different modes for testing intake components
    public enum TestingMode {
        IDLE,
        WHEEL_PIVOTS,
        RAMP_PIVOTS,
        FLOWER_PIVOTS,
        INTAKE_MOTOR
    }

    // This variable determines which mechanism the OpMode currently controls
    public static TestingMode activeTestingMode = TestingMode.IDLE;

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

    public static double intakeWheelPivotPositionUp = 0.3;
    public static double intakeWheelPivotPositionDown = 0.4;
    public static double intakeRampPivotPositionUp = 0.3;
    public static double intakeRampPivotPositionDown = 0.4;
    public static double intakeFlowerPivotPositionUp = 0.3;
    public static double intakeFlowerPivotPositionDown = 0.4;
    public static double intakeMotorPower = 0.5;

    // Shooter
    public static DcMotor.Direction shooterMotorLeftDirection = DcMotor.Direction.FORWARD;
    public static DcMotor.Direction shooterMotorRightDirection = DcMotorSimple.Direction.REVERSE;
    public static double shootingMotorPower = 0.6;
}