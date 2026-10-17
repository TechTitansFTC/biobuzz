package org.firstinspires.ftc.teamcode.util.paths;

import com.pedropathing.api.Paths;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;

public class PathChainBuilder {
    public static Path buildPathChain(double[] startPosition, double[] endPosition) {

        Pose startPose = new Pose(startPosition[0], startPosition[1], Math.toRadians(startPosition[2]));
        Pose endPose = new Pose(endPosition[0], endPosition[1], Math.toRadians(endPosition[2]));

        return Paths.line(startPose, endPose).linear(startPose, endPose);
    }
}