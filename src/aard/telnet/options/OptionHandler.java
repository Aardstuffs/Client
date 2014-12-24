package aard.telnet.options;

import aard.telnet.Codes;
import aard.telnet.Connection;

public abstract class OptionHandler {
    protected Connection conn;
    private int id;
    private String[] localOpts;
    private String[] remoteOpts;
    
    public OptionHandler(Connection conn, int id, String[] localOptions, String[] remoteOptions) {
        this.conn = conn;
        this.id = id;
        this.localOpts = localOptions;
        this.remoteOpts = remoteOptions;
    }
    
    protected void setValue(String v, boolean remote) {
        if (remote) remoteOpts[getID()] = v;
        else localOpts[getID()] = v;
    }
    
    public String getValue(boolean remote) {
        if (remote) return remoteOpts[getID()];
        else return localOpts[getID()];
    }
    
    public abstract void remoteSet(boolean enable);
    
    public abstract void localSet(boolean enable);
    
    /** Whether to acknowledge it **/
    public abstract boolean remoteResponse(boolean enable);
    
    /** Whether to acknowledge it **/
    public abstract boolean localResponse(boolean enable);
    
    protected void waitForSubnegotiation() {
        do {
            while (this.conn.read(Codes.IAC) != Codes.SB) {}
        } while (this.conn.read() != getID());
    }
    
    public int getID() {
        return id;
    }
}
