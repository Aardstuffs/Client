package aard.telnet.options;

import aard.telnet.Connection;

public class OptionHandlerUnimplemented extends OptionHandler {
    
    public OptionHandlerUnimplemented(Connection conn, int id, String[] localOptions, String[] remoteOptions) {
        super(conn, id, localOptions, remoteOptions);
    }
    
    @Override
    public void remoteSet(boolean enable) {}
    
    @Override
    public void localSet(boolean enable) {}
    
    @Override
    public boolean remoteResponse(boolean enable) {
        return false;
    }
    
    @Override
    public boolean localResponse(boolean enable) {
        return false;
    }
    
}
