package org.firstinspires.ftc.teamcode.game;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.parkTime;
import static org.firstinspires.ftc.teamcode.util.paths.PathChainBuilder.buildPathChain;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandScheduler;
import com.seattlesolvers.solverslib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.commands.ShooterCommand;
import org.firstinspires.ftc.teamcode.pedro.Constants;
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
    private enum AutoState {IDLE, SHOOT, INTAKE_1, INTAKE_2, MOVING, CHECK_TIP, PARK}
    private AutoState currentState;

    @Override
    public void init() {
        intakeSubsystem = new IntakeSubsystem(hardwareMap, telemetry);
        shooterSubsystem = new ShooterSubsystem(hardwareMap, telemetry);
        limelightSubsystem = new LimelightSubsystem(hardwareMap, telemetry);
        redAutonomousPaths = new RedAutonomousPaths();
        shooterCommand = new ShooterCommand(intakeSubsystem, shooterSubsystem);

        autoTimer = new ElapsedTime();
        autoTimer.reset();

        follower = Constants.create(hardwareMap);
        assert follower != null;
        follower.setX(redAutonomousPaths.getInitial()[0]);
        follower.setY(redAutonomousPaths.getInitial()[1]);
        follower.setHeading(Math.toRadians(redAutonomousPaths.getInitial()[2]));
        currPos = redAutonomousPaths.getInitial();
        currentState = AutoState.IDLE;
    }

    @Override
    public void start() {
        CommandScheduler.getInstance().schedule(ShooterCommand.toggleFlywheel(shooterSubsystem));
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
        switch (currentState) {
            case IDLE:
                if (30 - autoTimer.seconds() <= parkTime) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getPark()));
                    currPos = redAutonomousPaths.getPark();
                    currentState = AutoState.PARK;
                    break;
                }
                if (middleDesination.equals("down")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getDown()));
                    currPos = redAutonomousPaths.getDown();
                    currentState = AutoState.MOVING;
                    middleDesination = "";
                    break;
                }
                if (middleDesination.equals("up")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getUp()));
                    currPos = redAutonomousPaths.getUp();
                    currentState = AutoState.MOVING;
                    middleDesination = "";
                    break;
                }
                if (middleDesination.equals("flowerUp")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerUp1()));
                    currPos = redAutonomousPaths.getFlowerUp1();
                    currentState = AutoState.MOVING;
                    middleDesination = "";
                    break;
                }
                if (middleDesination.equals("flowerDown")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerDown1()));
                    currPos = redAutonomousPaths.getFlowerDown1();
                    currentState = AutoState.MOVING;
                    middleDesination = "";
                    break;
                }
                if (middleDesination.equals("garden")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getGarden1()));
                    currPos = redAutonomousPaths.getGarden1();
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
                            follower.follow(buildPathChain(currPos, redAutonomousPaths.getMiddle()));
                            currPos = redAutonomousPaths.getMiddle();
                            currentState = AutoState.MOVING;
                            break;
                        }
                    } else {
                        if (currPos == redAutonomousPaths.getDown() || currPos == redAutonomousPaths.getUp()) {
                            currentState = AutoState.CHECK_TIP;
                        } else {
                            if (currPos == redAutonomousPaths.getFlowerUp1() || currPos == redAutonomousPaths.getFlowerUp2()) {
                                follower.follow(buildPathChain(currPos, redAutonomousPaths.getUp()));
                                currPos = redAutonomousPaths.getUp();
                            } else {
                                follower.follow(buildPathChain(currPos, redAutonomousPaths.getDown()));
                                currPos = redAutonomousPaths.getDown();
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
                        follower.follow(buildPathChain(currPos, redAutonomousPaths.getMiddle()));
                        currPos = redAutonomousPaths.getMiddle();
                        currentState = AutoState.MOVING;
                        break;
                    }
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerUp1()));
                    currPos = redAutonomousPaths.getFlowerUp1();
                    currentState = AutoState.MOVING;
                    break;
                }
                if (flowerDown) {
                    flowerDown = false;
                    if (currPos == redAutonomousPaths.getUp()) {
                        middleDesination = "flowerDown";
                        follower.follow(buildPathChain(currPos, redAutonomousPaths.getMiddle()));
                        currPos = redAutonomousPaths.getMiddle();
                        currentState = AutoState.MOVING;
                        break;
                    }
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerDown1()));
                    currPos = redAutonomousPaths.getFlowerDown1();
                    currentState = AutoState.MOVING;
                    break;
                }
                if (garden) {
                    garden = false;
                    if (currPos == redAutonomousPaths.getUp()) {
                        middleDesination = "garden";
                        follower.follow(buildPathChain(currPos, redAutonomousPaths.getGarden1()));
                        currPos = redAutonomousPaths.getMiddle();
                        currentState = AutoState.MOVING;
                        break;
                    }
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getGarden1()));
                    currPos = redAutonomousPaths.getGarden1();
                    currentState = AutoState.MOVING;
                    break;
                }
                break;
            case SHOOT:
                hasPollen = false;
                knowTip = false;
                CommandScheduler.getInstance().schedule(shooterCommand);
                if (shooterCommand.isFinished()) {
                    currentState = AutoState.IDLE;
                }
                break;
            case INTAKE_1: // Assumes that intake transition time < time to go from intake pos 1 to 2
                intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionUp);
                intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionUp);
                intakeSubsystem.setMotorPower(intakeMotorPower);
                if (currPos == redAutonomousPaths.getFlowerDown1() || currPos == redAutonomousPaths.getFlowerUp1()) {
                    intakeSubsystem.setFlowerPivotPosition(intakeFlowerPivotPositionDown);
                } else {
                    intakeSubsystem.setFlowerPivotPosition(intakeFlowerPivotPositionUp);
                }
                if (currPos == redAutonomousPaths.getFlowerDown1()) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerDown2()));
                    currPos = redAutonomousPaths.getFlowerDown2();
                } else if (currPos == redAutonomousPaths.getFlowerUp1()) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerUp2()));
                    currPos = redAutonomousPaths.getFlowerUp2();
                } else if (currPos == redAutonomousPaths.getGarden1()) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getGarden2()));
                    currPos = redAutonomousPaths.getGarden2();
                }
                currentState = AutoState.INTAKE_2;
                break;
            case INTAKE_2:
                if (!follower.isBusy()) {
                    hasPollen = true;
                    intakeSubsystem.setMotorPower(0);
                    currentState = AutoState.IDLE;
                }
                break;
            case MOVING:
                if (!follower.isBusy()) {
                    currentState = AutoState.IDLE;
                }
                break;
            case CHECK_TIP:
                if (!limelightSubsystem.hasTarget()) {
                    knowTip = false;
                    currentState = AutoState.IDLE;
                    break;
                }
                if (currPos == redAutonomousPaths.getDown()) {
                    if (limelightSubsystem.getAprilTagID() == 34 || limelightSubsystem.getAprilTagID() == 35 ||
                            limelightSubsystem.getAprilTagID() == 36 || limelightSubsystem.getAprilTagID() == 37) {
                        tip = false; // trust me it works
                    } else {
                        tip = true;
                    }
                } else if (currPos == redAutonomousPaths.getUp()) {
                    if (limelightSubsystem.getAprilTagID() == 30 || limelightSubsystem.getAprilTagID() == 31 ||
                            limelightSubsystem.getAprilTagID() == 32 || limelightSubsystem.getAprilTagID() == 33) {
                        tip = true;
                    } else {
                        tip = false;
                    }
                }
                knowTip = true;
                currentState = AutoState.IDLE;
                break;
            case PARK:
                if (!follower.isBusy()) {
                    requestOpModeStop();
                    return;
                }
                break;
        }
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();
    }
}
