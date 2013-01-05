/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallaceled;

import java.awt.Color;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael control preset and custom loops
 */
public class Looper {

    private LEDController ledcontrol;
    // sequence stuff
    private Timer timer;
    private HashMap<String, ArrayList<Color>> sequences;
    private String currentseq;
    private int colorint;
    private seqT seqtask;

    public Looper(LEDController ledcon) {
        ledcontrol = ledcon;
        // create the sequence arraylist and add
        sequences = new HashMap<>();
        ArrayList defaultloop = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
        sequences.put("RGB", defaultloop);
        // set the default sequence
        currentseq = "RGB";
        // set default interval
        colorint = 5000;
        // setup the seqence timer
        timer = new Timer();
    }

    public void setInterval(int newint){
        colorint = newint;
        if (ledcontrol.getMode()==ledcontrol.SEQMODE){
            seqtask.cancel();
            seqrun = false;
            seqtask = new seqT();
            timer.scheduleAtFixedRate(seqtask, 0, colorint);
        }
    }

    public void startSequence() {
        // start fader if enabled
        if (enablefade){
            startFader();
        }
        seqtask = new seqT();
        timer.scheduleAtFixedRate(seqtask, 0, colorint);
    }

    public void stopSequence() {
        seqtask.cancel();
        seqrun = false;
        if (faderun){
            stopFader();
        }
    }

    public void setSequence(String name) {
        if (sequences.containsKey(name)) {
            stopSequence();
            activeseq = null;
            currentseq = name;
            startSequence();
        }
    }

    public void addSequence(String name, ArrayList<int[]> seq) {
        sequences.put(name, null);
    }
    // SEQUENCE TASK
    // sequence running indicator
    public boolean seqrun = false;
    private Iterator<Color> activeseq;

    public class seqT extends TimerTask {

        @Override
        public void run() {
            seqrun = true;
            // if new sequence; load it into arraylist
            if (activeseq == null) {
                activeseq = sequences.get(currentseq).iterator();
            }
            if (!activeseq.hasNext()) {
                activeseq = sequences.get(currentseq).iterator();
            }
            if (faderun) {
                setFadeColor(activeseq.next());
            } else {
                setColor(activeseq.next());
            }
        }
    }
    // FADE THREAD AND FUNCTIONS
    boolean initializing = true;
    private boolean stopfade = false;
    private boolean faderun = false;
    private int[] setcolor;
    private int[] curcolor;
    private int faderate = 5;
    private boolean crossfade = true;
    private boolean enablefade = true;
    private boolean dimcomplete;
    public void stopFader() {
        stopfade = true;
    }
    public void startFader() {
        if (!faderun) {
            fadeT fader = new fadeT();
            fader.start();
        }
    }
    public void enableFade(boolean fadeon){
        enablefade = fadeon;
        if (ledcontrol.getMode()==ledcontrol.SEQMODE){
            if (fadeon){
                startFader();
            } else {
                stopFader();
            }
        }
    }
    public void setFadeColor(Color color) {
        setcolor = new int[]{color.getRed(), color.getGreen(), color.getBlue()};
        dimcomplete = false;
    }
    public void setFadeRate(int rate) {
        faderate = rate;
    }
    public void Crossfade(boolean cfade){
        crossfade = cfade;
    }
    public class fadeT extends Thread {

        @Override
        public void run() {
            faderun = true;
            if (initializing) { // waiting for first value
                if (setcolor != null) { // set first value as current, set the color; initialization complete 
                    setColor(setcolor);
                    curcolor = setcolor;
                    initializing = false;
                }
            } else { //System.out.println("test3"); 
                if (Arrays.equals(setcolor, curcolor)) {
                    try {
                        // only perform fade when colors are not equal, otherwise sleep for 500
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Looper.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    doFadeStep();
                }
                //System.out.println(curcolor[0] + "-" + setcolor[0]+" " + curcolor[1] + "-" + setcolor[1] + " " + curcolor[2] + "-"+ setcolor[2]); 
            }
            // stop or continue; stop when fade is interrupted and fade complete 
            if (stopfade == true && Arrays.equals(setcolor, curcolor)) {
                stopfade = false; // fader has stopped
                faderun = false;
            } else {
                try {
                    Thread.sleep(20); // FADE SPEED
                } catch (InterruptedException ex) {
                    Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null,
                            ex);
                }
                run();
            }
        }
        private void doFadeStep() {
            int i = 0;
            // are we diming?
            if (!crossfade && !dimcomplete) {
                while (i < 3) {
                    // work out increments for each channel
                    curcolor[i] = (curcolor[i]-faderate<0?0:curcolor[i]-faderate);
                    i++;
                }
                dimcomplete = Arrays.equals(curcolor, new int[]{0,0,0});
            } else {
                while (i < 3) {
                    // work out increments for each channel
                    curcolor[i] = (curcolor[i] == setcolor[i] ? curcolor[i]
                            : (curcolor[i] < setcolor[i] ? (curcolor[i] + faderate <= setcolor[i] ? curcolor[i] + faderate
                            : setcolor[i]) : (curcolor[i] - faderate >= setcolor[i]
                            ? curcolor[i] - faderate : setcolor[i])));
                    i++;
                }
            }
            // set fade increment color
            //System.out.println(curcolor[0]+"-"+curcolor[1]+"-"+curcolor[2]+"-");
            setColor(curcolor);
        }
    }

    private void setColor(Color color) {
        // pass to controller object
        ledcontrol.setColor(new int[]{color.getRed(), color.getGreen(), color.getBlue()});
        // ledcontrol.setFadeColor(rgb); use fader
    }

    private void setColor(int[] rgb) {
        ledcontrol.setColor(rgb);
    }
}
