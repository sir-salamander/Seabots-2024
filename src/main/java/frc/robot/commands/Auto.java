// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import static frc.robot.Constants.LauncherConstants.kLaunchFeederSpeed;
import static frc.robot.Constants.LauncherConstants.kLauncherSpeed;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANLauncher;

public final class Auto {
  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(CANDrivetrain m_drivetrain, CANLauncher m_launcher) {
    /**
     * RunCommand is a helper class that creates a command from a single method, in this case we
     * pass it the arcadeDrive method to drive straight back at half power. We modify that command
     * with the .withTimeout(1) decorator to timeout after 1 second, and use the .andThen decorator
     * to stop the drivetrain after the first command times out
     */
    
    return new SequentialCommandGroup(new WaitCommand(2.5),
                                      new RunCommand(() -> m_launcher.m_launchWheel.set(ControlMode.PercentOutput, 1), m_launcher).withTimeout(2.5),
                                      new RunCommand(() -> m_launcher.m_feedWheel.set(ControlMode.PercentOutput, 1), m_launcher).withTimeout(1),
                                      new WaitCommand(1),
                                      new RunCommand(() -> m_drivetrain.arcadeDrive(0, 0.8), m_drivetrain).withTimeout(5)
                                      );


    // return
    //   new ModifiedWaitCommand().deadlineWith(
    //     new WaitCommand(2.5)
    //       .andThen(new RunCommand(() -> m_launcher.m_feedWheel.set(ControlMode.PercentOutput, 1))),
    //     new RunCommand(() -> m_launcher.m_launchWheel.set(ControlMode.PercentOutput, 1))
    //   )
    //   .andThen(new RunCommand(() -> m_drivetrain.arcadeDrive(0.5, 0)).withTimeout(5));
  }

  private Auto() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}