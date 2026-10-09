package org.firstinspires.ftc.teamcode.game;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

@Autonomous(name = "Red Auto")
public class RedAuto extends OpMode {
    IntakeSubsystem intakeSubsystem;
    ShooterSubsystem shooterSubsystem;
    LimelightSubsystem limelightSubsystem;
    private Follower follower;
    private SequentialCommandGroup autonomousRoutine;
    private boolean tip = false; // down, true is up (down means down is correct scoring position, up means up is correct scoring position)
    private boolean flowerUp = true; // true --> contains balls, false --> already intaked from
    private boolean flowerDown = true;
    private boolean garden = true;

    @Override
    public void init() {
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry);
        limelightSubsystem = new LimelightSubsystem(hardwareMap, telemetry);
    }

    @Override
    public void loop() {

    }
}
