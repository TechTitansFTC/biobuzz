package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.Constants;

@TeleOp(name = "Intake Live Tuner", group = "Testing")
public class IntakeLiveTunerOpMode extends OpMode {

    private Servo wheelPivotLeft, wheelPivotRight;
    private Servo rampPivotLeft, rampPivotRight;
    private Servo flowerPivotLeft, flowerPivotRight;
    private DcMotor intakeMotor;

    @Override
    public void init() {
        // Map hardware - change the strings to match your exact configuration names
        wheelPivotLeft = hardwareMap.get(Servo.class, "wheelPivotLeft");
        wheelPivotRight = hardwareMap.get(Servo.class, "wheelPivotRight");

        rampPivotLeft = hardwareMap.get(Servo.class, "rampPivotLeft");
        rampPivotRight = hardwareMap.get(Servo.class, "rampPivotRight");

        flowerPivotLeft = hardwareMap.get(Servo.class, "flowerPivotLeft");
        flowerPivotRight = hardwareMap.get(Servo.class, "flowerPivotRight");

        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        // Use RUN_WITHOUT_ENCODER for simple power tuning
        intakeMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        telemetry.addLine("Hardware Mapped. Ready to Start.");
        telemetry.update();
    }

    @Override
    public void loop() {
        // 1. Constantly update directions so they apply live from your dashboard
        wheelPivotLeft.setDirection(Constants.intakeWheelPivotServoLeftDirection);
        wheelPivotRight.setDirection(Constants.intakeWheelPivotServoRightDirection);

        rampPivotLeft.setDirection(Constants.intakeRampPivotServoLeftDirection);
        rampPivotRight.setDirection(Constants.intakeRampPivotServoRightDirection);

        flowerPivotLeft.setDirection(Constants.intakeFlowerPivotServoLeftDirection);
        flowerPivotRight.setDirection(Constants.intakeFlowerPivotServoRightDirection);

        intakeMotor.setDirection(Constants.intakeMotorDirection);

        // 2. Control logic based on the selected tuning mode
        switch (Constants.activeTestingMode) {
            case WHEEL_PIVOTS:
                if (gamepad1.a) {
                    wheelPivotLeft.setPosition(Constants.intakeWheelPivotPositionDown);
                    wheelPivotRight.setPosition(Constants.intakeWheelPivotPositionDown);
                } else if (gamepad1.b) {
                    wheelPivotLeft.setPosition(Constants.intakeWheelPivotPositionUp);
                    wheelPivotRight.setPosition(Constants.intakeWheelPivotPositionUp);
                }
                break;

            case RAMP_PIVOTS:
                if (gamepad1.a) {
                    rampPivotLeft.setPosition(Constants.intakeRampPivotPositionDown);
                    rampPivotRight.setPosition(Constants.intakeRampPivotPositionDown);
                } else if (gamepad1.b) {
                    rampPivotLeft.setPosition(Constants.intakeRampPivotPositionUp);
                    rampPivotRight.setPosition(Constants.intakeRampPivotPositionUp);
                }
                break;

            case FLOWER_PIVOTS:
                if (gamepad1.a) {
                    flowerPivotLeft.setPosition(Constants.intakeFlowerPivotPositionDown);
                    flowerPivotRight.setPosition(Constants.intakeFlowerPivotPositionDown);
                } else if (gamepad1.b) {
                    flowerPivotLeft.setPosition(Constants.intakeFlowerPivotPositionUp);
                    flowerPivotRight.setPosition(Constants.intakeFlowerPivotPositionUp);
                }
                break;

            case INTAKE_MOTOR:
                if (gamepad1.a) {
                    intakeMotor.setPower(Constants.intakeMotorPower);
                } else if (gamepad1.b) {
                    intakeMotor.setPower(0.0);
                }
                break;

            case IDLE:
            default:
                // Do nothing when idle to prevent accidental movement
                break;
        }

        // 3. Output current status to telemetry
        telemetry.addData("Active Mode", Constants.activeTestingMode.name());
        telemetry.addLine("--------------------------------");
        telemetry.addLine("Hold 'A' to test DOWN pos / POWER ON");
        telemetry.addLine("Hold 'B' to test UP pos / POWER OFF");
        telemetry.update();
    }
}