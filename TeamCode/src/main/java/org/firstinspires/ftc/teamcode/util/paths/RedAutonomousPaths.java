package org.firstinspires.ftc.teamcode.util.paths;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;

public class RedAutonomousPaths {
    private double[] initial;
    private double[] down;
    private double[] middle;
    private double[] up;
    private double[] flowerUp1;
    private double[] flowerUp2;
    private double[] flowerDown1;
    private double[] flowerDown2;
    private double[] garden1;
    private double[] garden2;

    public RedAutonomousPaths(Follower follower) {
        initial = new double[]{56, 9, 270};
        down = new double[]{58, 18, 270};
        middle = new double[]{38, 90, 135};
        up = new double[]{58, 126, 90};
        flowerUp1 = new double[]{48, 125, 90};
        flowerUp2 = new double[]{48, 130, 90};
        flowerDown1 = new double[]{18, 48, 180};
        flowerDown2 = new double[]{10, 48, 180};
        garden1 = new double[]{9, 15, 270};
        garden2 = new double[]{9, 9, 270};
    }

    // pass through middle on:
    /*
    * up to down
    * down to flowerUp
     */
}
