package com.example.socketsinterfaceandroiddemo;

import android.os.Bundle;
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
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    //make sure that the button works, and that text can be inserted into the text view.
    public void testButton(View view){
    	//Intent testButtonIntent = new Intent(this, MainActivity.class);
    	TextView helloWorldTextView = (TextView)findViewById(R.id.textView1);
    	helloWorldTextView.setText("The button works!");
    }
    
}
