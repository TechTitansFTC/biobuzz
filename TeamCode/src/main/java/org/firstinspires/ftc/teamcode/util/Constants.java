package org.firstinspires.ftc.teamcode.util;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;

@Configurable
public class Constants {
    // Drive
    public static RevHubOrientationOnRobot.LogoFacingDirection controlHubLogoFacingDirection = RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static RevHubOrientationOnRobot.UsbFacingDirection controlHubUSBFacingDirection = RevHubOrientationOnRobot.UsbFacingDirection.LEFT;

    // Limelight
    public static double pValue = 0.3;
    public static double iValue = 0.0;
    public static double dValue = 0.1;
    public static double limelightAlignAccuracy = 2;
    public static int flipLimelightPower = -1;
}
