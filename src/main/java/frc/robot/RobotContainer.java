// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.LauncherConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Auto;
import frc.robot.commands.LaunchNote;
import frc.robot.commands.PrepareLaunch;
import frc.robot.subsystems.CANClimber;
import frc.robot.subsystems.CANDrivetrain;
import frc.robot.subsystems.CANGrabber;
import frc.robot.subsystems.CANLauncher;

// import frc.robot.subsystems.CANDrivetrain;
// import frc.robot.subsystems.CANLauncher;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems are defined here.
  // private final PWMDrivetrain m_drivetrain = new PWMDrivetrain();
  private final CANDrivetrain m_drivetrain = new CANDrivetrain();
  // private final PWMLauncher m_launcher = new PWMLauncher();
  private final CANLauncher m_launcher = new CANLauncher();

  private final CANGrabber m_grabber = new CANGrabber();

  private final CANClimber m_climber = new CANClimber();

  /*The gamepad provided in the KOP shows up like an XBox controller if the mode switch is set to X mode using the
   * switch on the top.*/
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be accessed via the
   * named factory methods in the Command* classes in edu.wpi.first.wpilibj2.command.button (shown
   * below) or via the Trigger constructor for arbitary conditions
   */
  private void configureBindings() {
    // Set the default command for the drivetrain to drive using the joysticks
    m_drivetrain.setDefaultCommand(
        new RunCommand(
            () ->
                m_drivetrain.arcadeDrive(
                    -m_driverController.getRightX(), -m_driverController.getLeftY()),
            m_drivetrain));

    /*Create an inline sequence to run when the operator presses and holds the A (green) button. Run the PrepareLaunch
     * command for 1 seconds and then run the LaunchNote command */
    m_operatorController
        .button(1)
        .whileTrue(
            new PrepareLaunch(m_launcher)
                .withTimeout(LauncherConstants.kLauncherDelay)
                .andThen(new LaunchNote(m_launcher))
                .handleInterrupt(() -> m_launcher.stop()));

    // Set up a binding to run the intake command while the operator is pressing and holding the
    // left Bumper
    m_operatorController.button(2).whileTrue(m_launcher.getIntakeCommand());
    // grab note
    m_operatorController.button(4).whileTrue(m_grabber.GrabNote());
    // release note
    m_operatorController.button(3).whileTrue(m_grabber.ReleaseNote());
    // climb up
    m_operatorController.pov(0).whileTrue(m_climber.ClimbUp());
    // climb down
    m_operatorController.pov(180).whileTrue(m_climber.ClimbDown());
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // shooting part
    return Auto.exampleAuto(m_drivetrain, m_launcher);

  }
}
