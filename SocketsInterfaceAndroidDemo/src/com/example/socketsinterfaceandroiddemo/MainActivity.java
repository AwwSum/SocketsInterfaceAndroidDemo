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
import android.widget.TextView;

public class MainActivity extends Activity {

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
    
    //make sure that the button works, and that text can be inserted into the text view.
    public void testButton(View view) throws IOException{
    	//Intent testButtonIntent = new Intent(this, MainActivity.class);
    	TextView helloWorldTextView = (TextView)findViewById(R.id.textView1);
    	//helloWorldTextView.setText("The button works!");
    	Client myClient = new Client("184.72.220.54", 9119); //184.72.220.54 is the aws elastic IP of the server.
    	InputStream inFromWeb = myClient.getInputStream();
    	BufferedReader reader = new BufferedReader(new InputStreamReader(inFromWeb));
    	String message = reader.readLine(); //throws the IOException
    	helloWorldTextView.setText(message);
    }
    
}
