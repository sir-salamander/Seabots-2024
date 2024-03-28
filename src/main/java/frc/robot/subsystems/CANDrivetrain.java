// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/* This class declares the subsystem for the robot drivetrain if controllers are connected via CAN. Make sure to go to
 * RobotContainer and uncomment the line declaring this subsystem and comment the line for PWMDrivetrain.
 *
 * The subsystem contains the objects for the hardware contained in the mechanism and handles low level logic
 * for control. Subsystems are a mechanism that, when used in conjuction with command "Requirements", ensure
 * that hardware is only being used by 1 command at a time.
 */
public class CANDrivetrain extends SubsystemBase {
  /*Class member variables. These variables represent things the class needs to keep track of and use between
  different method calls. */
  public DifferentialDrive m_drivetrain;

  AHRS ahrs;


  /*Constructor. This method is called when an instance of the class is created. This should generally be used to set up
   * member variables and perform any configuration or set up necessary on hardware.
   */
  public CANDrivetrain() {

    WPI_VictorSPX leftFront = new WPI_VictorSPX(1);
    WPI_VictorSPX leftRear = new WPI_VictorSPX(2);
    WPI_VictorSPX rightRear = new WPI_VictorSPX(4);

    CANSparkMax rightFront = new CANSparkMax(8, MotorType.kBrushed);

    rightFront.setSmartCurrentLimit(30);
    //  *at stall (Drivetrain pushing against something) and helps maintain battery voltage under
    // heavy demand */

    // Sets current limits for the drivetrain motors. This helps reduce the likelihood of wheel
    // spin, reduces motor heating

    // Set the rear motors to follow the front motors.
    leftRear.follow(leftFront);
    MotorControllerGroup m_right = new MotorControllerGroup(rightFront, rightRear);
    // Invert the left side so both side drive forward with positive motor outputs
    leftFront.setInverted(true);
    rightFront.setInverted(true);

    // Put the front motors into the differential drive object. This will control all 4 motors with
    // the rears set to follow the fronts
    m_drivetrain = new DifferentialDrive(leftFront, m_right);

    ahrs = new AHRS(SPI.Port.kMXP);
  }

  /*Method to control the drivetrain using arcade drive. Arcade drive takes a speed in the X (forward/back) direction
   * and a rotation about the Z (turning the robot about it's center) and uses these to control the drivetrain motors */
  public void arcadeDrive(double speed, double rotation) {
    m_drivetrain.arcadeDrive(Math.min(speed, 0.8), rotation);
  }

  @Override
  public void periodic() {
    /*This method will be called once per scheduler run. It can be used for running tasks we know we want to update each
     * loop such as processing sensor data. Our drivetrain is simple so we don't have anything to put here */
    arcadeDrive(0, 0);
    SmartDashboard.putNumber("Speed", ahrs.getRawAccelY());
  }
}
