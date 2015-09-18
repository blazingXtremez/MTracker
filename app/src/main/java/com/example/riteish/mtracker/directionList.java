package com.example.riteish.mtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class directionList extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction_list);

        // get origin and destination location from previous activity
        Intent intent = getIntent();
        String origin = intent.getExtras().getString("origin");
        String destination = intent.getExtras().getString("dest");

        // put the origin and destination address in display
        //getting textview reference
        TextView originText = (TextView) findViewById(R.id.origin);
        TextView destText = (TextView) findViewById(R.id.dest);

        // setting the user input text for display
        originText.setText(origin);
        destText.setText(destination);

        // when this activity is created, first it will make the HTTP request for the directions API, get the response
        // and display the response for the origin to destination
        // API key that allows connection to the API
        final String API_KEY = "AIzaSyA4AKkzsN2jFl_sH_Iq_-BpYz4fg7njNMU";

        // create a string file that will create a url for the http request to get the xml file for the direction
        String myUrl = "https://maps.googleapis.com/maps/api/directions/xml?origin=" + origin + "&destination=" + destination + "&mode=transit&key=" + API_KEY;

        // call a different function that uses async task to connect to the url and get the xml file that can be parsed
        // send in the url to be connected to and get the result to be processed further
        List<MyDirections> mylist = null;
        try {
            mylist = (List<MyDirections>) new directionURL().execute(myUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        // direction will be shown in two level list view
        ListView mlist = (ListView) findViewById(R.id.dirList);



        // adpater to display the array list
        myAdapter adapt = new myAdapter(this, mylist);
        mlist.setAdapter(adapt);


        /**
        // setting onclick listener for the listview
        final List<MyDirections> finalMylist = mylist;
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // get the name of the transit method
                TextView mthd = (TextView)view.findViewById(R.id.dirMedium);
                String travelMethod = mthd.getText().toString();

                // load the new Intent that will display the extra information
                Intent intent = new Intent(getApplicationContext(),directionExtra.class);
                intent.putExtra("method", travelMethod);
                intent.putExtra("list", (java.io.Serializable) finalMylist);
                startActivity(intent);
            }
        });**/



        // once the summary of the direction is loaded, one can click on it and
        // load more information about the route

        // setting a onClickListener for the list view


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_direction_list, menu);
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
    // custom adapter to display the list

class myAdapter extends ArrayAdapter<MyDirections>{

    // default constructor for the adapter
    public myAdapter(Context context, List<MyDirections> dirs){
        super (context,0, dirs);
    }


    ////getView overRide
    @Override
    public View getView(int position, View convertView,ViewGroup parent){

        // getting the train data for that position
        MyDirections dir = getItem(position);

        // checking if the view is being reused
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.dir_layout,parent,false);
        }

        // lookup data for textviews
        //TextView method = (TextView)convertView.findViewById(R.id.dirMedium);
        TextView duration = (TextView)convertView.findViewById(R.id.dirDur);
        TextView info = (TextView)convertView.findViewById(R.id.dirInfo);


        // populating the data

        //method.setText(dir.getMethod());

        // the heading will just show the train name and the earliest time it will leave

        duration.setText(dir.getTrainName());
        info.setText(dir.getDepatureTime());

        // note : implementing a future schedule where the user wiill provide the time and
        // it will display the  train schedule for that time

        // return the completed view to be rendered
        return convertView;
    }
}
