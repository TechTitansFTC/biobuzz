package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

@TeleOp(name="Motor Power Test")
public class MotorPowerTest extends OpMode {
    private DcMotor testMotor;
    private GamepadEx gamepadEx;
    @Override
    public void init () {
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        gamepadEx = new GamepadEx(gamepad1);
        gamepadEx.getGamepadButton(GamepadKeys.Button.A)
                .whenPressed(() -> testMotor.setDirection(DcMotorSimple.Direction.REVERSE));
        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> testMotor.setDirection(DcMotorSimple.Direction.FORWARD));
    }

    @Override
    public void loop () {
        testMotor.setPower(gamepadEx.getLeftY());
        telemetry.addData("Motor Power", testMotor.getPower());
        telemetry.update();
    }
}
