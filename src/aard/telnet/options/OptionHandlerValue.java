package aard.telnet.options;

import aard.telnet.Codes;
import aard.telnet.Connection;

public class OptionHandlerValue extends OptionHandler {
    
    public OptionHandlerValue(Connection conn, byte id, String[] localOptions, String[] remoteOptions) {
        super(conn, id, localOptions, remoteOptions);
    }
    
    @Override
    public void remoteSet(boolean flag) {
        this.waitForSubnegotiation();
        StringBuilder v = new StringBuilder();
        boolean escaped = false;
        while (true) {
            int b = conn.read();
            if (escaped) {
                if (b == Codes.SE) {
                    break;
                } else {
                    v.append(b);
                }
                escaped = false;
            } else {
                if (b == 255) {
                    escaped = true;
                } else {
                    v.append((char) b);
                }
            }
            break;
        }
        this.setValue(v.toString(), true);
    }
    
    @Override
    public void localSet(boolean flag) {
        this.waitForSubnegotiation();
        StringBuilder v = new StringBuilder();
        boolean escaped = false;
        while (true) {
            int b = conn.read();
            if (escaped) {
                if (b == Codes.SE) {
                    break;
                } else {
                    v.append(b);
                }
                escaped = false;
            } else {
                if (b == 255) {
                    escaped = true;
                } else {
                    v.append((char) b);
                }
            }
            break;
        }
        this.setValue(v.toString(), false);
    }
    
}