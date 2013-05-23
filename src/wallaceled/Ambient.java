/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallaceled;

import java.awt.*;
import java.awt.image.BufferedImage;
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
    int captRate = 50; // ms to wait after each capture

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
        Looper looper = new Looper();
        looper.start();
    }

    public void stopAmb() {
        interrupt = true;
    }

    public void setPixelSkip(int pixels) {
        skipValue = pixels;
    }

    public void setCaptureRate(int captrate) {
        captRate = captrate;
    }

    public class Looper extends Thread {
        // captures and averages the rgb values on a timeout unless interupted
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int x = toolkit.getScreenSize().width; //possibly displayWidth
        int y = toolkit.getScreenSize().height; //possible displayHeight instead
        @Override
        public void run() {
            int pixel; //ARGB variable with 32 int bytes where sets of 8 bytes are: Alpha, Red, Green, Blue
            float r = 0;
            float g = 0;
            float b = 0;
            while (!interrupt) {
                //get screenshot into object "screenshot" of class BufferedImage
                BufferedImage screenshot = robby.createScreenCapture(new Rectangle(new Dimension(x, y)));
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
                // set the color
                setColor(new int[]{Math.round(r), Math.round(g), Math.round(b)});
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

    private void setColor(int[] rgb) {
        // pass to controller object
        ledcontrol.setColor(rgb);
    }
}
