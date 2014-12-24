package aard.telnet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.LinkedBlockingQueue;

public class Connection implements Runnable {
    public Connector conn;
    private BufferedReader input;
    private OutputStreamWriter output;
    private LinkedBlockingQueue<String> commands;
    public InputHandler inpHandler;
    public CommandHandler cHandler;
    
    public Connection(Connector c, BufferedReader input, OutputStreamWriter output, LinkedBlockingQueue<String> commands) {
        this.conn = c;
        this.input = input;
        this.output = output;
        this.commands = commands;
        this.inpHandler = new InputHandler(this);
        this.cHandler = new CommandHandler(this);
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            inpHandler.begin();
        }
    }
    
    public int read() {
        try {
            int b = input.read();
            if (b == -1) throw new Exception("End of stream");
            return b;
        } catch (Exception e) {
            e.printStackTrace();
            conn.print("Connection lost");
            return 0;
        }
    }
    
    /** Look for <i>char</i> and return the next character **/
    public char read(char look) {
        try {
            char c;
            while ((c = (char) input.read()) != look) {
                // Extra stuff?
                System.out.print(c);
            }
            return (char) input.read();
        } catch (IOException e) {
            e.printStackTrace();
            conn.print("Connection lost");
            return (char) 0;
        }
    }
    
    public void write(char[] out) {
        try {
            if (this.conn.isConnected()) output.write(out);
        } catch (IOException e) {
            e.printStackTrace();
            this.conn.print("Connection lost");
            this.conn.disconnect();
        }
    }
    
    public void write(String out) {
        this.write(out.toCharArray());
    }
}
