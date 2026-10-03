package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp(name="Motor Test")
public class MotorPowerTest extends OpMode {
    private DcMotor testMotor1;
    private DcMotor testMotor2;
    private GamepadEx gamepadEx;
    private double motorPower;
    private boolean twoMotors = true;
    private boolean analog = false;

    @Override
    public void init () {
        motorPower = 0.0;
        gamepadEx = new GamepadEx(gamepad1);
        telemetry.addData("INSTRUCTIONS: ", "Press DPAD up or down to select number of" +
                " motors. Name the motors 'testMotor1' and/or 'testMotor2' in config.");
        telemetry.update();
    }

    @Override
    public void init_loop() {
        gamepadEx.readButtons();
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_UP)) twoMotors = true;
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.DPAD_DOWN)) twoMotors = false;
        if (twoMotors) {
            telemetry.addData("  ", "One Motor");
            telemetry.addData("> ", "Two Motor");
        } else {
            telemetry.addData("> ", "One Motor");
            telemetry.addData("  ", "Two Motor");
        }
        telemetry.update();
    }

    @Override
    public void start() {
        testMotor1 = hardwareMap.get(DcMotor.class, "testMotor1");
        if (twoMotors) testMotor2 = hardwareMap.get(DcMotor.class, "testMotor2");
        testMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        if (twoMotors) testMotor2.setDirection(DcMotorSimple.Direction.FORWARD);

        gamepadEx.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> {
                    testMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
                    if (twoMotors) testMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
                });
        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> {
                    testMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
                    if (twoMotors) testMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
                });
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                .whenPressed(() -> {
                    motorPower -= 0.1;
                    if (motorPower < 0) motorPower = 0;
                });
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_UP)
                .whenPressed(() -> {
                    motorPower += 0.1;
                    if (motorPower > 1) motorPower = 1;
                });
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_LEFT)
                .whenPressed(() -> {
                    motorPower -= 0.05;
                    if (motorPower < 0) motorPower = 0;
                });
        gamepadEx.getGamepadButton(GamepadKeys.Button.DPAD_RIGHT)
                .whenPressed(() -> {
                    motorPower += 0.05;
                    if (motorPower > 1) motorPower = 1;
                });
        gamepadEx.getGamepadButton(GamepadKeys.Button.Y)
                .whenPressed(() -> {
                    motorPower += 0.01;
                    if (motorPower > 1) motorPower = 1;
                });

        gamepadEx.getGamepadButton(GamepadKeys.Button.X)
                .whenPressed(() -> {
                    motorPower -= 0.01;
                    if (motorPower < 0) motorPower = 0;
                });

        gamepadEx.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                .whenPressed(() -> analog = true);

        gamepadEx.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                .whenPressed(() -> analog = false);
    }

    @Override
    public void loop () {
        telemetry.addData("INSTRUCTIONS: ", "");
        telemetry.addData("DPAD UP/DOWN: ", "Motor Power +-0.1");
        telemetry.addData("DPAD RIGHT/LEFT: ", "Motor Power +-0.05");
        telemetry.addData("Y/X: ", "Motor Power +-0.01");
        telemetry.addData("A/B: ", "Switch Motor Direction");
        telemetry.addData("Left Bumper: ", "Switch to analog mode");
        telemetry.addData("Right Bumper: ", "Switch back to manual mode");

        telemetry.addData("", "");
        if (analog) {
            testMotor1.setPower(gamepadEx.getLeftY());
            if (twoMotors) testMotor2.setPower(gamepadEx.getRightY());
        } else {
            testMotor1.setPower(motorPower);
            if (twoMotors) testMotor2.setPower(motorPower);
        }
        telemetry.addData("Values: ", "");
        telemetry.addData("Motor Power: ", motorPower);
        String motor1Direction;
        String mode;
        if (testMotor1.getDirection() == DcMotorSimple.Direction.FORWARD) motor1Direction = "Forward";
        else motor1Direction = "Reverse";
        if (analog) mode = "Analog";
        else mode = "Manual";
        telemetry.addData("Motor 1 Direction (Motor 2 will be opposite): ", motor1Direction);
        telemetry.addData("Mode: ", mode);
        telemetry.update();
        CommandScheduler.getInstance().run();
        gamepadEx.readButtons();
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();
    }
}
