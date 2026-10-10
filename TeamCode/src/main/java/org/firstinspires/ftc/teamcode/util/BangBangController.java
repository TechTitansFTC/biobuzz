package org.firstinspires.ftc.teamcode.util;

public class BangBangController {
    private double tolerance;
    private double setpoint;
    private double measurement;

    public BangBangController() {
        this(Double.POSITIVE_INFINITY);
    }

    public BangBangController(double tolerance) {
        this.tolerance = tolerance;
    }

    public double calculate(double measurement, double setpoint) {
        this.measurement = measurement;
        this.setpoint = setpoint;
        return measurement < setpoint ? 1.0 : 0.0;
    }

    public boolean atSetpoint() {
        return Math.abs(setpoint - measurement) < tolerance;
    }

    public void setTolerance(double tolerance) {
        this.tolerance = tolerance;
    }

    public double getError() {
        return setpoint - measurement;
    }
}
