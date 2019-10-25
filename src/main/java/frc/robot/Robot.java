/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.subsystem.Drive;
import frc.robot.subsystem.Intake;
import frc.robot.subsystem.Arm;
import frc.robot.OI;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //Initalize joysticks
  private Joystick driver = new Joystick(0);
  private Joystick operator = new Joystick(1);

  //Initalize Operator Interface
  private OI operatorInterface = new OI();

  //Initalize Subsystems
  private Drive drive = Drive.getInstance();
  private Intake intake = Intake.getInstance();
  private Arm arm = Arm.getInstance();

  //Initalize Compressor 
  private Compressor compressor = new Compressor(0);
 
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    compressor.setClosedLoopControl(true);
    
    //Setup Smart Dashboard display
    SmartDashboard.putNumber("ArmEncoder Distance", 0);
    SmartDashboard.putNumber("ArmEncoder Rate", 0);

    intake.setHatch(true); //default hatch grabber to open
    arm.setArmPosition(0); //default arm position 0
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    //If robot is disable, STOP the arm PID (otherwise it will go full speed when you re-enable)
    if(DriverStation.getInstance().isDisabled()){
      arm.disableArmPID();
    }
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    //reset encoder to 0
    arm.armEncoderReset();

    //default arm to position 0 
    arm.setArmPosition(0);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() { //Sandstorm, not a true auton 
    drive.update();
    arm.update();
    intake.update();
    operatorInterface.driverControl(driver); // driver control 
    operatorInterface.operatorControl(operator); // operator control
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    drive.update();
    arm.update();
    intake.update();
    operatorInterface.driverControl(driver); // driver control 
    operatorInterface.operatorControl(operator); // operator control
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
