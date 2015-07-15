package ch.webvantage.nixonpi.communication;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import ch.webvantage.nixonpi.MainPreferences_;
import ch.webvantage.nixonpi.communication.model.NixonpiServer;
import ch.webvantage.nixonpi.event.DiscoveryEvent;
import ch.webvantage.nixonpi.util.EmulatorUtil;
import de.greenrobot.event.EventBus;
import hugo.weaving.DebugLog;

/**
 * Created by dkummer on 01/07/15.
 */
@EIntentService
public class DiscoverService extends IntentService {

    private static final String TAG = DiscoverService.class.getName();

    @Pref
    MainPreferences_ prefs;


    private static final String TAG_ADDRESSES = "ip_addresses";
    private static final String TAG_PORT = "port";


    public DiscoverService() {
        super(DiscoverService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Do nothing here
    }

    @DebugLog
    @ServiceAction
    void discover() {
        List<NixonpiServer> servers = new ArrayList<>();

        //TODO rewrite this hack
        if (EmulatorUtil.isEmulator()) {
            byte[] address = new byte[]{10,0,2,2};
            try {
                servers.add(new NixonpiServer(InetAddress.getByAddress(address), 8080));
                EventBus.getDefault().post(new DiscoveryEvent(servers));
                return;
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.setSoTimeout(prefs.timeout().get());

            sendDiscoveryRequest(socket);

            DatagramSocket responseSocket = new DatagramSocket(prefs.replyPort().get(), InetAddress.getByName("0.0.0.0"));
            responseSocket.setBroadcast(true);
            responseSocket.setSoTimeout(prefs.timeout().get());

            servers = listenForResponses(responseSocket);
            socket.close();
            responseSocket.close();
        } catch (IOException e) {
            Log.e(TAG, "Could not send discovery request", e);
        } catch (JSONException e) {
            Log.e(TAG, "Error parsing JSON", e);
        }
        EventBus.getDefault().post(new DiscoveryEvent(servers));

    }


    /**
     * Send a broadcast UDP packet containing a request for boxee services to
     * announce themselves.
     *
     * @throws IOException
     */
    @DebugLog
    private void sendDiscoveryRequest(DatagramSocket socket) throws IOException {
        String data = String.format("{ \"cmd\":\"%s\", \"application\":\"nixonpi\", \"reply_port\":\"%d\" }", "discover", prefs.replyPort().get());
        Log.d(TAG, "Sending data " + data);
        DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), getBroadcastAddress(), prefs.discoveryPort().get());
        socket.send(packet);
        Log.d(TAG, "Broadcast packet sent to: " + getBroadcastAddress().getHostAddress());

    }

    /**
     * Calculate the broadcast IP we need to send the packet along. If we send it
     * to 255.255.255.255, it never gets sent. I guess this has something to do
     * with the mobile network not wanting to do broadcast.
     */
    @DebugLog
    private InetAddress getBroadcastAddress() throws IOException {
        WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
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
    @DebugLog
    private List<NixonpiServer> listenForResponses(DatagramSocket socket)
            throws IOException, JSONException {
        long start = System.currentTimeMillis();
        byte[] buf = new byte[1024];
        List<NixonpiServer> servers = new ArrayList<>();

        boolean continueLoop = true;
        // Loop and try to receive responses until the timeout elapses. We'll get
        // back the packet we just sent out, which isn't terribly helpful, but we'll
        // discard it in parseResponse because the cmd is wrong.
        try {
            while (continueLoop) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String s = new String(packet.getData(), 0, packet.getLength());
                Log.d(TAG, "Packet received after " + (System.currentTimeMillis() - start) + " " + s);
                NixonpiServer server = parseResponse(s, ((InetSocketAddress) packet.getSocketAddress()).getAddress());
                if (server != null) {
                    servers.add(server);
                    continueLoop = false;
                }
                //TODO currently the service exits after timeout, we could directly exit here if a server is found
            }
        } catch (SocketTimeoutException e) {
            Log.d(TAG, "Receive timed out");
        }
        return servers;
    }

    @DebugLog
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
