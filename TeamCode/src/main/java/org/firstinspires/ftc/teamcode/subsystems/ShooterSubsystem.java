package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.util.Constants.shooterMotorLeftDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.shooterMotorRightDirection;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ShooterSubsystem extends SubsystemBase {
    private final DcMotor shooterMotorLeft;
    private final DcMotor shooterMotorRight;
    private final Telemetry telemetry;

    public ShooterSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.shooterMotorLeft = hardwareMap.get(DcMotor.class, "shooterMotorLeft");
        this.shooterMotorRight = hardwareMap.get(DcMotor.class, "shooterMotorRight");
        this.telemetry = telemetry;

        this.shooterMotorLeft.setDirection(shooterMotorLeftDirection);
        this.shooterMotorRight.setDirection(shooterMotorRightDirection);
    }

    @Override
    public void periodic() {
        telemetry.addData("Shooter Motor Power: ", getMotorPower());
    }

    public void setMotorPower(double power) {
        shooterMotorLeft.setPower(power);
        shooterMotorRight.setPower(power);
    }

    public double getMotorPower() {
        return shooterMotorLeft.getPower();
    }
}
