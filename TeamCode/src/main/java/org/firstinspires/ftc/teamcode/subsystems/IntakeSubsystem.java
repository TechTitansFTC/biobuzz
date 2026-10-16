package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotServoLeftDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotServoRightDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotServoLeftDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotServoRightDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotServoLeftDirection;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotServoRightDirection;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IntakeSubsystem extends SubsystemBase {
    private final Servo intakeWheelPivotServoLeft;
    private final Servo intakeWheelPivotServoRight;
    private final Servo intakeRampPivotServoLeft;
    private final Servo intakeRampPivotServoRight;
    private final Servo intakeFlowerPivotServoLeft;
    private final Servo intakeFlowerPivotServoRight;
    private final DcMotor intakeMotor;
    private final Telemetry telemetry;

    public IntakeSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        this.intakeWheelPivotServoLeft = hardwareMap.get(Servo.class, "intakeWheelPivotServoLeft"); // Expansion Hub 2
        this.intakeWheelPivotServoRight = hardwareMap.get(Servo.class, "intakeWheelPivotServoRight"); // Control Hub 2
        this.intakeRampPivotServoLeft = hardwareMap.get(Servo.class, "intakeRampPivotServoLeft"); // Expansion Hub 0
        this.intakeRampPivotServoRight = hardwareMap.get(Servo.class, "intakeRampPivotServoRight"); // Control Hub 4
        this.intakeFlowerPivotServoLeft = hardwareMap.get(Servo.class, "intakeFlowerPivotServoLeft"); // Expansion Hub 4
        this.intakeFlowerPivotServoRight = hardwareMap.get(Servo.class, "intakeFlowerPivotServoRight"); // Control Hub 0
        this.intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");

        this.intakeWheelPivotServoLeft.setDirection(intakeWheelPivotServoLeftDirection);
        this.intakeWheelPivotServoRight.setDirection(intakeWheelPivotServoRightDirection);
        this.intakeRampPivotServoLeft.setDirection(intakeRampPivotServoLeftDirection);
        this.intakeRampPivotServoRight.setDirection(intakeRampPivotServoRightDirection);
        this.intakeFlowerPivotServoLeft.setDirection(intakeFlowerPivotServoLeftDirection);
        this.intakeFlowerPivotServoRight.setDirection(intakeFlowerPivotServoRightDirection);
        this.intakeMotor.setDirection(intakeMotorDirection);
    }

    @Override
    public void periodic() {
        if (telemetry != null) {
            telemetry.addData("Intake Wheel Pivot Position: ", getWheelPivotPosition());
            telemetry.addData("Intake Ramp Pivot Position: ", getRampPivotPosition());
            telemetry.addData("Intake Flower Pivot Position: ", getFlowerPivotPosition());
            telemetry.addData("Intake Motor Power: ", getMotorPower());
        }
    }

    public void setWheelPivotPosition(double position) {
        if (intakeWheelPivotServoLeft == null || intakeWheelPivotServoRight == null) {
            return;
        }
        intakeWheelPivotServoLeft.setPosition(position);
        intakeWheelPivotServoRight.setPosition(position);
    }

    public void setRampPivotPosition(double position) {
        if (intakeRampPivotServoLeft == null || intakeRampPivotServoRight == null) {
            return;
        }
        intakeRampPivotServoLeft.setPosition(position);
        intakeRampPivotServoRight.setPosition(position);
    }

    public void setFlowerPivotPosition(double position) {
        if (intakeFlowerPivotServoLeft == null || intakeFlowerPivotServoRight == null) {
            return;
        }
        intakeFlowerPivotServoLeft.setPosition(position);
        intakeFlowerPivotServoRight.setPosition(position);
    }

    public void setMotorPower(double power) {
        if (intakeMotor == null) {
            return;
        }
        intakeMotor.setPower(power);
    }

    public double getWheelPivotPosition() {
        if (intakeWheelPivotServoLeft == null) {
            return 0.0;
        }
        return intakeWheelPivotServoLeft.getPosition();
    }

    public double getRampPivotPosition() {
        if (intakeRampPivotServoLeft == null) {
            return 0.0;
        }
        return intakeRampPivotServoLeft.getPosition();
    }

    public double getFlowerPivotPosition() {
        if (intakeFlowerPivotServoLeft == null) {
            return 0.0;
        }
        return intakeFlowerPivotServoLeft.getPosition();
    }

    public double getMotorPower() {
        if (intakeMotor == null) {
            return 0.0;
        }
        return intakeMotor.getPower();
    }
}