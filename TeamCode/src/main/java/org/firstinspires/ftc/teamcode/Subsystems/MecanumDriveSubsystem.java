package org.firstinspires.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.seattlesolvers.solverslib.command.SubsystemBase;


import org.firstinspires.ftc.robotcore.external.Telemetry;

public class MecanumDriveSubsystem extends SubsystemBase{
    IMU imu;
    DcMotor frontLeft, frontRight, backLeft, backRight;

    private final Telemetry telemetry;

    public MecanumDriveSubsystem(HardwareMap hardwareMap, IMU imu, Telemetry telemetry) {
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);

        this.imu = imu;
        this.telemetry = telemetry;

        register();
    }

    @Override
    public void periodic() {
        telemetry.addData("backLeftMotorPower", getBackLeftMotorPower());
        telemetry.addData("backRightMotorPower", getBackRightMotorPower());
        telemetry.addData("frontLeftMotorPower", getFrontLeftMotorPower());
        telemetry.addData("frontRightMotorPower", getFrontRightMotorPower());
    }

    public void drive(double x, double y, double rotation) {
        double headingRadians = (-(imu.getRobotYawPitchRollAngles().getYaw()) * Math.PI / 180.0);

        double rotatedX = x * Math.cos(headingRadians) - y * Math.sin(headingRadians);
        double rotatedY = x * Math.sin(headingRadians) + y * Math.cos(headingRadians);

        double fl = rotatedY + rotatedX + rotation;
        double fr = rotatedY - rotatedX - rotation;
        double bl = rotatedY - rotatedX + rotation;
        double br = rotatedY + rotatedX - rotation;

        double max = Math.max(1.0, Math.max(Math.abs(fl), Math.max(Math.abs(fr), Math.max(Math.abs(bl), Math.abs(br)))));

        frontLeft.setPower(fl / max);
        frontRight.setPower(fr / max);
        backLeft.setPower(bl / max);
        backRight.setPower(br / max);
    }

    public void resetIMU() {
        imu.resetYaw();
    }

    public double getBackLeftMotorPower() { return backLeft.getPower(); }
    public double getBackRightMotorPower() { return backRight.getPower(); }
    public double getFrontLeftMotorPower() { return frontLeft.getPower(); }
    public double getFrontRightMotorPower() { return frontRight.getPower(); }

}
