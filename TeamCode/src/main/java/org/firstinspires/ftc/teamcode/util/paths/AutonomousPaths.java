package org.firstinspires.ftc.teamcode.util.paths;

import java.util.Arrays;

public class AutonomousPaths {
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
    private double[] park;

    public AutonomousPaths() {
        initial = new double[]{56, 9, 270}; // x, y, heading
        down = new double[]{58, 18, 270};
        middle = new double[]{9, 93, 90};
        up = new double[]{58, 126, 90};
        flowerUp1 = new double[]{48, 123, 90};
        flowerUp2 = new double[]{48, 130, 90};
        flowerDown1 = new double[]{20, 48, 180};
        flowerDown2 = new double[]{14, 48, 180};
        garden1 = new double[]{9, 17, 270};
        garden2 = new double[]{9, 12.5, 270};
        park = new double[]{9, 95, 90};
    }

    public double[] getInitial() {
        return initial;
    }

    public double[] getDown() {
        return down;
    }

    public double[] getMiddle() {
        return middle;
    }

    public double[] getUp() {
        return up;
    }

    public double[] getFlowerUp1() {
        return flowerUp1;
    }

    public double[] getFlowerUp2() {
        return flowerUp2;
    }

    public double[] getFlowerDown1() {
        return flowerDown1;
    }

    public double[] getFlowerDown2() {
        return flowerDown2;
    }

    public double[] getGarden1() {
        return garden1;
    }

    public double[] getGarden2() {
        return garden2;
    }

    public double[] getPark() {
        return park;
    }

    public String getPose(double[] pose) {
        if (Arrays.equals(pose, initial)) return "initial";
        if (Arrays.equals(pose, down)) return "down";
        if (Arrays.equals(pose, middle)) return "middle";
        if (Arrays.equals(pose, up)) return "up";
        if (Arrays.equals(pose, flowerUp1)) return "flowerUp1";
        if (Arrays.equals(pose, flowerUp2)) return "flowerUp2";
        if (Arrays.equals(pose, flowerDown1)) return "flowerDown1";
        if (Arrays.equals(pose, flowerDown2)) return "flowerDown2";
        if (Arrays.equals(pose, garden1)) return "garden1";
        if (Arrays.equals(pose, garden2)) return "garden2";
        if (Arrays.equals(pose, park)) return "park";

        return "unknown";
    }

    // pass through middle on:
    /*
     * up to down
     * down to flowerUp
     * up to flowerDown
     * up to garden
     */
}