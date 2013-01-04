/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wallaceled;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
/**
 *
 * @author michael
 */
public class SerialConnect {
    SerialPort serialport;
    public SerialConnect(){
        
    }
    public String[] getPorts(){
        return SerialPortList.getPortNames();
    }
    public void connect(String port) throws SerialPortException{
        if (!port.equals("")){
            if (serialport!=null){
                if (serialport.isOpened()){
                    serialport.closePort();
                }
            }
            serialport = new SerialPort(port);
            serialport.openPort();
            serialport.setParams(9600, 8, 1, 0);
        }
    }
    public void disconnect() throws SerialPortException{
        serialport.closePort();
    }
    public void writedata(byte byteval) throws SerialPortException{
        if (serialport.isOpened()){
                serialport.writeByte(byteval);
        };
    }
}
