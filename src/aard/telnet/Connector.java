package aard.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;

import aard.gui.GuiConsole;
import aard.interfaces.IDisposable;

public class Connector implements IDisposable {
    public static final String DOMAIN = "aardmud.org";
    //public static final int PORT = 23;
    //public static final String DOMAIN = "towel.blinkenlights.nl";
    public static final int PORT = 23;
    
    private Socket connection;
    private BufferedReader input;
    private OutputStreamWriter output;
    private LinkedBlockingQueue<String> commands;
    
    private Thread telnetThread;
    private boolean connected;
    
    private GuiConsole console;
    
    public Connector(GuiConsole console) {
        this.commands = new LinkedBlockingQueue<String>();
        this.console = console;
    }
    
    /** <return> True if successful, false if not. </return> **/
    public boolean connect() {
        if (!isConnected()) {
            try {
                this.connection = new Socket(DOMAIN, PORT);
                this.connection.setKeepAlive(true);
                if (output != null) output.close();
                this.input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                this.output = new OutputStreamWriter(connection.getOutputStream());
                
                Connection c = new Connection(this, input, output, commands);
                this.telnetThread = new Thread(c, "AardConnection");
                this.telnetThread.start();
                
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                input = null;
                output = null;
            }
        }
        return false;
    }
    
    public void disconnect() {
        if (!connection.isClosed()) try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (input != null) try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (output != null) try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        telnetThread.interrupt(); // Thread should complete itself
        
        this.connection = null;
        this.telnetThread = null;
        this.input = null;
        this.output = null;
        this.connected = false;
        this.commands.clear();
    }
    
    public boolean isConnected() {
        return connected;
    }
    
    public void queueCommand(String command) {
        if (!commands.offer(command))
            this.print("Slow down! (Command not sent)");
    }
    
    @Override
    public void dispose() {
        if (!connection.isClosed()) try {
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (input != null) try {
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if (output != null) try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        telnetThread.interrupt(); // Thread should complete itself
    }
    
    public void write(String s) {
        console.outAccess.write(s);
    }
    
    public void print(String s) {
        console.outAccess.print(s);
    }
}
