/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

import java.util.function.DoubleSupplier;

public class SetRocketDrive extends CommandBase {

  private DriveTrain driveTrain;
  private DoubleSupplier left;
  private DoubleSupplier right;
  private DoubleSupplier turn;

  /**
   * Creates a new SetRocketDrive.
   */
  public SetRocketDrive(DriveTrain drive, DoubleSupplier leftTrigger, DoubleSupplier rightTrigger, DoubleSupplier turn) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveTrain = drive;
    this.left = leftTrigger;
    this.right = rightTrigger;
    this.turn = turn;
    addRequirements(drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double throttle = left.getAsDouble() > 0 ? -left.getAsDouble() : right.getAsDouble();
    double turnVal = turn.getAsDouble();

    double leftOut = throttle + turnVal;
    double rightOut = throttle - turnVal;
    driveTrain.setMotorPercentOutput(leftOut, rightOut);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
