// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static frc.robot.Constants.LauncherConstants.*;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANGrabber extends SubsystemBase {
  /** Creates a new CANGrabber. */
  CANSparkMax m_grabber;

  public CANGrabber() {
    m_grabber = new CANSparkMax(7, MotorType.kBrushed);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private void stop() {
    m_grabber.set(0);
  }

  public Command GrabNote() {
    return this.startEnd(
        () -> {
          m_grabber.set(kGrabIntakeSpeed);
        },
        () -> {
          stop();
        });
  }

  public Command ReleaseNote() {
    return this.startEnd(
        () -> {
          m_grabber.set(kGrabReleaseSpeed);
        },
        () -> {
          stop();
        });
  }
}
