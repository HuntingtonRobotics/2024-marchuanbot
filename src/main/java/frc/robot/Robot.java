// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardLayout;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.networktables.GenericEntry;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

/**
 * This is a demo program showing the use of the DifferentialDrive class. Runs the motors with split
 * arcade steering and an Xbox controller.
 */
public class Robot extends TimedRobot {

  private final WPI_TalonSRX m_leftMotor = new WPI_TalonSRX(6);
  private final WPI_TalonSRX m_rightMotor = new WPI_TalonSRX(5);
  private final DifferentialDrive m_robotDrive =
      new DifferentialDrive(m_leftMotor::set, m_rightMotor::set);
  private final XboxController m_driverController = new XboxController(0);
  private GenericEntry m_maxSpeed;

  public Robot() {
    SendableRegistry.addChild(m_robotDrive, m_leftMotor);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor);
  }

  @Override
  public void robotInit() {
    // We need to invert one side of the drivetrain so that positive voltages
    // result in both sides moving forward. Depending on how your robot's
    // gearbox is constructed, you might have to invert the left side instead.
    m_rightMotor.setInverted(true);

    m_maxSpeed =
    Shuffleboard.getTab("Configuration")
        .add("Max Speed", 1)
        .withWidget("Number Slider")
        .withPosition(1, 1)
        .withSize(2, 1)
        .getEntry();
  }

  @Override
  public void teleopPeriodic() {
    // Drive with split arcade drive.
    // That means that the Y axis of the left stick moves forward
    // and backward, and the X of the right stick turns left and right.
    m_robotDrive.setMaxOutput(m_maxSpeed.getDouble(1.0));
    m_robotDrive.arcadeDrive(-m_driverController.getLeftY(), -m_driverController.getLeftX());
  }
}
