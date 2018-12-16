package pl.minun.testseats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import khttp.KHttp;

public class DeskDetails extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;
    private Button deleteRsv;

    private TextView building;
    private TextView floor;
    private TextView deskID;
    private TextView status;
    private TextView who;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desk_details);

        mButton = (Button) findViewById(R.id.button);
        deleteRsv = (Button) findViewById(R.id.delete);
        //mTextView = (TextView) findViewById(R.id.textView2);
        building = (TextView) findViewById(R.id.building);
        floor = (TextView) findViewById(R.id.floor);
        deskID = (TextView) findViewById(R.id.deskID);
        status = (TextView) findViewById(R.id.status);
        who = (TextView) findViewById(R.id.who);

        setDeskDetails();

        if(SessionInfo.forToday) {
            if(status.getText().equals("FREE") || status.getText().equals("MY_RESERVED"))
                mButton.setText("Scan NFC to take");
            else
                mButton.setVisibility(View.GONE);

        }else{
            if(status.getText().equals("FREE"))
                mButton.setText("Reserve");
            else
                mButton.setVisibility(View.GONE);
        }

        //deleteRsv.setVisibility(View.VISIBLE);
        if(!(status.getText().equals("MY_RESERVED") ||  status.getText().equals("MY_TAKEN")))
            deleteRsv.setVisibility(View.GONE);

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(SessionInfo.forToday) {
                    Intent myIntent = new Intent(DeskDetails.this, TagScanActivity.class);
                    DeskDetails.this.startActivity(myIntent);
                }else{
                    String resp = reserveDesk();
                    Toast.makeText(getBaseContext(), resp, Toast.LENGTH_SHORT).show();
                    if(resp.equals("Operation Successful")) {
                        Intent myIntent = new Intent(DeskDetails.this, ChooseFloorActivity.class);
                        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        DeskDetails.this.startActivity(myIntent);
                    }
                }
            }
        });

        deleteRsv.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String resp = deleteReservation();
                Toast.makeText(getBaseContext(), resp, Toast.LENGTH_SHORT).show();
                if (resp.equals("Operation Successful")) {
                    Intent myIntent = new Intent(DeskDetails.this, ChooseFloorActivity.class);
                    myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    DeskDetails.this.startActivity(myIntent);
                }
            }
        });


    }

    public String deleteReservation(){
        khttp.responses.Response res;
        if(SessionInfo.forToday)
            res = KHttp.delete(SessionInfo.domain + "/desk?token=" + SessionInfo.token);
        else
            res = KHttp.delete(SessionInfo.domain + "/desk?token=" + SessionInfo.token + "&date=" + FloorPlan.getDateTomorrow());

        JSONObject obj = res.getJsonObject();
        try {
            obj.getString("code");
            return "Can't delete reservation";
        }catch(Exception ex){

        }
        return "Operation Successful";
    }
    public String reserveDesk(){
        Map<String, String> map = new HashMap<>();
        map.put("token", SessionInfo.token);
        map.put("id_desk", SessionInfo.deskId);
        map.put("date", FloorPlan.getDateTomorrow());
        khttp.responses.Response res = KHttp.put(SessionInfo.domain + "/desk", map, map,"token=" + SessionInfo.token + "&id_desk=" + SessionInfo.deskId +"&date=" + FloorPlan.getDateTomorrow());
        JSONObject obj = res.getJsonObject();
        try {
            obj.getString("code");
            return "Can't reserve desk";
        }catch(Exception ex){

        }
        return "Operation Successful";
    }

    private void setDeskDetails(){
        try{
            khttp.responses.Response res;
            if(SessionInfo.forToday)
              res = KHttp.get(SessionInfo.domain + "/desk?id_desk=" + SessionInfo.deskId + "&token=" + SessionInfo.token);
            else
               res = KHttp.get(SessionInfo.domain + "/desk?id_desk=" + SessionInfo.deskId + "&date=" + FloorPlan.getDateTomorrow() + "&token=" + SessionInfo.token);
            JSONObject obj = res.getJsonObject();

            building.setText(obj.getString("building_name"));
            floor.setText(obj.getString("floor_name"));
            deskID.setText(obj.getString("id_desk"));
            status.setText(obj.getString("status"));
            who.setText(obj.getString("username"));

            } catch (Exception ex) {

            }
    }
}
