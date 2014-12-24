package aard.telnet;

import aard.telnet.options.OptionHandler;
import aard.telnet.options.OptionHandlerSimple;

public class CommandHandler {
    private String[] localOptions;
    private String[] remoteOptions;
    private OptionHandler[] oHandlers;
    private Connection conn;
    
    public CommandHandler(Connection conn) {
        localOptions = new String[256];
        remoteOptions = new String[256];
        oHandlers = new OptionHandler[256];
        this.conn = conn;
        this.initOptions();
    }
    
    public void onCommand(int b) {
        if (b >= Codes.WILL && b <= Codes.DONT) {
            onOption(b, (char) conn.read());
        }
    }
    
    public void onOption(int b, char option) {
        boolean response = false;
        if (b == Codes.WILL || b == Codes.WONT) { // request to set remote option
            if (oHandlers[option] != null) {
                System.out.println(String.format("Option %s set remotely", b));
                response = oHandlers[option].remoteResponse(b == Codes.WILL);
                if (b == Codes.WILL && response) {
                    this.conn.write(new char[] { Codes.IAC, Codes.DO, option });
                } else {
                    this.conn.write(new char[] { Codes.IAC, Codes.DONT, option });
                }
                oHandlers[option].remoteSet(b == Codes.WILL);
            } else {
                this.conn.write(new char[] { Codes.IAC, Codes.DONT, option });
                System.out.println("No handler for option " + String.valueOf((int) option) + "!");
            }
        } else if (b == Codes.DO || b == Codes.DONT) { // request to set local option
            if (oHandlers[option] != null) {
                System.out.println(String.format("Option %s set locally", b));
                response = oHandlers[option].localResponse(b == Codes.WILL);
                if (b == Codes.DO && response) {
                    this.conn.write(new char[] { Codes.IAC, Codes.WILL, option });
                } else {
                    this.conn.write(new char[] { Codes.IAC, Codes.WONT, option });
                }
                oHandlers[option].localSet(b == Codes.DO);
            } else {
                this.conn.write(new char[] { Codes.IAC, Codes.WONT, option });
                System.out.println("No handler for option " + String.valueOf((int) option) + "!");
            }
        }
    }
    
    public void setOption(byte command, char option, String value) {
        StringBuilder s = new StringBuilder();
        s.append(Codes.IAC);
        s.append(Codes.DO);
        s.append(option);
    }
    
    /** <return> Whether a handler already existed </return> **/
    public boolean registerOptionHandler(int id, OptionHandler o) {
        if (this.oHandlers[id] != null) return true;
        this.oHandlers[id] = o;
        return false;
    }
    
    private void initOptions() {
        this.registerOptionHandler(Codes.ECHO, new OptionHandlerSimple(conn, (byte) Codes.ECHO, localOptions, remoteOptions));
    }
}
