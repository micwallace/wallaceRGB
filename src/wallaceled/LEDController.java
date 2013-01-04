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
    public final int LOOPMODE = 3;
    private int curmode = AMBMODE; // current mode
    int ambfade = 5; // rate of fade for the ambient effect
    private Ambient amb; // ambient robot/controller
    public SerialConnect serial; // serial connection object
    JPanel colorpanel; // GUI color panel

    public LEDController(JPanel cpanel) {
        // color panel; passed from gui
        colorpanel = cpanel;
        // setup serial object
        serial = new SerialConnect();
        // setup robot
        amb = new Ambient(LEDController.this);
    }

    public void andGodSaidLetThereBeLight() {
        // start in ambience mode
        amb.startAmb();
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
                amb.startAmb();
                curmode = AMBMODE;
                break;
            case MANMODE:
                amb.stopAmb();
                curmode = MANMODE;
                // turn off fader loop (graceful exit)
                // stopFader();
                try {
                    Thread.sleep(300); // give the thread a momoent to shutdown
                } catch (InterruptedException ex) {
                    Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                amb.stopAmb();  // switch to manual mode by default
                curmode = MANMODE;
        }
    }
    // manual set color
    private Color currentcolor;
    public void setColor(Color color) {
        if (curmode != MANMODE) {
            setMode(MANMODE);
        }
        currentcolor = color;
        setColor(new int[]{color.getRed(), color.getGreen(), color.getBlue()});
    }
    // set color used by ambient fader engine

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
    // FADER STUFF TBC
    /*
     * private boolean stopfade = false; private boolean faderun = false;
     * private int[] setcolor; private int[] curcolor; private int faderate =
     * 20;
     *
     * public void stopFader() { stopfade = true; }
     *
     * public void startFade() { if (!faderun) { fadeT fader = new fadeT();
     * fader.start(); } }
     *
     * public void setFadeColor(int[] rgb) { setcolor = rgb; }
     *
     * public void setFadeRate(int rate) { faderate = rate; }
     *
     * public class fadeT extends Thread { boolean initializing = true;
     * @Override public void run() { faderun = true; // loop until stop is
     * called and values are equal if (initializing) { // checking for first
     * value if (setcolor != null) { // set first value as current, set the
     * color; initialization complete setColor(setcolor); curcolor = setcolor;
     * initializing = false; } } else { //System.out.println("test3"); // work
     * out increments for each channel int i = 0; while (i < 3) { curcolor[i] =
     * (curcolor[i] == setcolor[i] ? curcolor[i] : (curcolor[i] < setcolor[i] ?
     * (curcolor[i] + faderate <= setcolor[i] ? curcolor[i]+faderate :
     * setcolor[i]) : (curcolor[i] - faderate >= setcolor[i] ?
     * curcolor[i]-faderate : setcolor[i]))); i++; } // set fade increment color
     * setColor(curcolor); //System.out.println(curcolor[0] + "-" + setcolor[0]
     * + " " + curcolor[1] + "-" + setcolor[1] + " " + curcolor[2] + "-" +
     * setcolor[2]); } // stop or continue if (stopfade == true &&
     * Arrays.equals(setcolor, curcolor)) { stopfade = false; // fader has
     * stopped faderun = false; } else { try { Thread.sleep(10); } catch
     * (InterruptedException ex) {
     * Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null,
     * ex); } run(); System.gc(); } }
    }
     */
}
