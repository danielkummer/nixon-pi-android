package ch.webvantage.nixonpi.event;

import java.util.List;

import ch.webvantage.nixonpi.communication.model.NixonpiServer;

/**
 * Created by dkummer on 24/06/15.
 */
public class DiscoveryEvent {

    private List<NixonpiServer> servers;

    public DiscoveryEvent(List<NixonpiServer> servers) {
        this.servers = servers;
    }

    public List<NixonpiServer> getServers() {
        return servers;
    }

    public boolean hasServers() {
        return !servers.isEmpty();
    }

    public NixonpiServer getFirstServer() {
        return servers.get(0);
    }
}
