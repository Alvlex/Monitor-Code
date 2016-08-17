package monitor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * Created by alexvanlint on 10/08/2016.
 */
@RestController
public class Webpage {
    private static String prefix = "<html><head><meta http-equiv=\"refresh\" content=\"30\"><title>Monitoring</title>" +
            "<style>table, th, td { border: 1px solid black; }</style></head><body><div>";
    @RequestMapping("/services")
    public String index() throws Exception{
        if (Maps.liveIsEmpty()){
            return prefix + "No web apps are sending information</div></body></html>";
        }
        else {
            return prefix + "<table><tr><td>Host Name</td><td>App Name</td><td>IP Address</td></tr>" + Maps.printAllStatuses() + "</table></div></body></html>";
        }
    }
    @RequestMapping(method = RequestMethod.GET, value="/services/instance/{hostname}")
    public String service(@PathVariable String hostname) {
        if (Maps.liveIsEmpty()){
            return prefix + "No web apps are sending information</div></body></html>";
        }
        else {
            return prefix + Maps.printSpecificStatus(hostname) + "</div></body></html>";
        }
    }
    @RequestMapping(method = RequestMethod.GET, value="/services/current")
    public String stale(){
        return prefix + Maps.printCurrentStatuses() + "</div></body></html>";
    }
}