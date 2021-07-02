package org.jeecg.modules.cg.cg_kafka.utils;

import com.googlecode.jsonrpc4j.JsonRpcClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class gorpcClient {

    public static void gorpc_client(){
        System.out.println("11111111111111111111111");
        Socket socket = null;
        try {
            socket = new Socket("localhost", 1234);
            JsonRpcClient client = new JsonRpcClient();

            InputStream ips = socket.getInputStream();
            OutputStream ops = socket.getOutputStream();
            Map<String,Object> params = new HashMap();
            params.put("A",17);
            params.put("B",8);
            Integer  reply = client.invokeAndReadResponse("Arith.Aultiply", new Object[]{params}, Integer.class,ops, ips);

            System.out.println("reply: " + reply);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
