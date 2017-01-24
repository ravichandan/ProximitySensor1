package apps.chandan.com.proximitysensor1;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by sitir on 08-01-2017.
 */

public class TagMemberUsingCodeActivity extends AppCompatActivity{

    PopupWindowCompat popupC;
    PopupWindow popup;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_scan_code);
    }
    public void generateCode(View view){
        if(popup !=null)
            popup.dismiss();
        Random rand= new Random();
        int number=rand.nextInt()/100;
        TextView tvMsg = new TextView(this);
        tvMsg.setText("Please enter this code in the other device in 60 seconds : "+number);
        LinearLayout layout= new LinearLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.addView(tvMsg,layoutParams);
        popup= new PopupWindow(TagMemberUsingCodeActivity.this);//, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.setContentView(layout);
        popup.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        popup.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        popup.showAtLocation(view,Gravity.CENTER,0,0);
        //PopupWindowCompat.showAsDropDown(popup,view ,0,0, Gravity.CENTER_HORIZONTAL);
        //PopupWindowCompat.setOverlapAnchor(popup, true);

        popup.getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopup();
            }
        });
    }

    public void receiveCode(View view){
        if(popup !=null)
            popup.dismiss();

        LayoutInflater inflater= (LayoutInflater) TagMemberUsingCodeActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.receive_code_popup, (ViewGroup)findViewById(R.id.popup_element));
        popup= new PopupWindow(layout, RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.MATCH_PARENT,true);
        //popup.showAsDropDown(view);

        popup.showAtLocation(view,Gravity.CENTER,0,0);
        //PopupWindowCompat.showAsDropDown(popup,view ,0,0, Gravity.CENTER_HORIZONTAL);
        Button okButton = (Button) layout.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText rr= (EditText) layout.findViewById(R.id.receivedCode);
                dismissPopup();
                Toast.makeText(TagMemberUsingCodeActivity.this,rr.getText(),Toast.LENGTH_LONG).show();
            }
        });
        Button cancelButton= (Button) layout.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPopup();
            }
        });

    }
    public void dismissPopup(){
        if(popup !=null)
            popup.dismiss();

    }

    @Override
    protected void onPause() {
        dismissPopup();
        super.onPause();
    }
}
