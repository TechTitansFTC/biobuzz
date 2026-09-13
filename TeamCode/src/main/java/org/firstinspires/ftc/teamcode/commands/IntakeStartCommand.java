package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.bucketServoIntakePosition;
import static org.firstinspires.ftc.teamcode.util.Constants.intakeActiveMotorPower;
import static org.firstinspires.ftc.teamcode.util.Constants.slidesIntakePosition;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem;

public class IntakeStartCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private final SlidesSubsystem slidesSubsystem;
    private ElapsedTime intakeCommandTime;
    private enum IntakeCommandEnum {BUCKET_DOWN, START_INTAKE, STOP}
    private IntakeCommandEnum intakeCommandEnumState;
    public IntakeStartCommand(IntakeSubsystem intakeSubsystem, SlidesSubsystem slidesSubsystem) {
        this.intakeSubsystem = intakeSubsystem;
        this.slidesSubsystem = slidesSubsystem;

        intakeCommandEnumState = IntakeCommandEnum.BUCKET_DOWN;
        intakeCommandTime = new ElapsedTime();
    }

    @Override
    public void initialize() {
        slidesSubsystem.setTargetSlidesPosition(slidesIntakePosition);
        intakeCommandEnumState = IntakeCommandEnum.BUCKET_DOWN;
        intakeCommandTime.reset();
    }

    @Override
    public void execute() {
        switch (intakeCommandEnumState) {
            case BUCKET_DOWN:
                slidesSubsystem.setBucketServoPosition(bucketServoIntakePosition);
                if (intakeCommandTime.seconds() >= 1) {
                    intakeCommandTime.reset();
                    intakeCommandEnumState = IntakeCommandEnum.START_INTAKE;
                }
                break;
            case START_INTAKE:
                intakeSubsystem.setIntakeMotorPower(intakeActiveMotorPower);
                intakeCommandEnumState = IntakeCommandEnum.STOP;
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return (intakeCommandEnumState == IntakeCommandEnum.STOP);

    }
}
