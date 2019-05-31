package huobiwebsocketconnection;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

/*

   To connect to huobi websocket and subscribe to it

 */

public class HuobiWebSocketClient extends WebSocketClient {



    public static  HashMap<Double,Double> bidsDetails=new HashMap<Double, Double>();

    public static HashMap<Double,Double> asksDetails=new HashMap<Double, Double>();

    public static HashMap<String,HashMap> tradeDetails =new HashMap<String,HashMap>();

    public static JSONObject marketDepthData;

     final  String subject="market.btcusdt.depth.";

    String step;

    private SimpMessagingTemplate simpMessagingTemplate;

    Logger logger= LoggerFactory.getLogger(HuobiWebSocketClient.class);





    public HuobiWebSocketClient(URI serverURI,SimpMessagingTemplate simpMessagingTemplate) {
        super(serverURI, new Draft_17());


        this.simpMessagingTemplate=simpMessagingTemplate;

    }



    @Override
    public void onOpen(ServerHandshake serverHandshake) {


        logger.info("Socket opened");
        JSONObject req=new JSONObject();
        step="step0";
        req.put("sub",subject+step);
        req.put("id","12321");
        send(req.toString());
    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onClose(int i, String s, boolean b) {

        logger.info("socket connection closed");
        SocketLayer socketLayerConnection=new SocketLayer();
        socketLayerConnection.connect(this);

    }

    @Override
    public void onError(Exception e) {

        logger.error(e.toString());
    }

    @Override
    public void onMessage(ByteBuffer bytes) {

        try {

            String message = new String(decompress(bytes.array()), "UTF-8");

            JSONObject jsonObject = new JSONObject(message);


            if (!StringUtils.isEmpty(message)) {

                if (message.indexOf("ping") > 0) {
                    String pong = jsonObject.toString();
                    send(pong.replace("ping", "pong"));
                }

                 else {
                     if(jsonObject!=null) {

                         if(jsonObject.toString().contains("tick")) {
                             JSONObject tick = jsonObject.getJSONObject("tick");
                             this.simpMessagingTemplate.convertAndSend("/topic/marketDepth", tick.toString());
                         }
                     }

                }
            }
        }
        catch (CharacterCodingException e) {

            logger.error(e.toString());


        }
        catch (Exception e) {

            logger.error(e.toString());

        }


    }



     /*

      To unzip the message got

     */



    public static byte[] decompress(byte[] depressData) throws Exception {

        ByteArrayInputStream inputStream = new ByteArrayInputStream(depressData);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);

        int count;
        byte data[] = new byte[1024];
        while ((count = gzipInputStream.read(data, 0, 1024)) != -1) {
            outputStream.write(data, 0, count);
        }
        gzipInputStream.close();
        depressData = outputStream.toByteArray();
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return depressData;
    }


}
    /*

       To parse the tradeDetails message and to view in jsp

     */


   /* @SendTo("/topic/marketDepth")
    public JSONObject parse(JSONObject toParse){

        //System.out.println(toParse);
        return toParse;


        /*if(toParse.toString().contains("tick")==true)
            tick=toParse.getJSONObject("tick");
        else
            return new JSONObject();

        JSONArray bids=tick.getJSONArray("bids");

        JSONArray asks=tick.getJSONArray("asks");

        bidsDetails.clear();
        asksDetails.clear();

        tradeDetails.clear();

        for(int iterator=0;iterator<bids.length();iterator++) {

            JSONArray bid= bids.optJSONArray(iterator);

            bidsDetails.put((Double)bid.get(0),(Double)bid.get(1));


        }

        for(int iterator=0;iterator<asks.length();iterator++) {

            JSONArray ask= asks.optJSONArray(iterator);

            asksDetails.put((Double)ask.get(0),(Double)ask.get(1));


        }


        tradeDetails.put("bids",bidsDetails);
        tradeDetails.put("asks",asksDetails);




    }
*/





