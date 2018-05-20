import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.Scanner;

import org.json.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(10003);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println(serverSocket.getInetAddress().toString());
        System.out.println("waiting");

        Socket connectSocket = null;
        try {
            connectSocket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        System.out.println("connected succ");

        InputStream connectSocketInputStream;
        OutputStream connectSocketOutputStream;
        try {
            connectSocketInputStream = connectSocket.getInputStream();
            connectSocketOutputStream = connectSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        JsonReader jsonReader = null;
        try {
            jsonReader = new JsonReader(connectSocketInputStream, "gbk") {
                @Override
                void onJsonObjectReceive(JSONObject jsonObject) {
                    System.out.println(jsonObject.toString(2));
                }

                @Override
                void onJsonArrayReceive(JSONArray jsonArray) {
                    System.out.println(jsonArray.toString(2));
                }
            };
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }

        try {
            jsonReader.beginRead();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                System.out.println("trying to disconnect");
//                connectSocketInputStream.close();
//                connectSocketOutputStream.close();
//                connectSocket.close();
//                serverSocket.close();
            } catch (Exception e1) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
