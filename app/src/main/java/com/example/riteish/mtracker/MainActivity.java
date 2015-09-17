package com.example.riteish.mtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;




public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // working on service status update button and see if that can be created
        final Button serviceAlert = (Button)findViewById(R.id.servicealerts);

        // when this button is clicled, perform some action
        serviceAlert.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v){
                    loadService(serviceAlert);
                }

        });

        // working on the button for train live feeds
        final Button liveFeed = (Button)findViewById(R.id.tracker);

        // setting on click lister that will load a different class for that screen
        liveFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // loadeing the intent, that will display the livefeed java file
                loadService(liveFeed);
            }
        });

        // button for loading subway map
        final Button map = (Button) findViewById(R.id.maps);

        // setting onclick listener for the button
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadService(map);
            }
        });

        // button for route directions from a origin to destination
        final Button direct  = (Button) findViewById(R.id.direction);

        // setting onclick listener for the button
        direct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadService(direct);
            }
        });


    }


    // loadservice method that will load the intent to get the service status window
    public void loadService(Button b){
        // initializing the intent
        Intent intent;

        // setting Intent for each buttons pressed it will load a different activity
        // if button is the service Alert load that page
        if (b == (Button)findViewById(R.id.servicealerts)){
            intent = new Intent(this, serviceStatus.class);
            startActivity(intent);
        }

        // if the button is for livefeed, load that page
        else if (b == (Button)findViewById(R.id.tracker)){
            intent = new Intent(this, liveTracker.class);
            startActivity(intent);
        }


        // if the button pressed is for map then load the map intent
        else if (b == (Button)findViewById(R.id.maps)){
            intent = new Intent(this, mapZoom.class);
            startActivity(intent);
        }

        // if the direction button is pressed it will load into the finding direction window
        else if (b == (Button)findViewById(R.id.direction)){
            intent  = new Intent(this,directions.class);
            startActivity(intent);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
