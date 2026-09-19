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
        this.intakeWheelPivotServoLeft = hardwareMap.get(Servo.class, "intakeWheelPivotServoLeft");
        this.intakeWheelPivotServoRight = hardwareMap.get(Servo.class, "intakeWheelPivotServoRight");
        this.intakeRampPivotServoLeft = hardwareMap.get(Servo.class, "intakeRampPivotServoLeft");
        this.intakeRampPivotServoRight = hardwareMap.get(Servo.class, "intakeRampPivotServoRight");
        this.intakeFlowerPivotServoLeft = hardwareMap.get(Servo.class, "intakeFlowerPivotServoLeft");
        this.intakeFlowerPivotServoRight = hardwareMap.get(Servo.class, "intakeFlowerPivotServoRight");
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

    }

    public void setWheelPivotPosition() {

    }
}
