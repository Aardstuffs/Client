package aard.telnet.options;

import aard.telnet.Connection;

public class OptionHandlerSimple extends OptionHandler {

    public OptionHandlerSimple(Connection conn, byte id, String[] localOptions, String[] remoteOptions) {
        super(conn, id, localOptions, remoteOptions);
    }

    @Override
    public void remoteSet(boolean enable) {
        this.setValue(enable ? "1" : "0", true);
    }

    @Override
    public void localSet(boolean enable) {
        this.setValue(enable ? "1" : "0", false);
    }

    @Override
    public boolean remoteResponse(boolean enable) {
        return this.getValue(true) == (enable ? "1" : "0");
    }

    @Override
    public boolean localResponse(boolean enable) {
        return this.getValue(false) == (enable ? "1" : "0");
    }
}
