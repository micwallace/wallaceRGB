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
    private Looper seq; // sequence controller
    public SerialConnect serial; // serial connection object
    JPanel colorpanel; // GUI color panel

    public LEDController(JPanel cpanel) {
        // color panel; passed from gui
        colorpanel = cpanel;
        // setup serial object
        serial = new SerialConnect();
        // setup robot
        amb = new Ambient(LEDController.this);
        // setup sequencer
        seq = new Looper(LEDController.this);
    }

    public void andGodSaidLetThereBeLight() {
        // start in ambience mode
        amb.startAmb();
    }
    
    public void applySettings(int[] intset, boolean fadeon, boolean cfade){
        // ambience stuff
        amb.setCaptureRate(intset[0]);
        amb.setPixelSkip(intset[1]);
        // sequnce stuff
        seq.enableFade(fadeon);
        seq.Crossfade(cfade);
        seq.setInterval(intset[2]*1000); // change to seconds
        seq.setFadeRate(intset[3]);
    }
    
    public void lightsOff() {
        if (curmode == this.MANMODE) {
            powerOffLights();
        } else {
            amb.stopAmb();
            while (amb.isrunning) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ex) {
                    Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            powerOffLights();
        }
    }

    public void lightsOn() {
        if (curmode == this.MANMODE) {
            setColor(currentcolor);
        } else {
            amb.startAmb();
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

    public int getMode() {
        return curmode;
    }

    public void setMode(int mode) {
        switch (mode) {
            case AMBMODE:
                if (curmode == SEQMODE) {
                    seq.stopSequence();
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
                curmode = MANMODE;
        }
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
    // set color used by ambient and sequence engine
    public void setColor(int[] rgb) {
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
