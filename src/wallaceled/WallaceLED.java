/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallaceled;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import jssc.SerialPortException;

/**
 *
 * @author michael
 */
public class WallaceLED extends javax.swing.JFrame {

    /**
     * Creates new form WallaceLED
     */
    LEDController ledcontrol;
    public WallaceLED() {
        HideToSystemTray();
        initComponents();
        setLocationRelativeTo(getRootPane());
        // setup serial connection class
        ledcontrol = new LEDController(ColorPanel);
        // get serial list
        String[] ports = ledcontrol.serial.getPorts();
        paintSerialList(ports);
        try {
            // try connecting to the last port
            ledcontrol.serial.connect(ports[ports.length-1]);
        } catch (SerialPortException ex) {
            Logger.getLogger(WallaceLED.class.getName()).log(Level.SEVERE, null, ex);
            errorDialog(new String[]{"Error", "Failed to connect to the selected serial port, try selecting another port from the menu"});
        }
        // setup mode button groups
        initModeButtons();
        // start led control
        ledcontrol.andGodSaidLetThereBeLight();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        colordialog = new javax.swing.JDialog();
        colorpicker = new javax.swing.JColorChooser();
        colorcancelbtn = new javax.swing.JButton();
        colorapplybtn = new javax.swing.JButton();
        manprevcb = new javax.swing.JCheckBox();
        settingsdialog = new javax.swing.JDialog();
        settingspane = new javax.swing.JTabbedPane();
        ambsettings = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        captsetting = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        pixelsetting = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        seqsettings = new javax.swing.JPanel();
        intsetting = new javax.swing.JLabel();
        fadeintsetting = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        faderatesetting = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        fadesetting = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        cfadesetting = new javax.swing.JCheckBox();
        cansettingbtn = new javax.swing.JButton();
        applysetbtn = new javax.swing.JButton();
        ColorPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        QuitMenu = new javax.swing.JMenuItem();
        SettingMenu = new javax.swing.JMenu();
        PortMenu = new javax.swing.JMenu();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        settingsbtn = new javax.swing.JMenuItem();
        ModeMenu = new javax.swing.JMenu();
        ambmoderb = new javax.swing.JRadioButtonMenuItem();
        seqmoderb = new javax.swing.JRadioButtonMenuItem();
        manmoderb = new javax.swing.JRadioButtonMenuItem();

        colordialog.setMinimumSize(new java.awt.Dimension(460, 300));

        colorcancelbtn.setText("Cancel");
        colorcancelbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorcancelbtnActionPerformed(evt);
            }
        });

        colorapplybtn.setText("Apply");
        colorapplybtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorapplybtnActionPerformed(evt);
            }
        });

        manprevcb.setText("Preview");
        manprevcb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manprevcbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout colordialogLayout = new javax.swing.GroupLayout(colordialog.getContentPane());
        colordialog.getContentPane().setLayout(colordialogLayout);
        colordialogLayout.setHorizontalGroup(
            colordialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, colordialogLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(manprevcb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colorapplybtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(colorcancelbtn))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, colordialogLayout.createSequentialGroup()
                .addGap(0, 18, Short.MAX_VALUE)
                .addComponent(colorpicker, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        colordialogLayout.setVerticalGroup(
            colordialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, colordialogLayout.createSequentialGroup()
                .addComponent(colorpicker, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(colordialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorcancelbtn)
                    .addComponent(colorapplybtn)
                    .addComponent(manprevcb)))
        );

        settingsdialog.setMinimumSize(new java.awt.Dimension(325, 275));

        jLabel1.setText("Capture Rate:");

        captsetting.setText("50");

        jLabel2.setText("ms  (rate of screen captures)");

        jLabel3.setText("Pixel Analysis:");

        pixelsetting.setText("10");

        jLabel4.setText("analyse every nth pixel");

        javax.swing.GroupLayout ambsettingsLayout = new javax.swing.GroupLayout(ambsettings);
        ambsettings.setLayout(ambsettingsLayout);
        ambsettingsLayout.setHorizontalGroup(
            ambsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ambsettingsLayout.createSequentialGroup()
                .addGroup(ambsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ambsettingsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ambsettingsLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ambsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pixelsetting, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(captsetting, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(ambsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        ambsettingsLayout.setVerticalGroup(
            ambsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ambsettingsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(ambsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(captsetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ambsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(pixelsetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(89, Short.MAX_VALUE))
        );

        settingspane.addTab("Ambience Mode", ambsettings);

        intsetting.setText("Interval:");

        fadeintsetting.setText("5");
        fadeintsetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fadeintsettingActionPerformed(evt);
            }
        });

        jLabel6.setText("(seconds between each color change)");

        jLabel7.setText("Rate:");

        faderatesetting.setText("5");
        faderatesetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                faderatesettingActionPerformed(evt);
            }
        });

        jLabel8.setText("(1-20 1=fastest)");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Fade");

        jLabel10.setText("Fade:");

        fadesetting.setSelected(true);
        fadesetting.setText("(enable fading)");
        fadesetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fadesettingActionPerformed(evt);
            }
        });

        jLabel11.setText("Crossfade:");

        cfadesetting.setSelected(true);
        cfadesetting.setText("(don't black out before color change)");
        cfadesetting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cfadesettingActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout seqsettingsLayout = new javax.swing.GroupLayout(seqsettings);
        seqsettings.setLayout(seqsettingsLayout);
        seqsettingsLayout.setHorizontalGroup(
            seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seqsettingsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(seqsettingsLayout.createSequentialGroup()
                        .addComponent(intsetting)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fadeintsetting, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6))
                    .addGroup(seqsettingsLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fadesetting)
                            .addComponent(cfadesetting)
                            .addGroup(seqsettingsLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(faderatesetting, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel8)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        seqsettingsLayout.setVerticalGroup(
            seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(seqsettingsLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(intsetting)
                    .addComponent(fadeintsetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addGap(10, 10, 10)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(fadesetting))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(cfadesetting))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(seqsettingsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(faderatesetting, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        settingspane.addTab("Seqences", seqsettings);

        cansettingbtn.setText("Cancel");
        cansettingbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cansettingbtnActionPerformed(evt);
            }
        });

        applysetbtn.setText("Apply");
        applysetbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applysetbtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settingsdialogLayout = new javax.swing.GroupLayout(settingsdialog.getContentPane());
        settingsdialog.getContentPane().setLayout(settingsdialogLayout);
        settingsdialogLayout.setHorizontalGroup(
            settingsdialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, settingsdialogLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(applysetbtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cansettingbtn))
            .addComponent(settingspane)
        );
        settingsdialogLayout.setVerticalGroup(
            settingsdialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settingsdialogLayout.createSequentialGroup()
                .addComponent(settingspane)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(settingsdialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cansettingbtn)
                    .addComponent(applysetbtn)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        ColorPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ColorPanelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout ColorPanelLayout = new javax.swing.GroupLayout(ColorPanel);
        ColorPanel.setLayout(ColorPanelLayout);
        ColorPanelLayout.setHorizontalGroup(
            ColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 181, Short.MAX_VALUE)
        );
        ColorPanelLayout.setVerticalGroup(
            ColorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 139, Short.MAX_VALUE)
        );

        FileMenu.setText("File");
        FileMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FileMenuActionPerformed(evt);
            }
        });

        QuitMenu.setText("Quit");
        QuitMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                QuitMenuActionPerformed(evt);
            }
        });
        FileMenu.add(QuitMenu);

        jMenuBar1.add(FileMenu);

        SettingMenu.setText("Settings");

        PortMenu.setText("Serial Port");
        SettingMenu.add(PortMenu);
        SettingMenu.add(jSeparator1);

        settingsbtn.setText("Settings...");
        settingsbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settingsbtnActionPerformed(evt);
            }
        });
        SettingMenu.add(settingsbtn);

        jMenuBar1.add(SettingMenu);

        ModeMenu.setText("Mode");

        ambmoderb.setSelected(true);
        ambmoderb.setText("Ambient Mode");
        ModeMenu.add(ambmoderb);

        seqmoderb.setSelected(true);
        seqmoderb.setText("Sequence Mode");
        ModeMenu.add(seqmoderb);

        manmoderb.setSelected(true);
        manmoderb.setText("Manual Mode");
        ModeMenu.add(manmoderb);

        jMenuBar1.add(ModeMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ColorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ColorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void FileMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FileMenuActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_FileMenuActionPerformed

    private void QuitMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_QuitMenuActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_QuitMenuActionPerformed
    int pvsavedmode = 0;
    Color pvsavedcolor;
    private void ColorPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ColorPanelMouseClicked
        // TODO add your handling code here:
        colordialog.setVisible(true);
        // set current values to return from preview
        pvsavedmode = ledcontrol.getMode();
        pvsavedcolor = ColorPanel.getBackground();
    }//GEN-LAST:event_ColorPanelMouseClicked

    private void colorcancelbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorcancelbtnActionPerformed
        // TODO add your handling code here:
        // restore original color if previewing
        if (pvsavedmode == ledcontrol.MANMODE){
            ledcontrol.setColor(pvsavedcolor);
        } else {
            ledcontrol.setMode(pvsavedmode);
            
        }
        setModeSelected(pvsavedmode);
        // close dialog
        colordialog.setVisible(false);
    }//GEN-LAST:event_colorcancelbtnActionPerformed

    private void colorapplybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorapplybtnActionPerformed
        // TODO add your handling code here:
        // Set LED Color
        Color color = colorpicker.getColor();
        ledcontrol.setColor(color);
        colordialog.setVisible(false);
        setModeSelected(0x2);
    }//GEN-LAST:event_colorapplybtnActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            // TODO add your handling code here:
            ledcontrol.serial.disconnect();
        } catch (SerialPortException ex) {
            Logger.getLogger(WallaceLED.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed
    private PropertyChangeListener manprevlist = new PropertyChangeListener(){
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            Color color = colorpicker.getColor();
            ledcontrol.setColor(color);
            setModeSelected(0x2);
        }  
    };
    private void manprevcbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manprevcbActionPerformed
        // TODO add your handling code here:
        if (manprevcb.isSelected()){
            colorpicker.getPreviewPanel().addPropertyChangeListener(manprevlist);
        } else {
            colorpicker.getPreviewPanel().removePropertyChangeListener(manprevlist);
        }
    }//GEN-LAST:event_manprevcbActionPerformed

    private void fadeintsettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fadeintsettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fadeintsettingActionPerformed

    private void faderatesettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_faderatesettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_faderatesettingActionPerformed

    private void fadesettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fadesettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fadesettingActionPerformed

    private void cfadesettingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cfadesettingActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cfadesettingActionPerformed

    private void settingsbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settingsbtnActionPerformed
        settingsdialog.setVisible(true);
    }//GEN-LAST:event_settingsbtnActionPerformed

    private void cansettingbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cansettingbtnActionPerformed
        settingsdialog.setVisible(false);
    }//GEN-LAST:event_cansettingbtnActionPerformed

    private void applysetbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_applysetbtnActionPerformed
        // TODO add your handling code here:
        if (saveSettings()){
            settingsdialog.setVisible(false);
        }
    }//GEN-LAST:event_applysetbtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WallaceLED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WallaceLED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WallaceLED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WallaceLED.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {   
                WallaceLED view = new WallaceLED();
                Color color = new Color(0, 0, 0);
                view.getContentPane().setBackground(color);
                Image icon = Toolkit.getDefaultToolkit().getImage(WallaceLED.class.getResource("img/RGBsmall.png"));
                view.setIconImage(icon);
                view.setVisible(true);
                
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ColorPanel;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu ModeMenu;
    private javax.swing.JMenu PortMenu;
    private javax.swing.JMenuItem QuitMenu;
    private javax.swing.JMenu SettingMenu;
    private javax.swing.JRadioButtonMenuItem ambmoderb;
    private javax.swing.JPanel ambsettings;
    private javax.swing.JButton applysetbtn;
    private javax.swing.JButton cansettingbtn;
    private javax.swing.JTextField captsetting;
    private javax.swing.JCheckBox cfadesetting;
    private javax.swing.JButton colorapplybtn;
    private javax.swing.JButton colorcancelbtn;
    private javax.swing.JDialog colordialog;
    private javax.swing.JColorChooser colorpicker;
    private javax.swing.JTextField fadeintsetting;
    private javax.swing.JTextField faderatesetting;
    private javax.swing.JCheckBox fadesetting;
    private javax.swing.JLabel intsetting;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JRadioButtonMenuItem manmoderb;
    private javax.swing.JCheckBox manprevcb;
    private javax.swing.JTextField pixelsetting;
    private javax.swing.JRadioButtonMenuItem seqmoderb;
    private javax.swing.JPanel seqsettings;
    private javax.swing.JMenuItem settingsbtn;
    private javax.swing.JDialog settingsdialog;
    private javax.swing.JTabbedPane settingspane;
    // End of variables declaration//GEN-END:variables
    private boolean saveSettings(){
        int[] intset = new int[]{Integer.valueOf(captsetting.getText()), Integer.valueOf(pixelsetting.getText()), Integer.valueOf(fadeintsetting.getText()), Integer.valueOf(faderatesetting.getText())};
        boolean fadeon = fadesetting.isSelected();
        boolean cfade = cfadesetting.isSelected();
        // validate
        String result = "OK";
        if (intset[0]<10 || intset[0]>20000){
            result="Capture rate must be between 10 and 20000";
        } else if (intset[1]<1){
            result="Pixel analysis must be a positive integer, recommended value is 5";
        } else if (intset[2]<1){
            result="Seqence interval must be at least a second";
        } else if (intset[3]<1 || intset[3]>50){
            result="Fade rate must be between 10 and 50"; 
        }
        if (!result.equals("OK")){
            errorDialog(new String[]{"Error", result});
            return false;
        } else {
            // set in application
            ledcontrol.applySettings(intset, fadeon, cfade);
            // write to settings file TBC
            return true;
        }
    }

    public final void errorDialog(String[] lines){
        JOptionPane.showMessageDialog(this, lines);
    }
    
    // portchange actionlistener
    ActionListener portchangelistener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
            try {
                String port = ((JRadioButtonMenuItem)e.getSource()).getText();
                ledcontrol.serial.connect(port);
            } catch (SerialPortException ex) {
                Logger.getLogger(WallaceLED.class.getName()).log(Level.SEVERE, null, ex);
                errorDialog(new String[]{"Error", "Failed to connect to the selected serial port, try selecting another port from the menu"});
            }
      }
    };
    ButtonGroup serialgroup;
    private void paintSerialList(String[] list){
        PortMenu.removeAll();
        serialgroup = new ButtonGroup();
        int i = 0;
        while (i<list.length){
            System.out.println(list[i]);
            JRadioButtonMenuItem menuitem = new JRadioButtonMenuItem();
            menuitem.setText(list[i]);
            menuitem.setSelected((i==list.length-1?true:false));
            menuitem.addActionListener(portchangelistener);
            serialgroup.add(menuitem);
            PortMenu.add(menuitem);
            i++;
        }
    }
    private ButtonGroup modegroup;
    ActionListener mclistener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
            JRadioButtonMenuItem btn = ((JRadioButtonMenuItem)e.getSource());
            String port = btn.getText();
            switch (port){
                case("Ambient Mode"):
                    ledcontrol.setMode(ledcontrol.AMBMODE);
                break;
                case("Sequence Mode"):
                    ledcontrol.setMode(ledcontrol.SEQMODE);
                break;
                case("Manual Mode"):
                    ledcontrol.setMode(ledcontrol.MANMODE);
            }
            modegroup.setSelected(btn.getModel(), rootPaneCheckingEnabled);
      }
    };
    private void initModeButtons(){
          modegroup = new ButtonGroup();
          ambmoderb.addActionListener(mclistener);
          modegroup.add(ambmoderb);
          seqmoderb.addActionListener(mclistener);
          modegroup.add(seqmoderb);
          manmoderb.addActionListener(mclistener);
          modegroup.add(manmoderb);
          modegroup.setSelected(ambmoderb.getModel(), rootPaneCheckingEnabled);
    }
    private void setModeSelected(int mode){
        switch(mode){
            case (1):
                modegroup.setSelected(ambmoderb.getModel(), rootPaneCheckingEnabled);
            break;
            case (2):
                modegroup.setSelected(manmoderb.getModel(), rootPaneCheckingEnabled);
            break;
            case (3):
                modegroup.setSelected(seqmoderb.getModel(), rootPaneCheckingEnabled);
            break;
        }
        
    }
    
    /**
     *
     * @author Mohammad Faisal ermohammadfaisal.blogspot.com
     * facebook.com/m.faisal6621
     *
     */
    TrayIcon trayIcon;
    SystemTray tray;

    private void HideToSystemTray() {
        System.out.println("creating instance");
        try {
            System.out.println("setting look and feel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            System.out.println("Unable to set LookAndFeel");
        }
        if (SystemTray.isSupported()) {
            System.out.println("system tray supported");
            tray = SystemTray.getSystemTray();

            Image image = Toolkit.getDefaultToolkit().getImage(WallaceLED.class.getResource("img/RGBsmall.png"));
            ActionListener exitListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Exiting....");
                    System.exit(0);
                }
            };
            
            ActionListener openListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(true);
                    setExtendedState(JFrame.NORMAL);
                }
            };
            ActionListener lightListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    MenuItem lightitem = (MenuItem) e.getSource();
                    if (lightitem.getLabel().equals("Lights off")){
                        ledcontrol.lightsOff();
                        lightitem.setLabel("Lights on");
                    } else {
                        ledcontrol.lightsOn();
                        lightitem.setLabel("Lights off");
                    }
                }
            };
            PopupMenu popup = new PopupMenu();
            MenuItem menuItem = new MenuItem("Open");
            menuItem.addActionListener(openListener);
            popup.add(menuItem);
            menuItem = new MenuItem("Lights off");
            menuItem.addActionListener(lightListener);
            popup.add(menuItem);
            menuItem = new MenuItem("Exit");
            menuItem.addActionListener(exitListener);
            popup.add(menuItem);
            trayIcon = new TrayIcon(image, "wallaceRGB", popup);
            trayIcon.setImageAutoSize(true);
        } else {
            System.out.println("system tray not supported");
        }
        addWindowStateListener(new WindowStateListener() {

            @Override
            public void windowStateChanged(WindowEvent e) {
                if (e.getNewState() == ICONIFIED) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        System.out.println("added to SystemTray");
                    } catch (AWTException ex) {
                        System.out.println("unable to add to tray");
                    }
                }
                if (e.getNewState() == 7) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                        System.out.println("added to SystemTray");
                    } catch (AWTException ex) {
                        System.out.println("unable to add to system tray");
                    }
                }
                if (e.getNewState() == MAXIMIZED_BOTH) {
                    //tray.remove(trayIcon);
                    setVisible(true);
                    System.out.println("Tray icon removed");
                }
                if (e.getNewState() == NORMAL) {
                    tray.remove(trayIcon);
                    setVisible(true);
                    System.out.println("Tray icon removed");
                }
            }
        });
        setIconImage(Toolkit.getDefaultToolkit().getImage("img/RGBsmall.jpg"));
        // hide when exit button pressed on window
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    setVisible(false);
                    tray.add(trayIcon);
                    System.out.println("added to SystemTray");
                } catch (AWTException ex) {
                    Logger.getLogger(WallaceLED.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //setVisible(true);
        setDefaultCloseOperation(JFrame.ICONIFIED);
    }
}
