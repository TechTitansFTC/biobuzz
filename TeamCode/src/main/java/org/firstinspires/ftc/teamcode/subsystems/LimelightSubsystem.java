package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.List;

public class LimelightSubsystem extends SubsystemBase {
    private final Limelight3A limelight;
    private final Telemetry telemetry;
    LLResult result;

    public LimelightSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.start();
        limelight.pipelineSwitch(0);
        result = limelight.getLatestResult();

        this.telemetry = telemetry;

        register();
    }

    @Override
    public void periodic() {
        result = limelight.getLatestResult();

        telemetry.addData("hasTarget", hasTarget());
        telemetry.addData("getAprilTagID", getAprilTagID());
    }

    // Returns true if any target is visible
    public boolean hasTarget() {
        if (result != null) {
            return result.isValid();
        }
        return false;
    }

    // Returns the first AprilTag ID detected, or -1 if none
    public int getAprilTagID() {
        if (hasTarget()) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();
            if (fiducials != null && !fiducials.isEmpty()) {
                return fiducials.get(0).getFiducialId();
            }
        }
        return -1;
    }
}
