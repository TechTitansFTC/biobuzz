package org.firstinspires.ftc.teamcode.util.paths;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class PathChainBuilder {
    public static PathChain buildPathChain(double[] startPosition, double[] endPosition) {
        return follower
                .pathBuilder()
                .addPath(
                        new BezierLine(new Pose(startPosition[0], startPosition[1]), new Pose(endPosition[0], endPosition[1]))
                )
                .setLinearHeadingInterpolation(Math.toRadians(startPosition[2]), Math.toRadians(endPosition[2]))
                .build();
    }
}
