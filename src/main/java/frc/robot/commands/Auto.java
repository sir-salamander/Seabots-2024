// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.LauncherConstants.kLaunchFeederSpeed;
import static frc.robot.Constants.LauncherConstants.kLauncherSpeed;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import frc.robot.Constants.LauncherConstants;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANLauncher;

public class Auto extends Command {
  /** Creates a new Auto. */
  private final CANLauncher m_launcher = new CANLauncher();

  private final CANDrivetrain m_Drivetrain = new CANDrivetrain();

  public Auto() {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_launcher, m_Drivetrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  private void launch() {
    new PrepareLaunch(m_launcher)
        .withTimeout(LauncherConstants.kLauncherDelay)
        .andThen(new LaunchNote(m_launcher))
        .handleInterrupt(() -> m_launcher.stop());
    new RunCommand(() -> m_launcher.setLaunchWheel(kLauncherSpeed), m_launcher)
        .withTimeout(1)
        .andThen(new RunCommand(() -> m_launcher.setFeedWheel(kLaunchFeederSpeed), m_launcher))
        .withTimeout(5)
        .andThen(new RunCommand(() -> m_launcher.setLaunchWheel(0), m_launcher))
        .alongWith(new RunCommand(() -> m_launcher.setFeedWheel(0), m_launcher));
  }

  private void drive() {
    new RunCommand(() -> m_Drivetrain.arcadeDrive(1, 0), m_Drivetrain)
        .withTimeout(5)
        .andThen(new RunCommand(() -> m_Drivetrain.arcadeDrive(0, 0), m_Drivetrain));
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    return;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
