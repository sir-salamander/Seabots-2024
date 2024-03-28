// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANLauncher;

public final class Auto {
  /** Example static factory for an autonomous command. */
  public static Command exampleAuto(CANDrivetrain m_drivetrain, CANLauncher m_launcher) {
    //THIS CODE IS DRIVING ME INSANE INSTEAD OF DRIVING THE ROBOT
    
     return new SequentialCommandGroup(
      new ParallelCommandGroup(new RunCommand(() -> m_launcher.m_launchWheel.set(ControlMode.PercentOutput, 1)), new SequentialCommandGroup(
        new WaitCommand(1.5).andThen(new RunCommand(() -> m_launcher.m_feedWheel.set(ControlMode.PercentOutput, 1))).withTimeout(1)
      )).withTimeout(5),
    new RunCommand(() -> m_drivetrain.arcadeDrive(1, 0)).withTimeout(5)
    );
  }

  private Auto() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
