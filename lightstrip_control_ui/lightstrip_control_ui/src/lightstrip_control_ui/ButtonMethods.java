package lightstrip_control_ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.Arrays;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class ButtonMethods {
	
	private static enum SerialStatus {
		SUCCESS,
		NO_TX,
		NO_RX,
		PORT_NOT_OPEN,
		PORT_NOT_CLOSED,
		DEVICE_NOT_FOUND
	}

	private static final int BAUD_RATE = 9600;
	private static final byte CHANGE_COLOR = 0;
	private static final byte LED_ON = 1;
	private static final byte LED_OFF = 2;
	private static byte[] CONNECT_MSG = "Hello from Arduino!".getBytes();
	private static Semaphore serialSem = new Semaphore(0);
	
	// Expand upon this for other Arduino names
	private static final String[] ARDUINO_DEVICE_NAMES = { "USB-SERIAL CH340" };
	
	protected static void changeColor(final Color toChange) {
		SerialPort myPort = getPort();
		String deviceName = myPort.getDescriptivePortName();
		
		System.out.println("Sending command to change color.");
		sendCommand(CHANGE_COLOR, myPort, deviceName, toChange, (byte)50);
	}
	
	protected static void turnOnLeds(final Color toChange) {
		SerialPort myPort = getPort();
		String deviceName = myPort.getDescriptivePortName();
		
		System.out.println("Sending command to turn on LED.");
		sendCommand(LED_ON, myPort, deviceName, toChange, (byte)50);
	}
	
	protected static void turnOffLeds(final Color toChange) {
		SerialPort myPort = getPort();
		String deviceName = myPort.getDescriptivePortName();
		
		System.out.println("Sending command to turn off LED.");
		sendCommand(LED_OFF, myPort, deviceName, toChange, (byte)0);
	}
	
	private static SerialPort getPort() {
		String portName;
		SerialPort toReturn;
		
		SerialPort portList[] = SerialPort.getCommPorts();
		ArrayList<String> validDeviceNames = new ArrayList<String>();
		
		for (SerialPort p : portList) {
//			System.out.println(p.getDescriptivePortName());
			for (int i = 0; i < ARDUINO_DEVICE_NAMES.length; i++) {
				if (p.getDescriptivePortName().contains(ARDUINO_DEVICE_NAMES[i])) { validDeviceNames.add(p.getDescriptivePortName()); }
			}
		}
		
		portName = validDeviceNames.get(0).substring(validDeviceNames.get(0).indexOf('(') + 1, validDeviceNames.get(0).indexOf(')'));
//		System.out.printf("Opening port on %s\n", portName);
		
		toReturn = SerialPort.getCommPort(portName);
		toReturn.setComPortParameters(BAUD_RATE, 8, 1, 0);
		toReturn.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);
		
		return toReturn;
	}
	
	private static SerialStatus sendCommand(final byte command, final SerialPort port, final String deviceName, final Color toChange, final byte brightness) {	
		SerialPortDataListener portListner = new SerialPortDataListener() {

			@Override
			public int getListeningEvents() { 
				return SerialPort.LISTENING_EVENT_DATA_RECEIVED; 
			}

			@Override
			public void serialEvent(SerialPortEvent event) {
				byte[] received = event.getReceivedData();
//				System.out.print("From Arduino: ");
//				System.out.println(received);
				if (event.getSerialPort().toString().contentEquals(port.toString())) {
					if (Arrays.equals(received, CONNECT_MSG)) {
						System.out.println("Got the connect message from Arduino!");
						serialSem.release();
					}
				}
			}
			
		};
		
		if (port.openPort()) {
			port.addDataListener(portListner);
			
			try {
				serialSem.acquire(); // to do: make this time out
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			byte cmdToSend[] = { command, (byte)toChange.getRed(), (byte)toChange.getGreen(), (byte)toChange.getBlue(), brightness };
			System.out.printf("Sending values: %d, %d, %d, %d, %d \n", command, toChange.getRed(), toChange.getGreen(), toChange.getBlue(), 50);
			
			if (port.writeBytes(cmdToSend, 5) == -1) {
				port.removeDataListener();
				return SerialStatus.NO_TX;
			}
		} else {
			System.out.println("Unable to open port on " + deviceName);
			port.removeDataListener();
			return SerialStatus.PORT_NOT_OPEN;
		}
		
		if (port.closePort() == false) {
			System.out.println("Unable to close port on " + deviceName);
			port.removeDataListener();
			return SerialStatus.PORT_NOT_CLOSED;
		}
		
		port.removeDataListener();
		return SerialStatus.SUCCESS;
	}
	
}
