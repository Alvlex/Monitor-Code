package monitor;

import org.json.simple.JSONObject;
import java.util.Date;
/**
 * Created by alexvanlint on 11/08/2016.
 */
public class StatusData {
    private String hostname, start, name, currentTime, ip, JRE;
    private Date received;
    private long numRequests;
    static long STALE = 10000;
    private long memUsed;
    private double cpuLoad;
    private long cpuLoadTime;
    public StatusData(String hostName, String startDate, String applicationName,
                      String continuousDate, long numRequests, String ip, String JRE, long memUsed, double cpuLoad, long cpuLoadTime) {
        hostname = hostName;
        start = startDate;
        name = applicationName;
        currentTime = continuousDate;
        received = new Date();

        this.ip = ip;
        this.JRE = JRE;
        this.numRequests = numRequests;
        this.cpuLoad = cpuLoad;
        this.cpuLoadTime = cpuLoadTime;
        this.memUsed = memUsed;

    }

    public static StatusData getStatusObject(JSONObject json) throws Exception {
        String applicationName = (String) json.get("applicationName");
        String startDate = (String) json.get("startDate");
        String continuousDate = (String) json.get("continuousDate");
        long numRequests = (long) json.get("numRequests");
        String ip = (String) json.get("ip");
        String hostname = (String) json.get("hostname");
        String JRE = (String) json.get("jre");
        long memUsed = (long) json.get("memUsed");
        double cpuLoad = (double) json.get("cpuLoad");
        long cpuLoadTime = (long) json.get("cpuLoadTime");
        return new StatusData(hostname, startDate, applicationName, continuousDate, numRequests, ip, JRE, memUsed, cpuLoad, cpuLoadTime);
    }

    public String print() {

        return "<table><tr><th>Host Name: </th><td>" + hostname + "</td></tr>" +
                "<tr><th>Application Name: </th><td>" + name + "</td></tr>" +
                "<tr><th>IP Address: </th><td>" + ip + "</td></tr>" +
                "<tr><th>Request Count: </th><td>" + numRequests + "</td></tr>" +
                "<tr><th>Java Version: </th><td>" + JRE + "</td></tr>" +
                "<tr><th>Start Time: </th><td>" + start + "</td></tr>" +
                "<tr><th>Current Time: </th><td>" + currentTime + "</td></tr>" +
                "<tr><th>CPU Usage: </th><td>" + cpuLoad + "</td></tr>" +
                "<tr><th>Memory Used: </th><td>" + memUsed + " bytes</td></tr>" +
                "<tr><th>Cumulative CPU time: </th><td>" + cpuLoadTime + " seconds</td></tr></table>";
    }
    public String print2(){
        String ipcolumn = ip;
        if (getAge() > STALE) {
            ipcolumn = "STALE (" + ip + ")";
        }
       return  "<tr><td><a href=" + "\"/services/instance/" + hostname +
                "/\">" + hostname + "</a></td><td>" + name + "</td><td>" + ipcolumn + "</td></tr>";
    }
    public String getName(){
        return hostname;
    }
    public long getAge() {
        Date now = new Date();
        // Returns millis
        return now.getTime() - received.getTime();
    }
}
