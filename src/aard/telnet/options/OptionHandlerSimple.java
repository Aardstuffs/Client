package aard.telnet.options;

import aard.telnet.Connection;

public class OptionHandlerSimple extends OptionHandler {

    public OptionHandlerSimple(Connection conn, byte id, String[] localOptions, String[] remoteOptions) {
        super(conn, id, localOptions, remoteOptions);
    }

    @Override
    public void remoteSet(boolean flag) {
        this.setValue(flag ? "1" : "0", true);
    }

    @Override
    public void localSet(boolean flag) {
        this.setValue(flag ? "1" : "0", false);
    }
}
