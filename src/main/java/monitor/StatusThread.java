package monitor;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by alexvanlint on 11/08/2016.
 */
public class StatusThread extends Thread {
    DatagramSocket serverSocket;
    public void run() {
        try {
            serverSocket = new DatagramSocket(21320);
            listen();
        } catch(Exception e){

        }
    }

    public void listen() throws IOException {
        byte[] receiveData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            if (receivePacket != null) {
                parseJSON(receivePacket);
            }
            else {

            }
        }
    }

    public void parseJSON(DatagramPacket receivePacket) {
        JSONObject json;

        String sentence = new String(receivePacket.getData(), receivePacket.getOffset(), receivePacket.getLength());
        try {
            JSONParser parser = new JSONParser();
            json = (JSONObject) parser.parse(sentence);
            StatusData status = StatusData.getStatusObject(json);
            Maps.add(status);
        } catch (Exception e) {

        }
    }
}
