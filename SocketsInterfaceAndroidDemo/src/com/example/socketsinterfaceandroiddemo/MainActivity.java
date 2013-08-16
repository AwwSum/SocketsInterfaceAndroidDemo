package com.example.socketsinterfaceandroiddemo;

import umass.socketsInterface.client.*; //the sockets interface library

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.os.Bundle;
import android.os.StrictMode;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Client receiverClient = null;
	private Client senderClient = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*	Allow network calls to be done on the main thread.
         * 	source: http://stackoverflow.com/questions/6343166/android-os-networkonmainthreadexception
         * 	By default android throws an exception when potentially long-running I/O is done on the main thread.
         * 		However, in this particular instance, that is literally the only purpose of this app existing,
         * 		so it's okay.
         */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //receives data from the server and writes it to a text view.
    public void receiveButton(View view) throws IOException{
    	//Intent testButtonIntent = new Intent(this, MainActivity.class);
    	
    	if(this.receiverClient == null){
    		this.receiverClient = new Client("184.72.220.54", 9119); //184.72.220.54 is the aws elastic IP of the server.
    	}
    	
    	TextView helloWorldTextView = (TextView)findViewById(R.id.textView1);
    	InputStream inFromWeb = receiverClient.getInputStream();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(inFromWeb));
    	String message = reader.readLine(); //throws the IOException
    	helloWorldTextView.setText(message);
    }
    
    //sends data to another client
    public void sendButton(View view){
		//get IP
		EditText ipText = (EditText)findViewById(R.id.editText2);
		String destAddr = ipText.getText().toString();
		//get port
		EditText portText = (EditText)findViewById(R.id.editText3);
		int destPort = Integer.parseInt(portText.getText().toString());
		//if this is the first time pressing the button, establish the connection.
		if(this.senderClient == null){
			this.senderClient = new Client("184.72.220.54", 9119, destAddr, destPort);
		}
    	//if sending to a different host, connect to them first.
		else if(!this.senderClient.getInetAddress().getAddress().equals(destAddr) || this.senderClient.getPort() != destPort){
			//184.72.220.54 is the aws elastic IP of the server.
			this.senderClient = new Client("184.72.220.54", 9119, destAddr, destPort);
		}
    	EditText dataText = (EditText)findViewById(R.id.editText1);
    	String data = dataText.getText().toString();
    	senderClient.write(data);
    }
    
}
