package aard.telnet;

public class Codes {
    
    // Options
    public static final char ECHO = (char) 1;
    public static final char SUPRESS_GA = (char) 3;
    public static final char STATUS = (char) 5;
    public static final char TIMING_MARK = (char) 6;
    public static final char TERMINAL_TYPE = (char) 24;
    public static final char MSDP = (char) 69;
    @Deprecated
    public static final char MCCP1 = (char) 85;
    public static final char MCCP2 = (char) 86;
    
    // TELOPT
    public static final char IS = (char) 0;
    public static final char SEND = (char) 1;
    
    // Commands following IAC character
    public static final char SE = (char) 240;
    public static final char NOP = (char) 241;
    public static final char DM = (char) 242;
    public static final char BRK = (char) 243;
    public static final char IP = (char) 244;
    public static final char AO = (char) 245;
    public static final char AYT = (char) 246;
    public static final char EC = (char) 247;
    public static final char EL = (char) 248;
    public static final char GA = (char) 249;
    public static final char SB = (char) 250;
    public static final char WILL = (char) 251;
    public static final char WONT = (char) 252;
    public static final char DO = (char) 253;
    public static final char DONT = (char) 254;
    public static final char IAC = (char) 255;
    
    // Aliases for convenience
    public static final char PING = (char) 246;
}
