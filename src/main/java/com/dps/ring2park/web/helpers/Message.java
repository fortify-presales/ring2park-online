package com.dps.ring2park.web.helpers;

public final class Message {

    /**
     * Enumeration elements are constructed once upon class loading.
     */
    public static final Message SUCCESS = new Message ("success");
    public static final Message ERROR = new Message ("error");
    public static final Message WARNING = new Message ("warning");
    public static final Message INFO = new Message ("info");

    public String toString() {
        return fName;
    }

    // PRIVATE
    private final String fName;

    /**
     * Private constructor prevents construction outside of this class.
     */
    private Message(String aName) {
        fName = aName;
    }
}
