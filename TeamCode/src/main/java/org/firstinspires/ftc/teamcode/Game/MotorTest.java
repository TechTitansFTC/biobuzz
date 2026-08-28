package org.firstinspires.ftc.teamcode.Game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

public class MotorTest extends OpMode {
    DcMotor testMotor;
    GamepadEx gamepadEx;
    @Override
    public void init () {
        testMotor = hardwareMap.get(DcMotor.class, "testMotor");
        gamepadEx = new GamepadEx(gamepad1);
    }

    @Override
    public void loop () {
        testMotor.setPower(gamepadEx.getLeftY());
    }
}
