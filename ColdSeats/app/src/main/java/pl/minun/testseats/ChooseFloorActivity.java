package pl.minun.testseats;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import khttp.KHttp;

public class ChooseFloorActivity extends AppCompatActivity {

    private Spinner building;
    private Spinner floor;
    private Button todayButton;
    private Button tomorrowButton;
    private  Button findMeButton;
    private  Button scanNFCButton;
    private ArrayAdapter<String> buildingAdapter;
    private ArrayAdapter<String> floorAdapter;
    private String[] items = new String[]{"", "1", "2", "three"};
    private String[] items2 = new String[]{};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SessionInfo.buildingName = null;
        SessionInfo.floorName = null;
        setContentView(R.layout.activity_choose_floor);
        building = (Spinner) findViewById(R.id.building);
        floor = (Spinner) findViewById(R.id.floor);
        todayButton = (Button) findViewById(R.id.today_button);
        tomorrowButton = (Button) findViewById(R.id.tomorrow_button);
        findMeButton = (Button) findViewById(R.id.findMeButton);
        scanNFCButton = (Button) findViewById(R.id.scan_nfc);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        getBuildings();
        buildingAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        floorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);

//set the spinners adapter to the previously created one.
        building.setAdapter(buildingAdapter);
        floor.setAdapter(floorAdapter);

        building.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateFloors();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                clearFloors();
/*                floorAdapter.clear();
                floor.setAdapter(floorAdapter);*/
            }

        });

        todayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SessionInfo.buildingName = building.getSelectedItem().toString();
                SessionInfo.floorName = floor.getSelectedItem().toString();
                SessionInfo.forToday = true;
                Intent myIntent = new Intent(ChooseFloorActivity.this,MapActivity.class);
                ChooseFloorActivity.this.startActivity(myIntent);
            }
        });

        tomorrowButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SessionInfo.buildingName = building.getSelectedItem().toString();
                SessionInfo.floorName = floor.getSelectedItem().toString();
                SessionInfo.forToday = false;
                Intent myIntent = new Intent(ChooseFloorActivity.this,MapActivity.class);
                ChooseFloorActivity.this.startActivity(myIntent);
            }
        });

        findMeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                khttp.responses.Response res = KHttp.get(SessionInfo.domain + "/floor?token=" + SessionInfo.token);
                JSONObject obj = res.getJsonObject();
                try {
                    SessionInfo.buildingName =  obj.getString("building_name");
                    SessionInfo.floorName =  obj.getString("number");
                    SessionInfo.forToday = true;
                    Intent myIntent = new Intent(ChooseFloorActivity.this,MapActivity.class);
                    ChooseFloorActivity.this.startActivity(myIntent);
                }catch(Exception ex){
                    Toast.makeText(getBaseContext(), "No reservation for today", Toast.LENGTH_SHORT).show();
                }

            }
        });

        scanNFCButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(ChooseFloorActivity.this,TagScanActivity.class);
                ChooseFloorActivity.this.startActivity(myIntent);
            }
        });

    }

    void getBuildings(){

        khttp.responses.Response res = KHttp.get(SessionInfo.domain + "/building");
        JSONObject obj = res.getJsonObject();

       // Toast.makeText(ChooseFloorActivity.this, obj.toString(),
        //        Toast.LENGTH_SHORT).show();
        try {
             JSONArray bulidingList= obj.optJSONArray("building_list");
            ArrayList<String> buildingsArrayList = new ArrayList<>();
            for (int i = 0; i < bulidingList.length(); ++i) {
                JSONObject subObj = bulidingList.getJSONObject(i);
                buildingsArrayList.add(subObj.getString("building_name"));
            }
       items = buildingsArrayList.toArray(new String[0]);
        }catch (Exception ex){
            return;
        }

    }

    void updateFloors(){


        khttp.responses.Response res = KHttp.get(SessionInfo.domain + "/building/" +building.getSelectedItem().toString());
        JSONObject obj = res.getJsonObject();

        //Toast.makeText(ChooseFloorActivity.this, obj.toString(),
         //       Toast.LENGTH_SHORT).show();
        try {
            JSONArray floorList= obj.optJSONArray("floor_list");
            ArrayList<String> floorArrayList = new ArrayList<>();
            for (int i = 0; i < floorList.length(); ++i) {
                JSONObject subObj = floorList.getJSONObject(i);
                floorArrayList.add(subObj.getString("floor_name"));
            }
            items2 = floorArrayList.toArray(new String[0]);
        }catch (Exception ex){
            return;
        }

       //floorAdapter.clear();
        floorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        floor.setAdapter(floorAdapter);
    }

    void clearFloors(){
        items2 = new String[]{};
        //floorAdapter.clear();
        floorAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        floor.setAdapter(floorAdapter);
    }
}
