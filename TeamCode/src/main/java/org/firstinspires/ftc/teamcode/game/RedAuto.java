package org.firstinspires.ftc.teamcode.game;

import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionDown;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeFlowerPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeRampPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeWheelPivotPositionUp;
import static org.firstinspires.ftc.teamcode.util.Constants.parkTime;
import static org.firstinspires.ftc.teamcode.util.Constants.shooterSpinUpTime;
import static org.firstinspires.ftc.teamcode.util.paths.PathChainBuilder.buildPathChain;

import java.util.Arrays;

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
    boolean isShooting = false;
    private boolean tip = false; // down, true is up (down means down is correct scoring position, up means up is correct scoring position)
    private boolean knowTip = false;
    private boolean flowerUp = true; // true --> contains balls, false --> already intaked from
    private boolean flowerDown = true;
    private boolean garden = true;
    private boolean hasPollen = true;
    private String middleDestination = ""; // up, down, flowerUp, none
    private double[] currPos;
    private enum AutoState {START, IDLE, SHOOT, INTAKE_1, INTAKE_2, MOVING, CHECK_TIP, PARK}
    private AutoState currentState;
    private boolean doneWithFirstMove = false;
    private boolean hasShot = false;
    int detectedAprilTag = 0;
    private boolean tipCheckedAtAll = false;
    private boolean visited = false;
    private boolean visted2 = false;

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
        follower.setX(redAutonomousPaths.getInitial()[0]);
        follower.setY(redAutonomousPaths.getInitial()[1]);
        follower.setHeading(Math.toRadians(redAutonomousPaths.getInitial()[2]));
        currPos = redAutonomousPaths.getInitial();
    }

    @Override
    public void start() {
        currentState = AutoState.START;
        autoTimer.reset();
    }

    @Override
    public void loop() {
        follower.update();
        CommandScheduler.getInstance().run();
        switch (currentState) {
            case START:
                follower.follow(buildPathChain(redAutonomousPaths.getInitial(), redAutonomousPaths.getDown()));
                currentState = AutoState.MOVING;
                currPos = redAutonomousPaths.getDown();
                break;
            case IDLE:
                if (30 - autoTimer.seconds() <= parkTime) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getPark()));
                    currPos = redAutonomousPaths.getPark();
                    currentState = AutoState.PARK;
                    break;
                }
                if (middleDestination.equals("down")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getDown()));
                    currPos = redAutonomousPaths.getDown();
                    currentState = AutoState.MOVING;
                    middleDestination = "";
                    break;
                }
                if (middleDestination.equals("up")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getUp()));
                    currPos = redAutonomousPaths.getUp();
                    currentState = AutoState.MOVING;
                    middleDestination = "";
                    break;
                }
                if (middleDestination.equals("flowerUp")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerUp1()));
                    currPos = redAutonomousPaths.getFlowerUp1();
                    currentState = AutoState.MOVING;
                    middleDestination = "";
                    break;
                }
                if (middleDestination.equals("flowerDown")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerDown1()));
                    currPos = redAutonomousPaths.getFlowerDown1();
                    currentState = AutoState.MOVING;
                    middleDestination = "";
                    break;
                }
                if (middleDestination.equals("garden")) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getGarden1()));
                    currPos = redAutonomousPaths.getGarden1();
                    currentState = AutoState.MOVING;
                    middleDestination = "";
                    break;
                }
                if (Arrays.equals(currPos, redAutonomousPaths.getFlowerUp1()) || Arrays.equals(currPos, redAutonomousPaths.getFlowerDown1()) || Arrays.equals(currPos, redAutonomousPaths.getGarden1())) {
                    currentState = AutoState.INTAKE_1;
                    break;
                }
                if (hasPollen) {
                    visted2 = true;
                    if (knowTip) {
                        double[] correctShootingPos;
                        if (tip) correctShootingPos = redAutonomousPaths.getUp();
                        else correctShootingPos = redAutonomousPaths.getDown();
                        if (Arrays.equals(currPos, correctShootingPos)) {
                            hasPollen = false;
                            currentState = AutoState.SHOOT;
                            break;
                        } else {
                            if (Arrays.equals(correctShootingPos, redAutonomousPaths.getUp())) {
                                middleDestination = "up";
                            } else {
                                middleDestination = "down";
                            }
                            follower.follow(buildPathChain(currPos, redAutonomousPaths.getMiddle()));
                            currPos = redAutonomousPaths.getMiddle();
                            currentState = AutoState.MOVING;
                            break;
                        }
                    } else {
                        if (Arrays.equals(currPos, redAutonomousPaths.getDown()) || Arrays.equals(currPos, redAutonomousPaths.getUp())) {
                            visited = true;
                            currentState = AutoState.CHECK_TIP;
                            break;
                        } else {
                            if (Arrays.equals(currPos, redAutonomousPaths.getFlowerUp1()) || Arrays.equals(currPos, redAutonomousPaths.getFlowerUp2())) {
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
                if (garden) {
                    garden = false;
                    if (Arrays.equals(currPos, redAutonomousPaths.getUp())) {
                        middleDestination = "garden";
                        follower.follow(buildPathChain(currPos, redAutonomousPaths.getMiddle()));
                        currPos = redAutonomousPaths.getMiddle();
                        currentState = AutoState.MOVING;
                        break;
                    }
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getGarden1()));
                    currPos = redAutonomousPaths.getGarden1();
                    currentState = AutoState.MOVING;
                    break;
                }
                if (flowerUp) {
                    flowerUp = false;
                    if (Arrays.equals(currPos, redAutonomousPaths.getDown())) {
                        middleDestination = "flowerUp";
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
                    if (Arrays.equals(currPos, redAutonomousPaths.getUp())) {
                        middleDestination = "flowerDown";
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
                break;
            case SHOOT:
                if (autoTimer.seconds() <= shooterSpinUpTime) break;
                if (!isShooting) {
                    hasPollen = false;
                    knowTip = false;
                    hasShot = true;
                    CommandScheduler.getInstance().schedule(shooterCommand);
                    isShooting = true;
                }
                if (!CommandScheduler.getInstance().isScheduled(shooterCommand)) {
                    isShooting = false;
                    currentState = AutoState.IDLE;
                }
                break;
            case INTAKE_1: // Assumes that intake transition time < time to go from intake pos 1 to 2
                intakeSubsystem.setWheelPivotPosition(intakeWheelPivotPositionUp);
                intakeSubsystem.setRampPivotPosition(intakeRampPivotPositionUp);
                intakeSubsystem.setMotorPower(intakeMotorPower);
                if (Arrays.equals(currPos, redAutonomousPaths.getFlowerDown1()) || Arrays.equals(currPos, redAutonomousPaths.getFlowerUp1())) {
                    intakeSubsystem.setFlowerPivotPosition(intakeFlowerPivotPositionDown);
                } else {
                    intakeSubsystem.setFlowerPivotPosition(intakeFlowerPivotPositionUp);
                }
                if (Arrays.equals(currPos, redAutonomousPaths.getFlowerDown1())) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerDown2()));
                    currPos = redAutonomousPaths.getFlowerDown2();
                } else if (Arrays.equals(currPos, redAutonomousPaths.getFlowerUp1())) {
                    follower.follow(buildPathChain(currPos, redAutonomousPaths.getFlowerUp2()));
                    currPos = redAutonomousPaths.getFlowerUp2();
                } else if (Arrays.equals(currPos, redAutonomousPaths.getGarden1())) {
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
                    doneWithFirstMove = true;
                }
                break;
            case CHECK_TIP:
                tipCheckedAtAll = true;
//                if (!limelightSubsystem.hasTarget()) {
//                    knowTip = false;
//                    currentState = AutoState.IDLE;
//                    break;
//                }
                if (Arrays.equals(currPos, redAutonomousPaths.getDown())) {
                    if (limelightSubsystem.getAprilTagID() == 34 || limelightSubsystem.getAprilTagID() == 35 ||
                            limelightSubsystem.getAprilTagID() == 36 || limelightSubsystem.getAprilTagID() == 37) {
                        tip = false; // trust me it works
                    } else {
                        tip = true;
                    }
                } else if (Arrays.equals(currPos, redAutonomousPaths.getUp())) {
                    if (limelightSubsystem.getAprilTagID() == 30 || limelightSubsystem.getAprilTagID() == 31 ||
                            limelightSubsystem.getAprilTagID() == 32 || limelightSubsystem.getAprilTagID() == 33) {
                        tip = true;
                    } else {
                        tip = false;
                    }
                }
                detectedAprilTag = limelightSubsystem.getAprilTagID();
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

        telemetry.addData("", "");
        telemetry.addData("State", currentState);
        telemetry.addData("tip", tip);
        telemetry.addData("knowTip", knowTip);
        telemetry.addData("flowerUp", flowerUp);
        telemetry.addData("flowerDown", flowerDown);
        telemetry.addData("garden", garden);
        telemetry.addData("hasPollen", hasPollen);
        telemetry.addData("doneWithFirstMove", doneWithFirstMove);
        telemetry.addData("currPos", redAutonomousPaths.getPose(currPos));
        telemetry.addData("lastDetectedapriltag", detectedAprilTag);
        telemetry.addData("hasShot", hasShot);
        telemetry.addData("tipChecked", tipCheckedAtAll);
        telemetry.addData("tipchecked0", visited);
        telemetry.addData("haspollen1", visted2);
        telemetry.update();
    }

    @Override
    public void stop() {
        CommandScheduler.getInstance().reset();
    }
}