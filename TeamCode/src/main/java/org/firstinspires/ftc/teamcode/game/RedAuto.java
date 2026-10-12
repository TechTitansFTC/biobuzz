package org.firstinspires.ftc.teamcode.game;

import static org.firstinspires.ftc.teamcode.util.Constants.transferWaitTime;
import static org.firstinspires.ftc.teamcode.util.paths.PathChainBuilder.buildPathChain;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

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
    private ElapsedTime autoTimer;
    private boolean tip = false; // down, true is up (down means down is correct scoring position, up means up is correct scoring position)
    private boolean knowTip = false;
    private boolean flowerUp = true; // true --> contains balls, false --> already intaked from
    private boolean flowerDown = true;
    private boolean garden = true;
    private boolean hasPollen = true;
    private String middleDesination = "up"; // up, down, flowerUp, none
    private double[] currPos;
    private enum AutoState {IDLE, SHOOT, INTAKE_1, INTAKE_2, MOVING, CHECK_TIP}
    private AutoState currentState;

    @Override
    public void init() {
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry);
        limelightSubsystem = new LimelightSubsystem(hardwareMap, telemetry);
        redAutonomousPaths = new RedAutonomousPaths();
        shooterCommand = new ShooterCommand(intakeSubsystem);
        toggleShooterMotorCommand = new ToggleShooterMotorCommand(shooterSubsystem);

        CommandScheduler.getInstance().schedule(toggleShooterMotorCommand);

        autoTimer = new ElapsedTime();
        autoTimer.reset();

        currPos = redAutonomousPaths.getInitial();

        currentState = AutoState.IDLE;
    }

    @Override
    public void loop() {
        follower.update();
        switch (currentState) {
            case IDLE:
                if (middleDesination.equals("down")) {
                    follower.followPath(buildPathChain(currPos, redAutonomousPaths.getDown()));
                    currentState = AutoState.MOVING;
                    middleDesination = "";
                    break;
                }
                if (middleDesination.equals("up")) {
                    follower.followPath(buildPathChain(currPos, redAutonomousPaths.getUp()));
                    currentState = AutoState.MOVING;
                    middleDesination = "";
                    break;
                }
                if (middleDesination.equals("flowerUp")) {
                    follower.followPath(buildPathChain(currPos, redAutonomousPaths.getFlowerUp1()));
                    currentState = AutoState.MOVING;
                    middleDesination = "";
                    break;
                }
                if (currPos == redAutonomousPaths.getFlowerUp1() || currPos == redAutonomousPaths.getFlowerDown1() || currPos == redAutonomousPaths.getGarden1()) {
                    currentState = AutoState.INTAKE_1;
                    break;
                }
                if (hasPollen) {
                    if (knowTip) {
                        double[] correctShootingPos;
                        if (tip) correctShootingPos = redAutonomousPaths.getUp();
                        else correctShootingPos = redAutonomousPaths.getDown();
                        if (currPos == correctShootingPos) {
                            hasPollen = false;
                            currentState = AutoState.SHOOT;
                        } else {
                            if (correctShootingPos == redAutonomousPaths.getUp()) {
                                middleDesination = "down";
                            } else {
                                middleDesination = "up";
                            }
                            follower.followPath(buildPathChain(currPos, redAutonomousPaths.getMiddle()));
                            currentState = AutoState.MOVING;
                            break;
                        }
                    } else {
                        if (currPos == redAutonomousPaths.getDown() || currPos == redAutonomousPaths.getUp()) {
                            currentState = AutoState.CHECK_TIP;
                        } else {
                            if (currPos == redAutonomousPaths.getFlowerUp1() || currPos == redAutonomousPaths.getFlowerUp2()) {
                                follower.followPath(buildPathChain(currPos, redAutonomousPaths.getUp()));
                            } else {
                                follower.followPath(buildPathChain(currPos, redAutonomousPaths.getDown()));
                            }
                            currentState = AutoState.MOVING;
                        }
                        break;
                    }
                }
                // if !hasPollen
                if (flowerUp) {
                    flowerUp = false;
                    if (currPos == redAutonomousPaths.getDown()) {
                        middleDesination = "flowerUp";
                        follower.followPath(buildPathChain(currPos, redAutonomousPaths.getMiddle()));
                        currentState = AutoState.MOVING;
                        break;
                    }
                    follower.followPath(buildPathChain(currPos, redAutonomousPaths.getFlowerUp1()));
                    currentState = AutoState.MOVING;
                    break;
                }
                if (flowerDown) {
                    flowerDown = false;
                    follower.followPath(buildPathChain(currPos, redAutonomousPaths.getFlowerDown1()));
                    currentState = AutoState.MOVING;
                    break;
                }
                if (garden) {
                    garden = false;
                    follower.followPath(buildPathChain(currPos, redAutonomousPaths.getGarden1()));
                    currentState = AutoState.MOVING;
                    break;
                }
                break;
            case SHOOT:
                hasPollen = false;
                CommandScheduler.getInstance().schedule(shooterCommand);
                if (shooterCommand.isFinished()) {
                    currentState = AutoState.IDLE;
                }
                break;
            case INTAKE_1:
                hasPollen = true;
                intakeSubsystem.
        }
    }
}
