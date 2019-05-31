package huobiwebsocketconnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;

@SpringBootApplication
public class Application {

    private final String huobiWebSocketUri="wss://api.huobi.pro/hbus/ws";

    @Autowired
    SocketLayer socketLayerConnection;

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    public void init(){

        try {

            URI uri = new URI(huobiWebSocketUri);

            HuobiWebSocketClient huobiWebSocketClient = new HuobiWebSocketClient(uri,simpMessagingTemplate);


            socketLayerConnection.connect(huobiWebSocketClient);


        } catch (URISyntaxException e) {

            e.printStackTrace();
        }
    }

}
