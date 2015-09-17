package com.example.riteish.mtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class directions extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        // Button reference
        Button getDir = (Button)findViewById(R.id.getDirection);


        // getting data for start and end input by user
        final EditText startAddress = (EditText)findViewById(R.id.startEnter);
        final EditText endAddress = (EditText)findViewById(R.id.endEnter);



        // when the button is clicked, process the origin and the destination
        getDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // getting the string out of edit text
                final String origin = startAddress.getText().toString();
                final String dest = endAddress.getText().toString();

                // creating intent to be loaded
                Intent intent = new Intent(getApplicationContext(), directionList.class);
                intent.putExtra("origin", origin);
                intent.putExtra("dest", dest);
                startActivity(intent);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_directions, menu);
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
