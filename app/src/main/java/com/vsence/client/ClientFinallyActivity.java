package com.vsence.client;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.vsence.client.newproject.R;


public class ClientFinallyActivity extends Activity implements View.OnClickListener {

    //    ClientFinally client;
    EditText et_clientSend;

    Button btn_send;

    ClientLastly client;
    StringBuffer receiveData=new StringBuffer();

    Handler handler=new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1==0x12) {
                receiveData.append((String)msg.obj);
                receiveData.append("\r\n");
            }

            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_finally);
        et_clientSend=(EditText) findViewById(R.id.et_clientSend);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        client=new ClientLastly(handler);
        new Thread(client).start();
    }


    public void btn_clientSend(View view){
        client.send(et_clientSend.getText().toString()+"");
        et_clientSend.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.close();
    }

    @Override
    public void onClick(View v) {
        btn_clientSend(null);
    }
}