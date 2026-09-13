package org.firstinspires.ftc.teamcode.game;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.util.PIDController;

@TeleOp(name="Motor Position Test")
public class MotorPositionTest extends OpMode {
    private DcMotor testMotor1;
    private DcMotor testMotor2;
    private PIDController pidController;
    private GamepadEx gamepadEx;
    private boolean secondMotor;
    private boolean secondMotorInitialized = false;
    @Override
    public void init() {
        testMotor1 = hardwareMap.get(DcMotor.class, "testMotor1");
        testMotor1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        gamepadEx = new GamepadEx(gamepad1);
        gamepadEx.getGamepadButton(GamepadKeys.Button.B)
                .whenPressed(() -> secondMotor = true);
    }

    @Override
    public void init_loop() {
        if (secondMotor && (!secondMotorInitialized)) {
            testMotor2 = hardwareMap.get(DcMotor.class, "testMotor2");
        }
    }
}
