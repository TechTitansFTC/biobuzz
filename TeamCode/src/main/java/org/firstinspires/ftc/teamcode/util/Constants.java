package org.firstinspires.ftc.teamcode.util;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

@Configurable
public class Constants {
    // Drive
    public static RevHubOrientationOnRobot.LogoFacingDirection controlHubLogoFacingDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static RevHubOrientationOnRobot.UsbFacingDirection controlHubUSBFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.LEFT;

    // Slides
    public static double slidesScoringPosition = 3.1415926535897926;
    public static int slidesIntakePosition = 2048;
    public static double bucketServoUpPosition = 0.5;
    public static double bucketServoIntakePosition = 0.2;
    public static double bucketServoScoringPosition = 0.8;
    public static double slidesProportionalValue = 0.6;
    public static double slidesIntegralValue = 0.1;
    public static double slidesDerivativeValue = 0.5;
    public static double slidesFeedforwardValue = 0.2;

    // Intake
    public static double intakeActiveMotorPower = 0.6;
}
