// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import javax.sound.sampled.Port;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import com.revrobotics.*;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.math.filter.SlewRateLimiter;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

private CANSparkMax leftmotor1 = new CANSparkMax(10, MotorType.kBrushed);
private CANSparkMax leftmotor2 = new CANSparkMax(8, MotorType.kBrushed);
private CANSparkMax rightmotor1 = new CANSparkMax(11, MotorType.kBrushed);
private CANSparkMax rightmotor2 = new CANSparkMax(9, MotorType.kBrushed);

SlewRateLimiter ramp = new SlewRateLimiter(0.75, -0.75, 0);  // first two numbers are your accelaration as a % (just diff direction). DONT TOUCH THE LAST NUMBER

private DifferentialDrive drive = new DifferentialDrive(leftmotor1, rightmotor1); //creating the differential drive object with a left side and right side
double speed;
double turn;

private Joystick joy1 = new Joystick(0);

private double startTime; 


@Override
  public void robotInit() {
    leftmotor2.follow(leftmotor1) ;               //sets leftmotor2 to do everything leftmotor 1 does
    rightmotor2.follow(rightmotor1);              //sets rightmotor2 to do everything rightmotor 1 does
    rightmotor1.setInverted(true);     //flips the rightside so you don't need to multiply by -1
    rightmotor2.setInverted(true);
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {
    startTime= Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
    double time = Timer.getFPGATimestamp();
    if (time - startTime > 3) {
    leftmotor1.set(0.6);
    leftmotor2.set(0.6);
    rightmotor1.set(-0.6);
    rightmotor2.set(-0.6);
  } else {
    leftmotor1.set(0);
    leftmotor2.set(0);
    rightmotor1.set(0);
    rightmotor2.set(0);
  }
}
  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic(){
    speed = ramp.calculate(joy1.getRawAxis(1)); //calculating our ramp while reading our joystick
    turn = joy1.getRawAxis(0);
    
    
    drive.arcadeDrive(speed, turn);   //acrade drive passes in speed and rotation to your drive motors

    //THIS WAS YOUR OLD CODE
    // double left = speed+turn;
    // double right = speed-turn;

    // leftmotor1.set(left);
    // leftmotor2.set (left);
    // rightmotor1.set(-right);
    // rightmotor2.set(-right);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}
}
