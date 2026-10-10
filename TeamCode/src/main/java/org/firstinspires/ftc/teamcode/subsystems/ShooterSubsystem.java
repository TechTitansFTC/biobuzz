package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.util.Constants.shooterMotorLeftDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.shooterMotorRightDirection;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.BangBangController;
import org.firstinspires.ftc.teamcode.util.Constants;

import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.control.feedforward.SimpleFFCoefficients;
import dev.nextftc.control.feedforward.SimpleFeedforward;

public class ShooterSubsystem extends SubsystemBase {
    private final DcMotorEx shooterMotorLeft;
    private final DcMotorEx shooterMotorRight;
    private final VoltageSensor batteryVoltageSensor;
    private final Telemetry telemetry;

    private final PIDCoefficients pidCoefficients = new PIDCoefficients(Constants.shooterkP);
    private final PIDController pid = new PIDController(pidCoefficients);
    private final SimpleFFCoefficients ffCoefficients = new SimpleFFCoefficients(Constants.shooterkS, Constants.shooterkV);
    private final SimpleFeedforward feedforward = new SimpleFeedforward(ffCoefficients);
    private final BangBangController bangBang = new BangBangController();

    private boolean enabled = true;
    private boolean idle = false;
    private boolean usingBangBang = false;
    private double measuredRpm = 0.0;
    private double outputPower = 0.0;
    private double batteryVoltage = 12.0;
    private final ElapsedTime voltageTimer = new ElapsedTime();

    public ShooterSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.shooterMotorLeft = hardwareMap.get(DcMotorEx.class, "shooterMotorLeft");
        this.shooterMotorRight = hardwareMap.get(DcMotorEx.class, "shooterMotorRight");
        this.batteryVoltageSensor = hardwareMap.voltageSensor.iterator().next();
        this.telemetry = telemetry;

        this.shooterMotorLeft.setDirection(shooterMotorLeftDirection);
        this.shooterMotorRight.setDirection(shooterMotorRightDirection);

        shooterMotorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterMotorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotorLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        shooterMotorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        shooterMotorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        shooterMotorRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
    }

    @Override
    public void periodic() {
        pidCoefficients.kP = Constants.shooterkP;
        ffCoefficients.kS = Constants.shooterkS;
        ffCoefficients.kV = Constants.shooterkV;

        measuredRpm = ticksPerSecondToWheelRpm(shooterMotorLeft.getVelocity());

        if (voltageTimer.seconds() > 0.25) {
            batteryVoltage = batteryVoltageSensor.getVoltage();
            voltageTimer.reset();
        }

        double targetRpm = getTargetRpm();
        if (targetRpm <= 0) {
            outputPower = 0.0;
            usingBangBang = false;
            pid.reset();
        } else if (targetRpm - measuredRpm > Constants.shooterBangBangThreshold) {
            outputPower = bangBang.calculate(measuredRpm, targetRpm);
            usingBangBang = true;
        } else {
            if (usingBangBang) pid.reset();
            usingBangBang = false;
            double volts = feedforward.calculate(targetRpm)
                    + pid.calculateFromReference(targetRpm, measuredRpm);
            outputPower = Range.clip(volts / batteryVoltage, 0.0, 1.0);
        }

        shooterMotorLeft.setPower(outputPower);
        shooterMotorRight.setPower(outputPower);

        telemetry.addData("Shooter Target RPM", targetRpm);
        telemetry.addData("Shooter Measured RPM", measuredRpm);
        telemetry.addData("Shooter Error RPM", targetRpm - measuredRpm);
        telemetry.addData("Shooter Mode", targetRpm <= 0 ? "OFF" : usingBangBang ? "BANG-BANG" : "PID+FF");
        telemetry.addData("Shooter Power", outputPower);
        telemetry.addData("Shooter At Speed", atSpeed());
        telemetry.addData("Battery V", batteryVoltage);
    }

    public void enable() {
        if (!enabled) pid.reset();
        enabled = true;
    }

    public void disable() {
        enabled = false;
    }

    public void toggle() {
        if (enabled) disable(); else enable();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setIdle(boolean idle) {
        if (this.idle != idle) pid.reset();
        this.idle = idle;
    }

    public double getTargetRpm() {
        if (!enabled) return 0.0;
        return idle ? Constants.shooterIdleRpm : Constants.shooterTargetRpm;
    }

    public double getMeasuredRpm() {
        return measuredRpm;
    }

    public boolean atSpeed() {
        return enabled && Math.abs(getTargetRpm() - measuredRpm) <= Constants.shooterRpmTolerance;
    }

    private double ticksPerSecondToWheelRpm(double ticksPerSecond) {
        double motorRpm = ticksPerSecond / Constants.shooterTicksPerRev * 60.0;
        return motorRpm / Constants.shooterGearRatio;
    }
}
