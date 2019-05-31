package huobiwebsocketconnection;

import org.apache.catalina.SessionEvent;
import org.apache.catalina.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketEventListener{


    private final static Logger logger = LoggerFactory.getLogger("SocketListener");
    @EventListener
    public void handleWebsocketRequest(SessionConnectEvent sessionConnectEvent){

        logger.info("request received  "+sessionConnectEvent.getMessage());
    }

    @EventListener
    public void handleRequestAfterConnection(SessionConnectedEvent sessionConnectedEvent) {

        logger.info("connection established  "+sessionConnectedEvent.getMessage());
    }

    @EventListener
    public void handleSubscription(SessionSubscribeEvent sessionSubscribeEvent) {

        logger.info("susbscription request "+sessionSubscribeEvent.getMessage());
    }

}
