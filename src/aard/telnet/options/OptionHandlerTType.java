package aard.telnet.options;

import aard.telnet.Codes;
import aard.telnet.Connection;

public class OptionHandlerTType extends OptionHandler {
    
    public OptionHandlerTType(Connection conn, byte id, String[] localOptions, String[] remoteOptions) {
        super(conn, id, localOptions, remoteOptions);
    }
    
    @Override
    public void remoteSet(boolean enable) {
        // Nothing
    }
    
    @Override
    public void localSet(boolean enable) {
        this.waitForSubnegotiation();
        if (conn.read() == Codes.SEND && conn.read() == Codes.IAC && conn.read() == Codes.SE) {
            conn.write(new char[] { Codes.IAC, Codes.SB, Codes.TERMINAL_TYPE,
                    Codes.IS });
            conn.write(TERMINAL_TYPE.toCharArray());
            conn.write(new char[] { Codes.IAC, Codes.SE });
        }
    }
    
    @Override
    public boolean remoteResponse(boolean enable) {
        return false;
    }
    
    @Override
    public boolean localResponse(boolean enable) {
        return true;
    }
    
    public static final String TERMINAL_TYPE = "unknown";
}
