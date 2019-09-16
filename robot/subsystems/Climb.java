package frc.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.Joystick;

public class Climb {
    private DoubleSolenoid back, front;
    VictorSP lowWheel1, lowWheel2;
    int cycleCount;
    public Climb(DoubleSolenoid back, DoubleSolenoid front, VictorSP lowWheel1, VictorSP lowWheel2, Joystick joystick, int backButtonChannel, int frontButtonChannel) {
        this.back = back;
        this.front = front;
        this.lowWheel1 = lowWheel1;
        this.lowWheel2 = lowWheel2;
        cycleCount = 0;
    }
    public raiseFront() {
        liftFront.set(Value.kForward);
    }
    public raiseBack() {
        liftBack.set(Value.kForward);
    }
    public lowerFront() {
        liftFront.set(Value.kReverse);
    }
    public lowerBack() {
        liftBack.set(Value.kReverse);
    }
    public driveForward() {
        lowWheel1.set(0.7);
        lowWheel2.set(0.7);
    }
    public stop() {
        lowWheel1.set(0);
        lowWheel2.set(0);
    }
    public autoRaise(double period, double dutyCycleBack, double dutyCycleFront) {
        cycleCount++;
        int cyclesPerPeriod = period / 0.02;
        double proportionThroughPeriod = (double) cycleCount / cyclesPerPeriod; // This is the proportion of the way we are in time through a period of oscillation of solenoid input
        if (proportionThroughPeriod >= 1) {
            cycleCount = 0;
        }
        if (proportionThroughPeriod < dutyCycleBack) {
            raiseBack();
        } else {
            lowerBack();
        }
        if (proportionThroughPeriod > dutyCycleFront) {
            lowerFront();
        }
    }
    public run() {
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

        if(joystick.getRawButton(4) && joy0.getRawButton(6) ){ 
            driveForward();
        }
        else {
            stop();
        } //if back lift is deployed and Y is pressed, Y drives low wheels forward
    }
}