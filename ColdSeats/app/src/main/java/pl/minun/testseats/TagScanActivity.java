package pl.minun.testseats;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.nfc.NfcAdapter;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;
import android.nfc.tech.NfcF;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import khttp.KHttp;

public class TagScanActivity extends AppCompatActivity {

    private TextView readTagTextView;
    private TextView checkNFCText;
    private NfcAdapter nfcAdapter;
    private PendingIntent mPendingIntent;
    private IntentFilter[] mIntentFilters;
    private String[][] mNFCTechLists;

    @Override
    public void onCreate(Bundle savedState) {
        super.onCreate(savedState);

        setContentView(R.layout.activity_desk_details);
        //readTagTextView = (TextView)findViewById(R.id.textView2);
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        final Context context = this;

        mPendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        //set an intent filter for all MIME data
        IntentFilter ndefIntent = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter tagIntent = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        try {
            ndefIntent.addDataType("*/*");
            mIntentFilters = new IntentFilter[] { ndefIntent, tagIntent };
        } catch (Exception e) {
            Log.e("TagDispatch", e.toString());
        }

        mNFCTechLists = new String[][] { new String[] { NfcF.class.getName() } };

        Toast.makeText(getBaseContext(), "Move phone closer to the tag", Toast.LENGTH_SHORT).show();
    }

    public static String byteArrayToHexString (byte[] b) {
        if (b != null) {
            StringBuilder s = new StringBuilder(2 * b.length);
            for (int i = 0; i < b.length; ++i) {
                final String t = Integer.toHexString(b[i]);
                final int l = t.length();
                if (l > 2) {
                    s.append(t.substring(l - 2));
                } else {
                    if (l == 1) {
                        s.append("0");
                    }
                    s.append(t);
                }
            }
            return s.toString();
        } else {
            return "";
        }
    }

    @Override
    public void onNewIntent(Intent intent) {
        String action = intent.getAction();
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        byte[] byteid = tag.getId();
        String id = byteArrayToHexString(byteid);
        String s = ""; // = action + "\n\n" + tag.toString();

        // parse through all NDEF messages and their records and pick text type only
        Parcelable[] data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if (data != null) {
            try {
                for (int i = 0; i < data.length; ++i) {
                    NdefRecord[] recs = ((NdefMessage)data[i]).getRecords();
                    for (int j = 0; j < recs.length; ++j) {
                        if (recs[j].getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(recs[j].getType(), NdefRecord.RTD_TEXT)) {

                            byte[] payload = recs[j].getPayload();
                            String textEncoding;
                            if ((payload[0] & 0200) == 0) textEncoding = "UTF-8";
                            else textEncoding = "UTF-16";
                            int langCodeLen = payload[0] & 0077;

                            s += new String(payload, langCodeLen + 1, payload.length - langCodeLen - 1, textEncoding);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e("TagDispatch", e.toString());
            }

        }
        //storeInFile(id, s);
        //readTagTextView.setText("Serial: " + id + "\n" + "Data: " + s);
        String res = takeDesk(id);
        Toast.makeText(getBaseContext(), res, Toast.LENGTH_SHORT).show();
        if(res.equals("Operation Successful")){

            Intent myIntent = new Intent(TagScanActivity.this,ChooseFloorActivity.class);
            myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            TagScanActivity.this.startActivity(myIntent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (nfcAdapter != null)
            nfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (nfcAdapter != null)
            nfcAdapter.disableForegroundDispatch(this);
    }

    public String takeDesk(String nfcSerial){
        Map<String, String> map = new HashMap<>();
        map.put("token", SessionInfo.token);
        map.put("nfc_id", nfcSerial.toUpperCase());
        khttp.responses.Response res = KHttp.put(SessionInfo.domain + "/desk", map, map,"token=" + SessionInfo.token + "&nfc_id=" + nfcSerial.toUpperCase());
        JSONObject obj = res.getJsonObject();
        try {
            obj.getString("code");
            return "Can't take desk";
        }catch(Exception ex){

        }
        return "Operation Successful";
    }
}