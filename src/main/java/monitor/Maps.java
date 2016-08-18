package monitor;
import org.apache.tomcat.jni.Status;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * Created by alexvanlint on 10/08/2016.
 */

@Component
public class Maps {

    private final static Map<String, StatusData> liveStatus = new HashMap<>();
    private final static Map<String, StatusData> staleStatus = new HashMap<>();

    public static String printCurrentStatuses() {
        String returnMessage = "<table><tr><td>Host Name</td><td>App Name</td><td>IP Address</td></tr>";
        List<String> toMove = new ArrayList<>();
        synchronized (liveStatus) {

            for (String key : liveStatus.keySet()) {
                StatusData status = liveStatus.get(key);
                long timeDiff = status.getAge();
                if (timeDiff > StatusData.STALE) {
                    toMove.add(key);
                }
                else{
                    returnMessage = returnMessage + status.print2();
                }
            }
            /*

                if(!toMove.isEmpty()){
                    for (int i = 0; i < toMove.size(); i++) {
                        String key = toMove.get(i);
                        staleStatus.put(key, liveStatus.get(key));
                        liveStatus.remove(key);
                    }
                    toMove.clear();
                }
                */
        }
        returnMessage = returnMessage + "</table>";
        return returnMessage;
    }

    public static String printSpecificStatus(String hostname) {
        try {
            synchronized (liveStatus) {
                if(liveStatus.get(hostname).equals(null)) {
                    StatusData status = staleStatus.get(hostname);
                    return status.print();
                }
                else{
                    StatusData status = liveStatus.get(hostname);
                    return status.print();
                }
            }
        }
        catch(NullPointerException e){
            System.out.println(hostname + " not found");
            return "";
        }
    }

    public static String printAllStatuses(){
        String returnMessage = "";
        synchronized (liveStatus) {
            for (String key : liveStatus.keySet()) {
                StatusData status = liveStatus.get(key);
                returnMessage = returnMessage.concat(status.print2());
            }
            for (String key : staleStatus.keySet()) {
                StatusData status = staleStatus.get(key);
                returnMessage = returnMessage.concat(status.print2());
            }
            return returnMessage;
        }
    }


    public static void add(StatusData status) {
        String hostname = status.getName();
        synchronized (liveStatus) {
            if (liveStatus.containsKey(hostname)) {
                liveStatus.replace(hostname, status);
                System.out.println("Replaced entry");
            } else {
                liveStatus.put(hostname, status);
                System.out.println("Added entry");
            }
        }
    }

    public static boolean liveIsEmpty() {
        synchronized (liveStatus) {
            return liveStatus.isEmpty();
        }
    }
}
