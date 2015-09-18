package dataAccess;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import gui.facade.Facade;

import java.util.Enumeration;

/**
 * COMUNICACION CON EL PUERTO SERIAL
 * <p>
 * Clase que permite la comunicación con el puerto serial de la máquina. Y envía los datos a las
 * diferentes "ventanas" del juego. Para que realicen la acción respectiva. Verifica en cual 
 * "ventana" se encuentra en ese momento y envía el datos respectivo. Además si no recibe
 * nigún dato en un periódo de 115ms, ejecuta los metodos respectivos para eliminar la condicion
 * actual generada por presionar algun tecla anteriormente a esos 115ms.
 * <p>
 * Esta clase esta basada, en el ejemplo de la Página Oficial de Arduino.cc
 * @author Fabian A. Solano Madriz
 * @version 1.0.1
 * @see <a href="http://playground.arduino.cc/interfacing/java">Interfacing Java and Arduino</a>
 *
 */
public class SerialAccess implements SerialPortEventListener {
	public String ID_Componente;
	public Facade facade;
	@SuppressWarnings("unused")
	private static int tempPot = 200;
	SerialPort serialPort;
        /** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { 
			"/dev/tty.usbserial-A9007UX1", // Mac OS X
                        "/dev/ttyACM0", // Raspberry Pi
			"/dev/ttyUSB0", // Linux
			"COM4", // Windows
			"COM3",
			"COM5",
			"COM6"
	};
	/**
	* A BufferedReader which will be fed by a InputStreamReader 
	* converting the bytes into characters 
	* making the displayed results codepage independent
	*/
	private BufferedReader input;
	/** The output stream to the port */
	private static OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;

	public void initialize() {
                // the next line is for Raspberry Pi and 
                // gets us into the while loop and was suggested here was suggested http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
                System.setProperty("gnu.io.rxtx.SerialPorts", "COM4"); //CAMBIAR POR CONFIG PROPERTIES //TODO

		CommPortIdentifier portId = null;
		@SuppressWarnings("rawtypes")
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				int inputLine= Integer.parseInt(input.readLine());
				System.out.println("DATO DE ENTRADA: " + inputLine);
				if(inputLine == 10){ //AMBOS INTERRUPTORES APAGADOS
					facade.switchValor(ID_Componente, 0, 0); //Patilla: 0 --- Valor: 0
					facade.switchValor(ID_Componente, 0, 1); //Patilla: 1 --- Valor: 0
				}
				if(inputLine == 20){ //A ENCENDIDO, B APAGADO
					facade.switchValor(ID_Componente, 1, 0); //Patilla: 0 --- Valor: 1
					facade.switchValor(ID_Componente, 0, 1); //Patilla: 1 --- Valor: 0
				}
				if(inputLine == 30){ //A APAGADO, B ENCENDIDO
					facade.switchValor(ID_Componente, 0, 0); //Patilla: 0 --- Valor: 0
					facade.switchValor(ID_Componente, 1, 1); //Patilla: 1 --- Valor: 1
				}
				if(inputLine == 40){ //AMBOS INTERRUPTORES ENCENDIDOS
					facade.switchValor(ID_Componente, 1, 0); //Patilla: 0 --- Valor: 1
					facade.switchValor(ID_Componente, 1, 1); //Patilla: 1 --- Valor: 1
				}
				int valorDeSalida = facade.getOutput(ID_Componente);
				writeToPort(valorDeSalida);
				close();
				
			}
			catch (Exception e) {
			System.err.println(e.toString());
			}
		}
		//Cuando no recibe ningun dato elimina la acción anterior.
		try{
			Thread.sleep(115);
			//Añadir Método para eliminar la presion de teclas actuales cuando no se esta presionando ninguna
			//Level1State.remove();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
			
	}

	protected void Serial(String ncom){
        //if(Integer.parseInt(ncom)>=3 && Integer.parseInt(ncom)<=9)
        PORT_NAMES[2] = ncom;
        initialize();
        Thread t=new Thread() {
            public void run() {
                try {Thread.sleep(1000000);} catch (InterruptedException ie) {}
            }
        };
        t.start();
        System.out.println("Serial Communications Started");
    }
	
	public void run(String ID, Facade f){
		ID_Componente = ID;
		facade = f;
		Serial("COM4");
	}
	
	public static void writeToPort(int respuesta){
		if(respuesta == 1){
			try {
				output.write(57); //ESCRIBE EN EL PUERTO UN 9 (57 en ASCII), Sinónimo de un 1 (HIGH) en el sketch de Arduino
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(respuesta == 0){ //ESCRIBE EN EL PUERTO UN 8 (56 en ASCII), Sinónimo de un 0 (LOW) en el sketch de Arduino
			try {
				output.write(56);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}