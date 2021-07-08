package org.jeecg.modules.cg.cg_kafka.utils;

import com.googlecode.jsonrpc4j.JsonRpcClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class gorpcClient {

    public static ReplyBean gorpc_client(String hostname,int port,String methodName,Map<String,Object> params){
        System.out.println("11111111111111111111111:"+hostname+":"+port+":"+methodName+":"+params);
        ReplyBean reply = null;
        Socket socket = null;
        try {
            socket = new Socket(hostname, port);
            JsonRpcClient client = new JsonRpcClient();

            InputStream ips = socket.getInputStream();
            OutputStream ops = socket.getOutputStream();

            reply = client.invokeAndReadResponse(methodName, new Object[]{params}, ReplyBean.class, ops, ips);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        return reply;
    }

}
