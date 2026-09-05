package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name="Servo Test")
public class ServoTest extends OpMode {
    private Servo testServo;
    private GamepadEx gamepadEx;

    @Override
    public void init () {
        testServo = hardwareMap.get(Servo.class, "testServo");
        gamepadEx = new GamepadEx(gamepad1);
    }

    @Override
    public void loop () {
        double servoPos = testServo.getPosition();
        if (gamepadEx.wasJustPressed(GamepadKeys.Button.A)) {
            testServo.setPosition(servoPos + 0.01);
        } else if (gamepadEx.wasJustPressed(GamepadKeys.Button.B)) {
            testServo.setPosition(servoPos - 0.01);
        }
        telemetry.addData("Servo Pos", testServo.getPosition());
        telemetry.update();
    }
}
