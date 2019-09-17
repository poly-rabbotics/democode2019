package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Joystick;

public class Climb {
    private DoubleSolenoid back, front;
    VictorSP lowWheel1, lowWheel2;
    Joystick joystick;
    int cycleCount;
    public Climb(DoubleSolenoid back, DoubleSolenoid front, VictorSP lowWheel1, VictorSP lowWheel2, Joystick joystick, int backButtonChannel, int frontButtonChannel) {
        this.back = back;
        this.front = front;
        this.lowWheel1 = lowWheel1;
        this.lowWheel2 = lowWheel2;
        this.joystick = joystick;
        cycleCount = 0;
    }
    public void raiseFront() {
        front.set(Value.kForward);
    }
    public void raiseBack() {
        back.set(Value.kForward);
    }
    public void lowerFront() {
        front.set(Value.kReverse);
    }
    public void lowerBack() {
        back.set(Value.kReverse);
    }
    public void driveForward() {
        lowWheel1.set(0.7);
        lowWheel2.set(0.7);
    }
    public void stop() {
        lowWheel1.set(0);
        lowWheel2.set(0);
    }
    public void autoRaise(double period, double dutyCycleBack, double dutyCycleFront) {
        cycleCount++;
        int cyclesPerPeriod = (int) (period / 0.02);
        double proportionThroughPeriod = (double) cycleCount / cyclesPerPeriod; // This is the proportion of the way we are in time through a period of oscillation of solenoid input
        if (proportionThroughPeriod >= 1) {
            cycleCount = 0;
        }
        if (proportionThroughPeriod < dutyCycleBack) {
            raiseBack();
            System.out.println("RB");
        } else {
            lowerBack();
            System.out.println("LB");
        }
        if (proportionThroughPeriod < dutyCycleFront) {
            raiseFront();
            System.out.println("RF");
        } else {
            lowerFront();
            System.out.println("LF");
        }
    }
    public void run() {
        if(joystick.getRawButton(6)){// right bumper
            autoRaise(0.25, 0.8, 1);
        }
        else {
          lowerBack();
        }//holding deploys back lift
      
        if(joystick.getRawButton(5)){// left bumper
          raiseFront();
        }
        else {
          lowerFront();
        }//holding deploys front lift

        if(joystick.getRawButton(4) && joystick.getRawButton(6) ){ 
            driveForward();
        }
        else {
            stop();
        } //if back lift is deployed and Y is pressed, Y drives low wheels forward
    }
}