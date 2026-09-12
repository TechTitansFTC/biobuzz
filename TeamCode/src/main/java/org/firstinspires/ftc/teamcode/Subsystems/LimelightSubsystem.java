package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Configurable
public class LimelightSubsystem extends SubsystemBase {
    private final Limelight3A limelight;
    private final Telemetry telemetry;
    public LimelightSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {
        this.limelight = hardwareMap.get(Limelight3A.class, "limelight");
        this.telemetry = telemetry;

        limelight.pipelineSwitch(3);
        limelight.start();

        register();
    }

    @Override
    public void periodic() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            telemetry.addData("Limelight Target", "tx: %.2f, ty: %.2f", getTx(), getTy());
            telemetry.addData("Horizontal Distance", calculateHorizontalDistance());
        } else {
            telemetry.addData("Limelight Target", "None");
        }
    }

    public LLResult getLatestResult() {
        return limelight.getLatestResult();
    }

    public double getTy() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTy();
        }
        return 0;
    }

    public double getTx() {
        LLResult result = getLatestResult();
        if (result != null && result.isValid()) {
            return result.getTx();
        }
        return 0;
    }

    public double calculateHoodPositionTicks() {
        if (!hasTarget()) return 0; // lowest position
        double distanceInches = calculateHorizontalDistance();

        return -6.37485*Math.pow(0.883676, distanceInches) + 1.00556;
    }

    public double calculateHorizontalDistance() {
        if (!hasTarget()) return -1; // lowest position
        double h2 = 11.5;
        double hh2 = 29.5;

        double a2 = getTx();

        double angleRadians = Math.toRadians(a2);

        return ((hh2 - h2) / Math.tan(angleRadians)) - 3.5;
    }

    public boolean hasTarget() {
        LLResult result = getLatestResult();
        return result != null && result.isValid();
    }


    public void stop() {
        limelight.stop();
    }
}