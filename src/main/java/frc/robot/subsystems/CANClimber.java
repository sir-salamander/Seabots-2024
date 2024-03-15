// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.LauncherConstants.*;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANClimber extends SubsystemBase {
  /** Creates a new CANClimber. */
  CANSparkMax m_climber;

  public CANClimber() {
    m_climber = new CANSparkMax(8, MotorType.kBrushless);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void stop() {
    m_climber.set(0);
  }

  public Command ClimbUp() {
    return this.startEnd(
        () -> {
          m_climber.set(kclimberUp);
        },
        () -> {
          stop();
        });
  }

  public Command ClimbDown() {
    return this.startEnd(
        () -> {
          m_climber.set(kclimberDown);
        },
        () -> {
          stop();
        });
  }
}
