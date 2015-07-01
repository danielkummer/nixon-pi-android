package ch.webvantage.nixonpi.communication;

import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import ch.webvantage.nixonpi.communication.model.NixonpiServer;
import ch.webvantage.nixonpi.event.DiscoveryEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by dkummer on 24/06/15.
 * based on https://code.google.com/p/boxeeremote/source/browse/
 */
public class Discoverer extends Thread {

    private static final int TIMEOUT_MS = 500;
    private static final String TAG = Discoverer.class.getName();
    private static final int DISCOVERY_PORT = 1234;
    private static final int REPLY_PORT = 1234;
    private static final String TAG_ADDRESSES = "ip_addresses";
    private static final String TAG_PORT = "port";

    private WifiManager wifi;

    public Discoverer(@NotNull WifiManager wifi) {
        this.wifi = wifi;
    }

    public void run() {
        List<NixonpiServer> servers = new ArrayList<>();
        try {
            //DatagramSocket socket = new DatagramSocket(DISCOVERY_PORT);
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.setSoTimeout(TIMEOUT_MS);

            sendDiscoveryRequest(socket);

            DatagramSocket responseSocket = new DatagramSocket(REPLY_PORT, InetAddress.getByName("0.0.0.0"));
            responseSocket.setBroadcast(true);

            servers = listenForResponses(responseSocket);
            socket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not send discovery request", e);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
        }
        EventBus.getDefault().post(new DiscoveryEvent(servers));
        //mReceiver.addAnnouncedServers(servers);
    }

    /**
     * Send a broadcast UDP packet containing a request for boxee services to
     * announce themselves.
     *
     * @throws IOException
     */
    private void sendDiscoveryRequest(DatagramSocket socket) throws IOException {
        String data = String.format("{ \"cmd\":\"%s\", \"application\":\"nixonpi\", \"reply_port\":\"%d\" }", "discover", REPLY_PORT);
        Log.d(TAG, "Sending data " + data);
        DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), getBroadcastAddress(), DISCOVERY_PORT);
        socket.send(packet);
        Log.d(TAG, "Broadcast packet sent to: " + getBroadcastAddress().getHostAddress());

    }

    /**
     * Calculate the broadcast IP we need to send the packet along. If we send it
     * to 255.255.255.255, it never gets sent. I guess this has something to do
     * with the mobile network not wanting to do broadcast.
     */
    private InetAddress getBroadcastAddress() throws IOException {
        DhcpInfo dhcp = wifi.getDhcpInfo();
        if (dhcp == null) {
            Log.d(TAG, "Could not get DHCP info");
            return null;
        }

        int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
        byte[] quads = new byte[4];
        for (int k = 0; k < 4; k++)
            quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
        return InetAddress.getByAddress(quads);
    }

    /**
     * Listen on socket for responses, timing out after TIMEOUT_MS
     *
     * @param socket socket on which the announcement request was sent
     * @return list of discovered servers, never null
     * @throws IOException
     */
    private List<NixonpiServer> listenForResponses(DatagramSocket socket)
            throws IOException, JSONException {
        long start = System.currentTimeMillis();
        byte[] buf = new byte[1024];
        List<NixonpiServer> servers = new ArrayList<>();

        // Loop and try to receive responses until the timeout elapses. We'll get
        // back the packet we just sent out, which isn't terribly helpful, but we'll
        // discard it in parseResponse because the cmd is wrong.
        try {
            while (true) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String s = new String(packet.getData(), 0, packet.getLength());
                Log.d(TAG, "Packet received after " + (System.currentTimeMillis() - start) + " " + s);
                NixonpiServer server = parseResponse(s, ((InetSocketAddress) packet.getSocketAddress()).getAddress());
                if (server != null) {
                    servers.add(server);
                }
                /*
                // Send the packet data back to the UI thread
    Intent localIntent = new Intent(Constants.BROADCAST_ACTION)
            // Puts the data into the Intent
            .putExtra(Constants.EXTENDED_DATA_STATUS, data);
    // Broadcasts the Intent to receivers in this app.
    LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
                 */

            }
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "Receive timed out");
        }
        return servers;
    }

    private NixonpiServer parseResponse(String response, InetAddress address) throws JSONException {
        JSONObject jsonObj = new JSONObject(response);
        List<String> serverAddresses = new ArrayList<>();

        // Getting JSON Array node
        JSONArray adresses = jsonObj.getJSONArray(TAG_ADDRESSES);
        int port = jsonObj.getInt(TAG_PORT);

        //TODO do i really need the array of addresses? - i could return more data
        for (int i = 0; i < adresses.length(); i++) {
            serverAddresses.add(adresses.getString(i));
        }
        NixonpiServer server = new NixonpiServer(address, port);
        Log.d(TAG, "Discovered server " + server);
        return server;
    }

}
