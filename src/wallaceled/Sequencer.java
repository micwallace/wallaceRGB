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
public class Sequencer {

    private LEDController ledcontrol;
    // sequence stuff
    private Timer timer;
    private HashMap<String, ArrayList<Color>> sequences;
    private String currentseq;
    private int colorint;
    private seqT seqtask;
    public int[] curseqcolor;
    fadeT fader;

    public Sequencer(LEDController ledcon) {
        ledcontrol = ledcon;
        // create the sequence arraylist and put presets
        sequences = new HashMap<>();
        loadPresetSequences();
        // set the default sequence
        currentseq = "RGB";
        // set default interval
        colorint = 10000;
        // setup the seqence timer
        timer = new Timer();
    }

    public void setInterval(int newint) {
        colorint = newint;
        if (ledcontrol.getMode() == ledcontrol.SEQMODE) {
            seqtask.cancel();
            seqrun = false;
            seqtask = new seqT();
            timer.scheduleAtFixedRate(seqtask, 0, colorint);
        }
    }

    public void startSequence() {
        // start fader if enabled
        if (enablefade) {
            startFader();
        }
        startSeqTask();
    }

    public void stopSequence() {
        stopSeqTask();
        stopFader();
        activeseq = null;
    }

    private void startSeqTask() {
        seqtask = new seqT();
        timer.scheduleAtFixedRate(seqtask, 0, colorint);
    }

    private boolean stopSeqTask() {
        if (seqrun) {
            seqtask.cancel();
            seqrun = false;
            return true;
        }
        return false;
    }

    public void setSequence(String name) {
        if (sequences.containsKey(name)) {
            boolean taskstopped = stopSeqTask();
            activeseq = null;
            currentseq = name;
            if (taskstopped) {
                startSeqTask();
            }
        }
    }

    public String[] getSequences() {
        Object[] tempobj = sequences.keySet().toArray();
        String[] seqnames = Arrays.copyOf(tempobj, tempobj.length, String[].class);
        return seqnames;
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
    private boolean stopfade = false;
    public boolean faderun = false;
    public boolean doingfade = false;
    private float[] targetcolor;
    private float[] curcolor;
    private int faderate = 2; // fade increment
    private int fadeint = 50; // ms between each shade
    private boolean crossfade = true;
    private boolean enablefade = true;
    private boolean dimcomplete;

    public void stopFader() {
        stopfade = true;
        fader.interrupt();
    }

    public void startFader() {
        if (!faderun) {
            curcolor = null;
            curseqcolor = null;
            fader = new fadeT();
            fader.start();
        }
    }

    public void enableFade(boolean fadeon) {
        enablefade = fadeon;
        if (ledcontrol.getMode() == ledcontrol.SEQMODE) {
            if (fadeon) {
                startFader();
            } else {
                stopFader();
            }
        }
    }

    public void setFadeColor(Color color) {
        targetcolor = new float[]{color.getRed(), color.getGreen(), color.getBlue()};
        curseqcolor = new int[]{color.getRed(), color.getGreen(), color.getBlue()};
        dimcomplete = false;
    }

    public void setFadeRate(int rate) {
        faderate = rate;
    }

    public void setFadeSpeed(int speed) {
        fadeint = speed;
    }

    public void Crossfade(boolean cfade) {
        crossfade = cfade;
    }

    public class fadeT extends Thread {

        private float[] target;
        private float[] curint;
        boolean initializing = true;

        @Override
        public void run() {
            faderun = true;
            curint = new float[]{1, 1, 1};
            while (faderun == true) {
                if (initializing) { // waiting for first value
                    if (targetcolor != null) { // set first value as current, set the color; initialization complete 
                        setColor(targetcolor);
                        curcolor = targetcolor;
                        this.target = new float[]{0, 0, 0}; // so we know when it has been changed and can recalculate intervals
                        initializing = false;
                    }
                } else { //System.out.println("test3"); 
                    if (Arrays.equals(targetcolor, curcolor)) {
                        try {
                            // only perform fade when colors are not equal, otherwise sleep for 10
                            Thread.sleep(10);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Sequencer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        doingfade = false;
                    } else {
                        doFadeStep();
                        doingfade = true;
                    }
                    //System.out.println(curcolor[0] + "-" + setcolor[0]+" " + curcolor[1] + "-" + setcolor[1] + " " + curcolor[2] + "-"+ setcolor[2]); 
                }
                // stop or continue; stop when fade is interrupted and fade complete 
                if (stopfade == true && Arrays.equals(targetcolor, curcolor)) {
                    stopfade = false; // fader has stopped
                    faderun = false;
                    this.interrupt();
                } else {
                    try {
                        Thread.sleep(fadeint); // FADE SPEED
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    //run();
                }
            }
        }

        private void doFadeStep() {
            int i = 0;
            // are we diming?
            if (!crossfade && !dimcomplete) {
                while (i < 3) {
                    // work out increments for each channel
                    curcolor[i] = (curcolor[i] - faderate < 0 ? 0 : curcolor[i] - faderate);
                    i++;
                }
                dimcomplete = Arrays.equals(curcolor, new float[]{0, 0, 0});
            } else {
                i = 0;
                // apply interval to each channel
                while (i < 3) {
                    // new taget color? work out new relative intervals
                    if (target[i] != targetcolor[i]) {
                        float diff = targetcolor[i] - curcolor[i];
                        diff = (diff < 0 ? -diff : diff);
                        curint[i] = (diff / 100) * faderate;
                        System.out.println("test");
                        target[i] = targetcolor[i];
                    }

                    // work out increments for each channel
                    curcolor[i] = (Math.round(curcolor[i]) == Math.round(targetcolor[i]) ? targetcolor[i]
                            : (curcolor[i] < targetcolor[i] ? (curcolor[i] + curint[i] <= targetcolor[i] ? curcolor[i] + curint[i]
                            : targetcolor[i]) : (curcolor[i] - curint[i] >= targetcolor[i]
                            ? curcolor[i] - curint[i] : targetcolor[i])));
                    i++;
                }
            }
            // set fade increment color
            //System.out.println(curint[0]+"-"+curint[1]+"-"+curint[2]);
            //System.out.println(curcolor[0]+"-"+curcolor[1]+"-"+curcolor[2]+"-");
            setColor(curcolor);
        }
    }

    private void setColor(Color color) {
        // pass to controller object
        curseqcolor = new int[]{color.getRed(), color.getGreen(), color.getBlue()};
        ledcontrol.setColor(new int[]{color.getRed(), color.getGreen(), color.getBlue()});
        // ledcontrol.setFadeColor(rgb); use fader
    }

    private void setColor(float[] rgb) {
        ledcontrol.setColor(new int[]{Math.round(rgb[0]), Math.round(rgb[1]), Math.round(rgb[2])});
    }

    private void loadPresetSequences() {
        ArrayList temploop = new ArrayList<>(Arrays.asList(Color.CYAN, Color.MAGENTA, Color.YELLOW));
        sequences.put("CMY-NO-K", temploop);
        temploop = new ArrayList<>(Arrays.asList(Color.RED, Color.GREEN, Color.BLUE));
        sequences.put("RGB", temploop);
        temploop = new ArrayList<>(Arrays.asList(Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.BLUE, Color.MAGENTA));
        sequences.put("RGB Rainbow", temploop);
        temploop = new ArrayList<>(Arrays.asList(Color.RED, new Color(255, 127, 0), Color.YELLOW, Color.GREEN, Color.BLUE, new Color(102, 0, 255), new Color(139, 0, 255)));
        sequences.put("True Rainbow", temploop);
    }
}
