package ch.webvantage.nixonpi.communication.model;

import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * Created by dkummer on 24/06/15.
 */
public class NixonpiServer {

    private InetAddress addr;
    private int port;


    public NixonpiServer(InetAddress addr, int port) {
        this.addr = addr;
        this.port = port;
    }

    public InetAddress getAddr() {
        return addr;
    }

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public static NixonpiServer createDummy() {
        return new NixonpiServer(InetAddress.getLoopbackAddress(), 1234);
    }

    @Override
    public String toString() {
        return String.format("%s:%d", addr.getHostAddress(), port);
    }
}
