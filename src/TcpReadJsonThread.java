package top.cc_love.chartex;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.WeakReference;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class TcpReadJsonThread extends Thread {
    private static final String TAG = TcpReadJsonThread.class.getSimpleName();

    String charset = "utf-8";

    public TcpReadJsonThread() {

    }
    public TcpReadJsonThread(String charset) {
        this.charset = charset;
    }

    abstract void onServerSocketEstablished(ServerSocket serverSocket);
    abstract void onServerSocketAccept(Socket socket);
    abstract void onTcpReceiveJsonObject(JSONObject jsonObject);
    abstract void onTcpReceiveJsonArray(JSONArray jsonArray);
    abstract void onTcpShutDown(IOException e);

    @Override
    public void run() {
        ServerSocket serverSocket = null;
        InputStream connectSocketInputStream = null;
        OutputStream connectSocketOutputStream = null;
        Socket connectSocket = null;
        try {
            //open port
            try {
                serverSocket = new ServerSocket(10003);
                onServerSocketEstablished(serverSocket);
            } catch (IOException e) {
                e.printStackTrace();
                onTcpShutDown(e);
                return;
            }

            Log.d(TAG, "run: listen: " + serverSocket.getInetAddress().toString());

            try {
                connectSocket = serverSocket.accept();
                onServerSocketAccept(connectSocket);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            Log.d(TAG, "run: connected succ");


            try {
                connectSocketInputStream = connectSocket.getInputStream();
                connectSocketOutputStream = connectSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            JsonReader jr = new JsonReader(connectSocketInputStream, connectSocketOutputStream, charset) {
                @Override
                void onJsonObjectReceive(JSONObject jsonObject) {
                    onTcpReceiveJsonObject(jsonObject);
                }

                @Override
                void onJsonArrayReceive(JSONArray jsonArray) {
                    onTcpReceiveJsonArray(jsonArray);
                }
            };

            try {
                jr.beginRead();
            } catch (JSONException e) {
                throw e;
            } catch (IOException e) {
                try {
                    connectSocketInputStream.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    connectSocketOutputStream.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    connectSocket.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                try {
                    serverSocket.close();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                onTcpShutDown(e);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
