package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.dValue;
import static org.firstinspires.ftc.teamcode.util.Constants.flipLimelightPower;
import static org.firstinspires.ftc.teamcode.util.Constants.iValue;
import static org.firstinspires.ftc.teamcode.util.Constants.pValue;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDriveSubsystem;
import org.firstinspires.ftc.teamcode.util.PIDController;

public class LimelightAlignCommand extends CommandBase {
    private final MecanumDriveSubsystem mecanumDriveSubsystem;
    private final LimelightSubsystem limelightSubsystem;
    private final ElapsedTime loopTimer;
    private final PIDController pidController;
    private final GamepadEx gamepadEx;
    public LimelightAlignCommand(GamepadEx gamepadEx, MecanumDriveSubsystem mecanumDriveSubsystem, LimelightSubsystem limelightSubsystem) {
        this.mecanumDriveSubsystem = mecanumDriveSubsystem;
        this.limelightSubsystem = limelightSubsystem;
        this.gamepadEx = gamepadEx;

        loopTimer = new ElapsedTime();
        pidController = new PIDController(pValue, iValue, dValue);

        addRequirements(mecanumDriveSubsystem, limelightSubsystem);
    }

    @Override
    public void initialize() {
        pidController.setTarget(0.0);
        loopTimer.reset();
    }

    @Override
    public void execute() {
        double currentTx = limelightSubsystem.getTx();
        pidController.setPID(pValue, iValue, dValue);
        if (currentTx == -361) {
            mecanumDriveSubsystem.drive(0, 0, 0);
            return;
        }
        double dt = loopTimer.seconds();
        loopTimer.reset();
        if (dt == 0) return;
        double power = pidController.calculateOutput(currentTx, dt);
        power *= flipLimelightPower;
        double x = gamepadEx.getLeftX();
        double y = gamepadEx.getLeftY();
        mecanumDriveSubsystem.drive(x, y, power);
    }

    @Override
    public boolean isFinished() {
        double currentTx = limelightSubsystem.getTx();
        return currentTx == -361;
    }
}