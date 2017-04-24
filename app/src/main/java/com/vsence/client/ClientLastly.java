package com.vsence.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * 通过socket实现
 * @author Administrator
 *
 */
public class ClientLastly implements Runnable{
    private static final String TAG="ClientLastly";
    private static final String NAME="com.repackaging.localsocket";
    private int timeout=30000;
    Socket client;
    PrintWriter os;
    BufferedReader is;

    Handler handler;

    ClientLastly(Handler handler){
        this.handler=handler;
//        try {
//            //连接服务器
//            client=new Socket("localhost", 8888);
//            Log.i(TAG, "Client=======连接服务器成功=========");
//            client.setSoTimeout(timeout);
//            os=new PrintWriter(client.getOutputStream());
//            is=new BufferedReader(new InputStreamReader(client.getInputStream()));
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }

    //发数据
    public void send(String data){
        Log.i(TAG, "Client=======data=========");
        if (os!=null) {
            os.println(data);
            os.flush();
        }
    }


    //接收据
    @Override
    public void run() {
        try {
            //连接服务器
//            client=new Socket("192.168.191.1", 8080);
            client=new Socket("localhost", 8080);
            Log.i(TAG, "Client=======连接服务器成功=========");
            client.setSoTimeout(timeout);
            os=new PrintWriter(client.getOutputStream());
            is=new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        String result="";
        while(true){
            try {
                result=is.readLine();
                Log.i(TAG, "客户端接到的数据为："+result);
                //将数据带回acitvity显示
                Message msg=handler.obtainMessage();
                msg.arg1=0x12;
                msg.obj=result;
                handler.sendMessage(msg);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    public void close(){
        try {
            if (os!=null) {
                os.close();
            }
            if (is!=null) {
                is.close();
            }
            if(client!=null){
                client.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}