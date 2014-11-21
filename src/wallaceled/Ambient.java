/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallaceled;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author michael This is the class that captures screen content, works out RGB
 * averages and writes them to the serial port
 */
public class Ambient {

    Robot robby; //creates object "robby" of robot class
    boolean interrupt = false;
    boolean isrunning = false;
    LEDController ledcontrol;
    int skipValue = 10; // skip this many pixels when reading screenshot
    int captRate = 500; // ms to wait after each capture
    int threshold = 60;
    Looper looper;
    Fader fader;

    public Ambient(LEDController ledcon) {
        ledcontrol = ledcon;
        try {
            robby = new Robot();
        } catch (AWTException e) {
            System.out.println("Robot class not supported by your system!");
        }
    }

    public void startAmb() {
        interrupt = false;
        isrunning = true;
        looper = new Looper();
        looper.start();
        fader = new Fader();
        fader.start();
    }

    public void stopAmb() {
        interrupt = true;
        fader.interrupt();
        looper.interrupt();
    }

    public void setPixelSkip(int pixels) {
        skipValue = pixels;
    }

    public void setCaptureRate(int captrate) {
        captRate = captrate;
    }
    
    public void setThreshold(int thresh) {
        threshold = thresh;
    }
    
    public void setFadeRate(int rate){
       faderate = rate; 
    }
    
    public void setFadeSpeed(int speed){
        fadeint = speed;
    }

    public class Looper extends Thread {
        // captures and averages the rgb values on a timeout unless interupted
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = toolkit.getScreenSize().width; //possibly displayWidth
        int y = toolkit.getScreenSize().height; //possible displayHeight instead
        BufferedImage screenshot;
        @Override
        public void run() {
            int pixel; //ARGB variable with 32 int bytes where sets of 8 bytes are: Alpha, Red, Green, Blue
            float r = 0;
            float g = 0;
            float b = 0;
            targetcolor = new float[]{1,1,1};
            while (!interrupt) {
                //get screenshot into object "screenshot" of class BufferedImage
                screenshot = robby.createScreenCapture(new Rectangle(new Dimension(x, y)));
                // loop through pixels at interval
                int i = 0;
                int j = 0;
                for (i = 0; i < x; i = i + skipValue) {
                    for (j = 0; j < y; j = j + skipValue) {
                        pixel = screenshot.getRGB(i, j); //the ARGB integer has the colors of pixel (i,j)
                        r = r + (int) (255 & (pixel >> 16)); //add up reds
                        g = g + (int) (255 & (pixel >> 8)); //add up greens
                        b = b + (int) (255 & (pixel)); //add up blues
                    }
                }
                int aX = x / skipValue;
                int aY = y / skipValue;
                r = r / (aX * aY); //average red
                g = g / (aX * aY); //average green
                b = b / (aX * aY); //average blue
                //System.out.println(r+","+g+","+b);
                // filter values to increase saturation
                int colorchange = Math.round((Math.abs(r-targetcolor[0]) + Math.abs(g-targetcolor[1]) + Math.abs(b-targetcolor[2]))/3);
                if (colorchange>threshold){
                float maxColorInt;
                float minColorInt;
                maxColorInt = Math.max(Math.max(r, g), b);
                if (maxColorInt == r) {
                    // red
                    if (maxColorInt < (225 - 20)) {
                        r = maxColorInt + 20;
                    }
                } else if (maxColorInt == g) {
                    //green
                    if (maxColorInt < (225 - 20)) {
                        g = maxColorInt + 20;
                    }
                } else {
                    //blue
                    if (maxColorInt < (225 - 20)) {
                        b = maxColorInt + 20;
                    }
                }
                //minimise smallest
                minColorInt = Math.min(Math.min(r, g), b);
                if (minColorInt == r) {
                    // red
                    if (minColorInt > 20) {
                        r = minColorInt - 20;
                    }
                } else if (minColorInt == g) {
                    //green
                    if (minColorInt > 20) {
                        g = minColorInt - 20;
                    }
                } else {
                    //blue
                    if (minColorInt > 20) {
                        b = minColorInt - 20;
                    }
                }
                // set the color to fade to
                    //setColor(new int[]{Math.round(r), Math.round(g), Math.round(b)});
                    setFadeColor(new Color(Math.round(r),Math.round(g),Math.round(b)));
                }
                // sleep
                try {
                    // sleep and repeat
                    Thread.sleep(captRate);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Ambient.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            // interupted
            interrupt = false;
            isrunning = false;
        }
    }
    // FADER
    private float[] targetcolor;
    private float[] curcolor;
    public int[] curseqcolor;
    public boolean doingfade = false;
    private int faderate = 5; // fade increment
    private int fadeint = 10; // ms between each shade
    private class Fader extends Thread {
       private float[] target;
        private float[] curint;
        boolean initializing = true;
        @Override
        public void run() {
            curint = new float[]{1,1,1};
            while (isrunning) {
                if (initializing) { // waiting for first value
                    if (targetcolor != null) { // set first value as current, set the color; initialization complete 
                        setColor(targetcolor);
                        curcolor = targetcolor;
                        this.target = new float[]{0,0,0}; // so we know when it has been changed and can recalculate intervals
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
                        doingfade=false;
                    } else {
                        doFadeStep();
                        doingfade=true;
                    }
                    //System.out.println(curcolor[0] + "-" + setcolor[0]+" " + curcolor[1] + "-" + setcolor[1] + " " + curcolor[2] + "-"+ setcolor[2]); 
                }
                    try {
                        Thread.sleep(fadeint); // FADE SPEED
                    } catch (InterruptedException ex) {
                        Logger.getLogger(LEDController.class.getName()).log(Level.SEVERE, null,
                                ex);
                    }
            }
        }

        private void doFadeStep() {
            int i = 0;
            // are we diming?
                i=0;
                // apply interval to each channel
                while (i < 3) {
                // new taget color? work out new relative intervals
                if (target[i] != targetcolor[i]){
                        float diff = targetcolor[i]-curcolor[i];
                        diff = (diff < 0 ? -diff : diff);
                        curint[i] =  (diff/100)*faderate;
                        //System.out.println("test");
                        target[i] = targetcolor[i];
                }
                
                    // work out increments for each channel
                    curcolor[i] = (Math.round(curcolor[i]) == Math.round(targetcolor[i]) ? targetcolor[i]
                            : (curcolor[i] < targetcolor[i] ? (curcolor[i] + curint[i] <= targetcolor[i] ? curcolor[i] + curint[i]
                            : targetcolor[i]) : (curcolor[i] - curint[i] >= targetcolor[i]
                            ? curcolor[i] - curint[i] : targetcolor[i])));
                    i++;
            }
            // set fade increment color
            //System.out.println(curint[0]+"-"+curint[1]+"-"+curint[2]);
            //System.out.println(curcolor[0]+"-"+curcolor[1]+"-"+curcolor[2]+"-");
            setColor(curcolor);
        }
    }

    private void setFadeColor(Color color) {
        targetcolor = new float[]{color.getRed(), color.getGreen(), color.getBlue()};
        curseqcolor = new int[]{color.getRed(), color.getGreen(), color.getBlue()};
    }
    private void setColor(float[] rgb) {
        ledcontrol.setColor(new int[]{Math.round(rgb[0]),Math.round(rgb[1]),Math.round(rgb[2])});
    }
    private void setColor(int[] rgb) {
        // pass to controller object
        ledcontrol.setColor(rgb);
    }
}
