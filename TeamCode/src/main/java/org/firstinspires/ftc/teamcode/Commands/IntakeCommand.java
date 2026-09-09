package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.bucketServoIntakePosition;
import static org.firstinspires.ftc.teamcode.util.Constants.slidesIntakePosition;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem;

public class IntakeCommand extends CommandBase {
    private final IntakeSubsystem intakeSubsystem;
    private final SlidesSubsystem slidesSubsystem;
    private ElapsedTime intakeCommandTime;
    private enum IntakeCommandEnum {BUCKET_DOWN, START_INTAKE, BUCKET_UP, END_INTAKE}
    private IntakeCommandEnum intakeCommandEnumState;
    public IntakeCommand(IntakeSubsystem intakeSubsystem, SlidesSubsystem slidesSubsystem) {
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
            // TODO: Finish this
        }
    }

}
