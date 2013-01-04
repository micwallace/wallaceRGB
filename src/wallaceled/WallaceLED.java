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
        ColorPanel = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        QuitMenu = new javax.swing.JMenuItem();
        SettingMenu = new javax.swing.JMenu();
        PortMenu = new javax.swing.JMenu();
        AmbMenu = new javax.swing.JCheckBoxMenuItem();

        colordialog.setMinimumSize(new java.awt.Dimension(460, 300));
        colordialog.setPreferredSize(new java.awt.Dimension(460, 300));

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

        AmbMenu.setSelected(true);
        AmbMenu.setText("Ambient Mode");
        AmbMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AmbMenuActionPerformed(evt);
            }
        });
        SettingMenu.add(AmbMenu);

        jMenuBar1.add(SettingMenu);

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

    private void AmbMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AmbMenuActionPerformed
        // TODO add your handling code here:
        if (this.AmbMenu.isSelected()){
            ledcontrol.setMode(ledcontrol.AMBMODE);
        } else {
            ledcontrol.setMode(ledcontrol.MANMODE);
        }
    }//GEN-LAST:event_AmbMenuActionPerformed

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
            AmbMenu.setSelected(true);
        }
        // close dialog
        colordialog.setVisible(false);
    }//GEN-LAST:event_colorcancelbtnActionPerformed

    private void colorapplybtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorapplybtnActionPerformed
        // TODO add your handling code here:
        // Set LED Color
        Color color = colorpicker.getColor();
        ledcontrol.setColor(color);
        colordialog.setVisible(false);
        AmbMenu.setSelected(false);
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
            AmbMenu.setSelected(false);
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
    private javax.swing.JCheckBoxMenuItem AmbMenu;
    private javax.swing.JPanel ColorPanel;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu PortMenu;
    private javax.swing.JMenuItem QuitMenu;
    private javax.swing.JMenu SettingMenu;
    private javax.swing.JButton colorapplybtn;
    private javax.swing.JButton colorcancelbtn;
    private javax.swing.JDialog colordialog;
    private javax.swing.JColorChooser colorpicker;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JCheckBox manprevcb;
    // End of variables declaration//GEN-END:variables
    
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
