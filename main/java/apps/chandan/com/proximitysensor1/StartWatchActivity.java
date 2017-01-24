package apps.chandan.com.proximitysensor1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by sitir on 28-12-2016.
 */

public class StartWatchActivity extends AppCompatActivity {
    static final int CONTACT_PICK=1;
    Intent result=null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_watch);
        Button submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayInfo();
            }
        });

        Button pickContactsButton = (Button) findViewById(R.id.pickContact);
        pickContactsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactsPick();
            }
        });
    }

    public void contactsPick( ) {

        Intent intent= new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));

        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent,CONTACT_PICK);

    }

    protected  void displayInfo(){
        EditText rr= (EditText) findViewById(R.id.refreshRateText);
       // EditText phoneNumber = (EditText) findViewById(R.id.phNumText);
        if(result!=null) {
           // Toast.makeText(this, "Watching contact : "+result, Toast.LENGTH_LONG).show();
            setResult(RESULT_OK,result);
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CONTACT_PICK && resultCode == RESULT_OK) {
           // Intent intent = new Intent();
            result=data;

        }
    }
}
