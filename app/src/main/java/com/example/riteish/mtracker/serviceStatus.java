package com.example.riteish.mtracker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class serviceStatus extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_status);


        String url = "http://web.mta.info/status/serviceStatus.txt";


        /**
        //creating a reference to the TextView
        TextView status = (TextView)findViewById(R.id.infoText);
        // setting scrolling for textview
        status.setMovementMethod(new ScrollingMovementMethod());


        // connecting to file
        String trainName;
        String trainStatus;
        **/
        // connecting to get the trainlist list
        List<Trains> myList = null;
        try {
            myList = (List<Trains>) new OpenUrl().execute(url).get();
            // updating list to get rid of null objects
            // creating new list that filters these all

            // calling iterator on the list to update the list to get rid of null elements
            Iterator<Trains> iter = myList.iterator();

            while (iter.hasNext()){
                Trains a = iter.next();
                if (a.trainName != null){

                    if (a.information != null && a.date != null && a.time != null){
                        // do nothing
                    }
                    else{
                        a.information = " ";
                        a.time = " ";
                        a.date = " ";
                    }
                }
                else{
                    iter.remove();
                }
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // creating a Listview to display the data
        ListView lstView = (ListView)findViewById(R.id.myList);

        /**    // adapter for list view
        ArrayAdapter<Trains> adapter = new ArrayAdapter<Trains>(this, android.R.layout.simple_list_item_1,myList);
        lstView.setAdapter(adapter);
        **/

        // using custom defined adapter
        userAdapter adapter = new userAdapter(this,myList);
        lstView.setAdapter(adapter);

        // setting listener onclick listener for each item in the list
        final List<Trains> finalMyList = myList;
        lstView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // get the TextView access to trainStatus for enabling the onItemClick
                TextView c = (TextView)view.findViewById(R.id.trainStatus);


                // referencing TextView for train name
                TextView d = (TextView)view.findViewById(R.id.trainName);

                // get the train status value
                String tStatus = c.getText().toString();

                // get the trainName value
                String tName = d.getText().toString();

                // if the train status is not "GOOD SERVICE", load the new activity that will show
                // the information on delays and such
                if (!tStatus.equals("GOOD SERVICE")){
                    Intent intent = new Intent(getApplicationContext(),serviceExtra.class);
                    intent.putExtra("list", (java.io.Serializable) finalMyList);
                    intent.putExtra("name",tName);
                    startActivity(intent);
                }


            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_service_status, menu);
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

// defining a adapter that will only show the desired information
class userAdapter extends ArrayAdapter<Trains>{

    // default constructor for the adapter
    public userAdapter(Context context, List<Trains> trains){
        super (context,0, trains);
    }

    //getView overRide
    @Override
    public View getView(int position, View convertView,ViewGroup parent){

        // getting the train data for that position
        Trains train = getItem(position);

        // checking if the view is being reused
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user,parent,false);
        }
        // lookup data for textviews
        TextView trainName = (TextView)convertView.findViewById(R.id.trainName);
        TextView trainStatus = (TextView)convertView.findViewById(R.id.trainStatus);

       // setting the color for trainStatus, if it is not good service make it red
        if (train.getStatus().equals("GOOD SERVICE")){
            trainStatus.setTextColor(Color.parseColor("#17b608"));
        }

        else{
            trainStatus.setTextColor(Color.parseColor("#f63201"));
        }

        // populating the data
        trainName.setText(train.getTrainName());
        trainStatus.setText(train.getStatus());

        // return the completed view to be rendered
        return convertView;
    }

}
