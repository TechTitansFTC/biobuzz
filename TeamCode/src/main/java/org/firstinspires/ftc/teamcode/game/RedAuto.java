package org.firstinspires.ftc.teamcode.game;

import static org.firstinspires.ftc.teamcode.util.paths.PathChainBuilder.buildPathChain;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;
import com.seattlesolvers.solverslib.pedroCommand.FollowPathCommand;

import org.firstinspires.ftc.teamcode.commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.commands.ToggleShooterMotorCommand;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.LimelightSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;
import org.firstinspires.ftc.teamcode.util.paths.RedAutonomousPaths;

@Autonomous(name = "Red Auto")
public class RedAuto extends OpMode {
    IntakeSubsystem intakeSubsystem;
    ShooterSubsystem shooterSubsystem;
    LimelightSubsystem limelightSubsystem;
    ShooterCommand shooterCommand;
    ToggleShooterMotorCommand toggleShooterMotorCommand;
    private Follower follower;
    private SequentialCommandGroup autonomousRoutine;
    private RedAutonomousPaths redAutonomousPaths;
    private boolean tip = false; // down, true is up (down means down is correct scoring position, up means up is correct scoring position)
    private boolean knowTip = false;
    private boolean flowerUp = true; // true --> contains balls, false --> already intaked from
    private boolean flowerDown = true;
    private boolean garden = true;
    private boolean hasPollen = true;
    private double[] currPos;
    private enum AutoState {IDLE, SHOOT, INTAKE_FLOOR, INTAKE_FLOWER, MOVING, CHECK_TIP}
    private AutoState currentState;

    @Override
    public void init() {
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry);
        limelightSubsystem = new LimelightSubsystem(hardwareMap, telemetry);
        redAutonomousPaths = new RedAutonomousPaths();

        currPos = redAutonomousPaths.getInitial();

        currentState = AutoState.IDLE;
    }

    @Override
    public void loop() {
        switch (currentState) {
            case IDLE:
                if (hasPollen) {
                    if (knowTip) {
//                        if ()
                    }
                }
                break;
        }
    }
}
