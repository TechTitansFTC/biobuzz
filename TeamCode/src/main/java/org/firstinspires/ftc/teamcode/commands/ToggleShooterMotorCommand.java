package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.util.Constants.shooterMotorPower;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.ShooterSubsystem;

public class ToggleShooterMotorCommand extends CommandBase {
    private final ShooterSubsystem shooterSubsystem;

    public ToggleShooterMotorCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        if (shooterSubsystem.getMotorPower() == shooterMotorPower) {
            shooterSubsystem.setMotorPower(0);
        } else {
            shooterSubsystem.setMotorPower(shooterMotorPower);
        }
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
