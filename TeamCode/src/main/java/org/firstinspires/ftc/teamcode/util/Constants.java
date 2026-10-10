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
    public static DcMotor.Direction intakeMotorDirection = DcMotor.Direction.FORWARD;

    public static double intakeWheelPivotPositionUp = 0.4;
    public static double intakeWheelPivotPositionDown = 0.3;
    public static double intakeRampPivotPositionUp = 0.5;
    public static double intakeRampPivotPositionDown = 0.9;
    public static double intakeFlowerPivotPositionUp = 0.62;
    public static double intakeFlowerPivotPositionDown = 0.4;
    public static double intakeMotorPower = 0.8;
    public static boolean intakeReset = false;
    public static double intakeChangeTime = 0.3;

    // Shooter
    public static DcMotor.Direction shooterMotorLeftDirection = DcMotor.Direction.REVERSE;
    public static DcMotor.Direction shooterMotorRightDirection = DcMotorSimple.Direction.FORWARD;
    public static double shooterTicksPerRev = 28;
    public static double shooterGearRatio = 1.5;
    public static double transferWaitTime = 0.7;

    public static double shooterTargetRpm = 4000;
    public static double shooterIdleRpm = 1000;
    public static double shooterRpmTolerance = 100;
    public static double shooterSpinUpTimeout = 2.0;
    public static double shooterBangBangThreshold = 300;

    public static double shooterkS = 0.0;     //TODO: Tune using panels - volts to overcome friction
    public static double shooterkV = 0.0028;  //TODO: Tune using panels - volts per RPM (theoretical start, tune first)
    public static double shooterkP = 0.0;     //TODO: Tune using panels - volts per RPM of error
}
