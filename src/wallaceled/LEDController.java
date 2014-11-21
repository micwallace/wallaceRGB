/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallaceled;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import jssc.SerialPortException;

/**
 *
 * @author michael
 */
public class LEDController {

    public final int AMBMODE = 1;
    public final int MANMODE = 2;
    public final int SEQMODE = 3;
    private int curmode = AMBMODE; // current mode
    private Ambient amb; // ambient robot/controller
    private Sequencer seq; // sequence controller
    public SerialConnect serial; // serial connection object
    JPanel colorpanel; // GUI color panel
    private float brightness;
            
    public LEDController(JPanel cpanel) {
        brightness = new Float(1); // 0.1 - 1; 1==100%
        // color panel; passed from gui
        colorpanel = cpanel;
        // setup serial object
        serial = new SerialConnect();
        // setup robot
        amb = new Ambient(LEDController.this);
        // setup sequencer
        seq = new Sequencer(LEDController.this);
    }

    public void andGodSaidLetThereBeLight() {
        try {
            Thread.sleep(500); // gives time for serial to connect
        } catch (InterruptedException ex) {
            Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // start in ambience mode
        amb.startAmb();
    }
    
    public void applySettings(int[] intset, boolean fadeon, boolean cfade){
        // ambience stuff
        amb.setCaptureRate(intset[0]);
        amb.setPixelSkip(intset[1]);
        amb.setThreshold(intset[5]);
        amb.setFadeRate(intset[6]);
        amb.setFadeSpeed(intset[7]);
        // sequnce stuff
        seq.enableFade(fadeon);
        seq.Crossfade(cfade);
        seq.setInterval(intset[2]*1000); // change to seconds
        seq.setFadeRate(intset[3]);
        seq.setFadeSpeed(intset[4]);
    }
    
    public void lightsOff() {
        switch(curmode){
            case AMBMODE:
                amb.stopAmb();
                while (amb.isrunning) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case SEQMODE:
                seq.stopSequence();
                while (seq.faderun) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
        }
        powerOffLights();
    }

    public void lightsOn() {
        switch (curmode) {
            case MANMODE:
                setColor(currentcolor);
                break;
            case AMBMODE:
                amb.startAmb();
                break;
            case SEQMODE:
                seq.startSequence();
        }
    }

    private void powerOffLights() {
        try {
            serial.writedata((byte) (255));
            serial.writedata((byte) 0);
            serial.writedata((byte) 0);
            serial.writedata((byte) 0);
        } catch (SerialPortException ex) {
            Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setBrightness(int bright){
        brightness=Float.valueOf(Float.valueOf(bright)/10);
        if (currentcolor==null){ // use the color panel to set the manual color if null
                currentcolor = colorpanel.getBackground();
        }
        setLED(new int[]{Math.round(currentcolor.getRed()), Math.round(currentcolor.getGreen()), Math.round(currentcolor.getBlue())});
        System.out.println(brightness);
    }

    public int getMode() {
        return curmode;
    }
    
    public String[] getSequences(){
        return seq.getSequences();
    }
    
    public void setSequence(String name){
        seq.setSequence(name);
    }

    public void setMode(int mode) {
        switch (mode) {
            case AMBMODE:
                if (curmode == SEQMODE) {
                    seq.stopSequence();
                    while (seq.faderun) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                amb.startAmb();
                curmode = AMBMODE;
                break;
            case SEQMODE:
                if (curmode == AMBMODE) {
                    amb.stopAmb();
                    while (amb.isrunning) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                seq.startSequence();
                curmode = SEQMODE;
                break;
            case MANMODE:
                if (curmode == AMBMODE) {
                    amb.stopAmb();
                    while (amb.isrunning) {
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    seq.stopSequence();
                }
                currentcolor = colorpanel.getBackground(); // set the first manual color from the currently displayed
                curmode = MANMODE;
        }
        // setBrightness(10); This is set from the ui on mode change
    }
    // manual set color
    private Color currentcolor;
    public void setColor(Color color) { // should be used for manual selection only
        if (curmode != MANMODE) {
            setMode(MANMODE);
        }
        currentcolor = color;
        setColor(new int[]{color.getRed(), color.getGreen(), color.getBlue()});
    }
    public void setColor(int[] rgb){
        setLED(rgb);
        currentcolor = new Color(rgb[0], rgb[1], rgb[2]);
    }
    // set color used by ambient and sequence engine
    public void setLED(int[] rgb) {
        // apply brightness
        int i = 0;
        while(i<3){
            rgb[i]=Math.round(rgb[i]*brightness);
            i++;
        }
        //System.out.println(brightness);
        // pass to serial object
        try {
            serial.writedata((byte) (255));
            serial.writedata((byte) (rgb[0]));
            serial.writedata((byte) (rgb[1]));
            serial.writedata((byte) (rgb[2]));
        } catch (SerialPortException ex) {
            Logger.getLogger(Ambient.class.getName()).log(Level.SEVERE, null, ex);
        }
        // set in color panel
        colorpanel.setBackground(new Color(rgb[0], rgb[1], rgb[2]));
    }
}
