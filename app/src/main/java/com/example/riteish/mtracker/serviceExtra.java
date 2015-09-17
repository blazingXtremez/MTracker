package com.example.riteish.mtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;


public class serviceExtra extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_extra);

        // getting the list sent from intent
        Intent i = getIntent();
        List<Trains> trainList = (List<Trains>)i.getSerializableExtra("list");

        // get the passed trainName value that will help us look through the file
        String tName = (String)i.getStringExtra("name");

        // seeing if it is working
        TextView myText = (TextView)findViewById(R.id.myText);

        String date;
        String time;
        String info;

        // getting the train status, if the status is not "GOOD SERVICE" display the detail
        for (Trains train: trainList){
            if (train.getTrainName().equals(tName)){

                // get the information related to the train
                date = train.getDate();
                info = train.getInfo();

                // parsing info String for removing unwanted characters
                if(info.contains("&")){
                    String[] myInfo = info.split(";");
                    for (String s: myInfo){
                        if (!s.contains("&")){
                            info = "".concat(s);
                        }
                    }
                }

                // show the data using TextView
                myText.append(date);
                myText.append("\n");
                myText.append(info);

            }
        }
        // making my text scrollable
        myText.setMovementMethod(new ScrollingMovementMethod());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_service_extra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
