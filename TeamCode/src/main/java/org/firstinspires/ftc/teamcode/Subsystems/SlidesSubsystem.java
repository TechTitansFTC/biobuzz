package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.util.Constants.slidesDerivativeValue;
import static org.firstinspires.ftc.teamcode.util.Constants.slidesFeedforwardValue;
import static org.firstinspires.ftc.teamcode.util.Constants.slidesIntegralValue;
import static org.firstinspires.ftc.teamcode.util.Constants.slidesProportionalValue;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.PIDController;

public class SlidesSubsystem extends SubsystemBase {
    private final DcMotor rightSlideMotor;
    private final DcMotor leftSlideMotor;
    private final Servo bucketServo;
    private final PIDController slidesController;
    private final Telemetry telemetry;
    private int targetSlidesPosition;
    private double currentSlidesPower;
    private ElapsedTime integralControlTimer;
    public SlidesSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        rightSlideMotor = hardwareMap.get(DcMotor.class, "rightSlideMotor");
        leftSlideMotor = hardwareMap.get(DcMotor.class, "leftSlideMotor");
        bucketServo = hardwareMap.get(Servo.class, "bucketServo");

        rightSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        leftSlideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        slidesController = new PIDController(slidesProportionalValue, slidesIntegralValue, slidesDerivativeValue, slidesFeedforwardValue);
        targetSlidesPosition = 0;
        currentSlidesPower = 0;

        integralControlTimer = new ElapsedTime();
    }

    @Override
    public void periodic() {
        slidesControl();
        telemetry.addData("targetSlidesPosition", getTargetSlidesPosition());
        telemetry.addData("currentSlidesPower", getCurrentSlidesPower());
        telemetry.addData("bucketServoPosition", getBucketServoPosition());
    }

    public void setTargetSlidesPosition(int targetSlidesPosition) {
        this.targetSlidesPosition = targetSlidesPosition;
        rightSlideMotor.setTargetPosition(targetSlidesPosition);
        leftSlideMotor.setTargetPosition(targetSlidesPosition);
    }

    public double getTargetSlidesPosition() {
        return targetSlidesPosition;
    }

    public double getCurrentSlidesPower() {
        return currentSlidesPower;
    }

    public void slidesControl() {
        double deltaTime = integralControlTimer.seconds();
        if (deltaTime == 0) return;
        integralControlTimer.reset();
        double currentPosition = leftSlideMotor.getCurrentPosition();
        slidesController.setTarget(targetSlidesPosition);
        currentSlidesPower = slidesController.calculateOutput(currentPosition, deltaTime);
        leftSlideMotor.setPower(currentSlidesPower);
        rightSlideMotor.setPower(currentSlidesPower);
    }

    public void setBucketServoPosition(double position) {
        bucketServo.setPosition(position);
    }

    public double getBucketServoPosition() {
        return bucketServo.getPosition();
    }
}
