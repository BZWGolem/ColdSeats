package pl.minun.testseats;

import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import khttp.KHttp;

public class FloorPlan {
    public String[] shape;
    public int height = 10;
    public int width = 10;
    public Map<String, String> deskToStatusMap;

    public FloorPlan(String buildingName, String floorName, boolean forToday) {
        khttp.responses.Response res;
        if(forToday)
            res = KHttp.get(SessionInfo.domain + "/floor?" + "building_name=" + buildingName + "&number=" + floorName + "&token=" + SessionInfo.token);
        else
            res = KHttp.get(SessionInfo.domain + "/floor?" + "building_name=" + buildingName + "&number=" + floorName + "&token=" + SessionInfo.token + "&date=" + getDateTomorrow());
        JSONObject obj = res.getJsonObject();

        deskToStatusMap = new HashMap<>();
        JSONArray floorList = obj.optJSONArray("desks_id");
        try {
            for (int i = 0; i < floorList.length(); ++i) {
                JSONObject subObj = floorList.getJSONObject(i);
                deskToStatusMap.put(subObj.getString("id_desk"), subObj.getString("status"));
            }

            deskToStatusMap.put("0", "EMPTY");


            ArrayList<String> floorArrayList = new ArrayList<>();
            shape = obj.getString("shape").split(",");
            width = obj.getInt("width");
        } catch (Exception ex) {

        }

    }

    public static String getDateTomorrow(){
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return df.format(cal.getTime());
    }
}
