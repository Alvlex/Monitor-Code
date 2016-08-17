package monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by alexvanlint on 10/08/2016.
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        StatusThread myThread = new StatusThread();
        myThread.start();

        SpringApplication.run(Application.class, args);

    }
}
