package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp(name="MANUAL Motor Power Test Two Motors")
public class MotorPowerTestTwoMotors extends OpMode {
    private DcMotor testMotor1;
    private DcMotor testMotor2;
    private GamepadEx gamepadEx;
    private double motorPower;

    @Override
    public void init () {
        motorPower = 0.0;
        testMotor1 = hardwareMap.get(DcMotor.class, "testMotor1");
        testMotor2 = hardwareMap.get(DcMotor.class, "testMotor2");
        gamepadEx = new GamepadEx(gamepad1);
        gamepadEx.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> {
                    testMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
                    testMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
                });
        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> {
                    testMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
                    testMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
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
    }

    @Override
    public void loop () {
        testMotor1.setPower(motorPower);
        testMotor2.setPower(motorPower);
        telemetry.addData("Motor Power: ", motorPower);
        telemetry.update();
        CommandScheduler.getInstance().run();
        gamepadEx.readButtons();
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();
    }
}
