/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystem;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import frc.robot.Constants;
/**
 * Add your docs here.
 */
public class Intake {

    private PWMVictorSPX mball_intake;
    private static Intake m_Instance;
    private DoubleSolenoid hatch;

    public void setup(){
        mball_intake = new PWMVictorSPX(Constants.ballIntakeID);
        hatch = new DoubleSolenoid(Constants.hatch1, Constants.hatch2);
        setHatch(true);   //default the hatch

    }
    private Intake(){
        setup();
    } 

    public static Intake getInstance(){
        if (m_Instance == null) {
            m_Instance = new Intake();
        }
        return m_Instance;
    }

    public void setBallIntake(double ball){
        mball_intake.set(ball); 
    }

    //hatch Intake
    public void setHatch(boolean hDirection ){
        if(hDirection){
            hatch.set(DoubleSolenoid.Value.kForward);
        }
        else if(!hDirection){
            hatch.set(DoubleSolenoid.Value.kReverse);
        }
    }

    public void update(){
    }

}
