package pl.minun.testseats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class MapActivity extends AppCompatActivity {

    private FloorPlan floorPlan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        floorPlan = new FloorPlan(SessionInfo.buildingName, SessionInfo.floorName, SessionInfo.forToday);
        setTitle(SessionInfo.buildingName + " : " + SessionInfo.floorName);
        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setNumColumns(floorPlan.width);

        DisplayMetrics displayMetrics = MapActivity.this.getResources().getDisplayMetrics();

        //Toast.makeText(MapActivity.this,displayMetrics.widthPixels + "", Toast.LENGTH_SHORT).show();

        //float dpHeight = displayMetrics.heightPixels / displayMetrics.density;


        //gridview.setColumnWidth(displayMetrics.widthPixels/20);
        //gridview.setColumnHeight(displayMetrics.heightPixels/8);
        //gridview.setAdapter(new ImageAdapter(this));
        gridview.setAdapter(new GridAdapter(this, floorPlan));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                //Toast.makeText(MapActivity.this, "Number:" + floorPlan.shape[position] + "\n status: " + floorPlan.deskToStatusMap.get(floorPlan.shape[position]),
                    //    Toast.LENGTH_SHORT).show();

                SessionInfo.deskId = floorPlan.shape[position];
                if(!SessionInfo.deskId.equals("0")) {
                    Intent myIntent = new Intent(MapActivity.this, DeskDetails.class);
                    MapActivity.this.startActivity(myIntent);
                }

            }
        });

    }



}
