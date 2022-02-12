package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * @author Toby Leeder
 */
public class StatusLights extends SubsystemBase{
    
    public AddressableLED m_led = new AddressableLED(Constants.LEDs.LEDport);
    public AddressableLEDBuffer m_ledBuffer = new AddressableLEDBuffer(60);
    
    public StatusLights(){
    m_led.setLength(m_ledBuffer.getLength());
    m_led.setData(m_ledBuffer);
    m_led.start();
    }
    
    public void setLight(int Red, int Blue, int Green){
        for(int i = 0; i < m_ledBuffer.getLength(); i++){
            m_ledBuffer.setRGB(i, Red, Blue, Green);
        }
    }
    public void setOptixColors(){
        for(int i = 0; i < m_ledBuffer.getLength(); i++){
            if (i == 1 || i == m_ledBuffer.getLength()-1) m_ledBuffer.setRGB(i, 11, 188, 208);
            else if (i < m_ledBuffer.getLength()*8/20 && i > m_ledBuffer.getLength()*7/20) m_ledBuffer.setRGB(i, 0, 0, 0);
            else if (i > m_ledBuffer.getLength()*12/20 && i < m_ledBuffer.getLength()*13/20) m_ledBuffer.setRGB(i, 0, 0, 0);
            else m_ledBuffer.setRGB(i, 178, 242, 24);
        }
    }
    public void setOff(){
        for(int i = 0; i < m_ledBuffer.getLength(); i++) m_ledBuffer.setRGB(i, 0, 0, 0);
    }
}
