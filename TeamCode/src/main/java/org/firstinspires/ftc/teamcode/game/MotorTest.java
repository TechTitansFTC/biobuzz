package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

@TeleOp(name="Motor Test")
public class MotorTest extends OpMode {
    private DcMotor testMotor;
    private GamepadEx gamepadEx;
    @Override
    public void init () {
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        gamepadEx = new GamepadEx(gamepad1);
    }

    @Override
    public void loop () {
        testMotor.setPower(gamepadEx.getLeftY());
        telemetry.addData("Motor Power", testMotor.getPower());
        telemetry.update();
    }
}
