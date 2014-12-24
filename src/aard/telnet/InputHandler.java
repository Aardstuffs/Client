package aard.telnet;

public class InputHandler {
    private Connection conn;
    
    private boolean escaped;
    private boolean lineEnding;
    private StringBuilder buffer;
    
    public InputHandler(Connection conn) {
        this.conn = conn;
        this.buffer = new StringBuilder();
    }
    
    public void begin() {
        int b = conn.read();
        if (escaped) {
            this.conn.cHandler.onCommand(b);
            escaped = false;
        } else {
            if (b == 255) {
                escaped = true;
                return;
            }
            out((char) b);
        }
        //if (b > -1 && b < 256) System.out.print((char) b);
    }
    
    private void out(char c) {
        buffer.append(c);
        
        if (c == '\r') {
            if (lineEnding && buffer.length() > BUFFER_SIZE) this.flush();  
            else lineEnding = true;
        }
        if ((lineEnding && c == '\n') || (!lineEnding && buffer.length() > BUFFER_SIZE)) {
            this.flush();
            lineEnding = false;
        }
    }
    
    private void flush() {
        conn.conn.write(buffer.toString());
        buffer.setLength(0);
    }
    
    private static final int BUFFER_SIZE = 256;
}
