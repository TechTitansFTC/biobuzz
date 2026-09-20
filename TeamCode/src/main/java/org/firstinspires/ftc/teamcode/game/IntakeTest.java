package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;

@TeleOp(name="Intake Test TeleOp")
public class IntakeTest extends OpMode {
    IntakeSubsystem intakeSubsystem;

    private Servo[] servos;
    private double[] positions = new double[6];
    private final String[] servoNames = {
            "intakeWheelPivotServoLeft", "intakeWheelPivotServoRight",
            "intakeRampPivotServoLeft", "intakeRampPivotServoRight",
            "intakeFlowerPivotServoLeft", "intakeFlowerPivotServoRight"
    };

    private int selectedIndex = 0;

    // Gamepad state tracking for edge detection (prevents rapid scrolling)
    private boolean lastDpadUp = false;
    private boolean lastDpadDown = false;
    private boolean lastDpadLeft = false;
    private boolean lastDpadRight = false;
    private boolean lastLeftBumper = false;
    private boolean lastRightBumper = false;

    @Override
    public void init() {
        // Initialize subsystem so its directions from Constants are automatically applied
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);

        // Fetch the servo instances from the hardware map to tune them independently
        servos = new Servo[]{
                hardwareMap.get(Servo.class, "intakeWheelPivotServoLeft"),
                hardwareMap.get(Servo.class, "intakeWheelPivotServoRight"),
                hardwareMap.get(Servo.class, "intakeRampPivotServoLeft"),
                hardwareMap.get(Servo.class, "intakeRampPivotServoRight"),
                hardwareMap.get(Servo.class, "intakeFlowerPivotServoLeft"),
                hardwareMap.get(Servo.class, "intakeFlowerPivotServoRight")
        };

        // Capture initial positions to start tuning from where they are
        for (int i = 0; i < 6; i++) {
            double pos = servos[i].getPosition();
            positions[i] = Double.isNaN(pos) ? 0.5 : pos; // default to 0.5 if not previously set
        }
    }

    @Override
    public void loop() {
        // --- SELECTION LOGIC ---
        // Cycle down the list
        if (gamepad1.dpad_down && !lastDpadDown) {
            selectedIndex = (selectedIndex + 1) % 6;
        }
        // Cycle up the list (+6 prevents negative modulo results in Java)
        if (gamepad1.dpad_up && !lastDpadUp) {
            selectedIndex = (selectedIndex - 1 + 6) % 6;
        }

        // --- ADJUSTMENT LOGIC ---
        double increment = 0;
        if (gamepad1.dpad_right && !lastDpadRight) increment = 0.1;
        if (gamepad1.dpad_left && !lastDpadLeft) increment = -0.1;
        if (gamepad1.right_bumper && !lastRightBumper) increment = 0.01;
        if (gamepad1.left_bumper && !lastLeftBumper) increment = -0.01;

        if (increment != 0) {
            positions[selectedIndex] += increment;

            // Clamp between 0.0 and 1.0 to prevent servo damage
            if (positions[selectedIndex] > 1.0) positions[selectedIndex] = 1.0;
            if (positions[selectedIndex] < 0.0) positions[selectedIndex] = 0.0;

            // Apply the new position specifically to the selected servo
            servos[selectedIndex].setPosition(positions[selectedIndex]);
        }

        // --- TELEMETRY UI ---
        telemetry.addLine("--- INTAKE TUNING MENU ---");
        telemetry.addLine("Up/Down D-Pad: Select Servo");
        telemetry.addLine("Left/Right D-Pad: +/- 0.1");
        telemetry.addLine("Left/Right Bumper: +/- 0.01");
        telemetry.addLine(" ");

        for (int i = 0; i < 6; i++) {
            String prefix = (i == selectedIndex) ? "-> " : "   ";
            telemetry.addData(prefix + servoNames[i], "%.3f", positions[i]);
        }

        telemetry.addLine("\n--- INTAKE SUBSYSTEM TELEMETRY ---");
        intakeSubsystem.periodic(); // Prints standard subsystem telemetry below

        // --- STATE UPDATES ---
        lastDpadUp = gamepad1.dpad_up;
        lastDpadDown = gamepad1.dpad_down;
        lastDpadLeft = gamepad1.dpad_left;
        lastDpadRight = gamepad1.dpad_right;
        lastLeftBumper = gamepad1.left_bumper;
        lastRightBumper = gamepad1.right_bumper;
    }
}