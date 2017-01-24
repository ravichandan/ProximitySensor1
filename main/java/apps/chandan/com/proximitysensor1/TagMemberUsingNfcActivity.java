package apps.chandan.com.proximitysensor1;


import android.content.pm.PackageManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by sitir on 05-01-2017.
 */

public class TagMemberUsingNfcActivity extends AppCompatActivity {

    NfcAdapter nfcAdapter;
    boolean androidBeamAvailable;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this," NFC  0", Toast.LENGTH_LONG).show();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC)) {
            // disable add device by touching other device
            Toast.makeText(this,"No NFC available on this device1", Toast.LENGTH_LONG).show();
        }
         if(Build.VERSION.SDK_INT  < Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            androidBeamAvailable=false;
            Toast.makeText(this,"No NFC available on this device2", Toast.LENGTH_LONG).show();
        }
        else if(isExternalStorageAvailable()) {
            Toast.makeText(this,"NFC available on this device3", Toast.LENGTH_LONG).show();

            androidBeamAvailable=true;
            nfcAdapter= NfcAdapter.getDefaultAdapter(this);

            String fileName = "offerHandShake";
            String text = "Offer Handshake";
            /*File file = new File(Environment.getExternalStorageDirectory(),fileName);
             try {
                 file.createNewFile();
                 System.out.println(file.exists() +"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
             } catch (IOException e) {
                 e.printStackTrace();
             }
             boolean created= file.mkdirs();
             System.out.println(created +"*******************************************************************888");
            FileOutputStream outputStream;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write(text.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            setContentView(R.layout.activity_add_member_nfc);
            NdefMessage nm= new NdefMessage(new NdefRecord[]{NdefRecord.createMime("application/vnd.com.example.android.beam",fileName.getBytes())});
            nfcAdapter.setNdefPushMessage(nm,this);

        }
        setContentView(R.layout.activity_add_member_nfc);

    }






    public boolean isExternalStorageAvailable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
