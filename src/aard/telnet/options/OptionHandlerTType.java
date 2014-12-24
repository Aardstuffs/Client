package aard.telnet.options;

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
    }
    
    @Override
    public boolean remoteResponse(boolean enable) {
        return false;
    }
}
