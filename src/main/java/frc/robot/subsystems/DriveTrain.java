/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

// Based on team 4201's 2020 drivetrain
public class DriveTrain extends SubsystemBase {

  private TalonFX[] driveMotors = {
          new TalonFX(Constants.leftFrontDriveMotor),
          new TalonFX(Constants.leftRearDriveMotor),
          new TalonFX(Constants.rightFrontDriveMotor),
          new TalonFX(Constants.rightRearDriveMotor)
  };

  /**
   * Creates a new DriveTrain.
   */
  public DriveTrain() {
    for (TalonFX motor : driveMotors) {
      motor.configFactoryDefault();
      motor.configSupplyCurrentLimit(new SupplyCurrentLimitConfiguration(true, 30, 0, 0));
      motor.configOpenloopRamp(0.1);
      motor.configClosedloopRamp(0.1);
      motor.setNeutralMode(NeutralMode.Coast);
      motor.configForwardSoftLimitEnable(false);
      motor.configReverseSoftLimitEnable(false);
    }

    driveMotors[0].setInverted(true);
    driveMotors[1].setInverted(true);
    driveMotors[2].setInverted(false);
    driveMotors[3].setInverted(false);

    driveMotors[0].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    driveMotors[2].configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    driveMotors[0].setSensorPhase(false);
    driveMotors[2].setSensorPhase(false);

    driveMotors[1].set(ControlMode.Follower, driveMotors[0].getDeviceID());
    driveMotors[3].set(ControlMode.Follower, driveMotors[2].getDeviceID());
    driveMotors[1].setNeutralMode(NeutralMode.Brake);
    driveMotors[3].setNeutralMode(NeutralMode.Brake);

    driveMotors[1].configOpenloopRamp(0);
    driveMotors[3].configOpenloopRamp(0);
  }

  public void setMotorPercentOutput(double leftOut, double rightOut) {
    driveMotors[0].set(ControlMode.PercentOutput, leftOut);
    driveMotors[2].set(ControlMode.PercentOutput, rightOut);
  }

  public void setMotorArcadeDrive(double throttle, double turn) {
    double leftOut = throttle + turn;
    double rightOut = throttle - turn;
    setMotorPercentOutput(leftOut, rightOut);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
