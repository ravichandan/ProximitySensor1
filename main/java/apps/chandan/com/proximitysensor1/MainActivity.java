package apps.chandan.com.proximitysensor1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import apps.chandan.com.proximitysensor1.entities.Member;
import apps.chandan.com.proximitysensor1.entities.Watch;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    static final int MEMBER_PICK =1;
    static final int TAG_MEMBER=2;

    private ArrayList<Watch> members = new ArrayList<Watch>();
    private MyListAdapter adapter;
    private Member selectedMember;
    private final Member currentMember  = new Member("Chandan ");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new MyListAdapter (this,R.layout.activity_list_item_view,R.id.label,R.id.statusLabel, members);

        ListView lv = (ListView) findViewById(R.id.watch_list);
        lv.setAdapter(adapter);

       //lv.setOnClickListener(MainActivity.this);

    }

    public void startWatch(View view){
        Intent intent = new Intent(this,StartWatchActivity.class);
        startActivityForResult(intent, MEMBER_PICK);
    }

    public void tagMember(View view){

        //Intent intent = new Intent(this, TagMemberUsingNfcActivity.class);
        //startActivityForResult(intent,TAG_MEMBER);
        Intent intent = new Intent(this, TagMemberUsingCodeActivity.class);
        startActivityForResult(intent,TAG_MEMBER);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MEMBER_PICK && resultCode==RESULT_OK) {
            Uri contact=data.getData();
            String [] projection = {Phone.DISPLAY_NAME,Phone.NUMBER};
            Cursor cursor= getContentResolver().query(contact,projection,null,null,null);
            cursor.moveToFirst();

            String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
            Member m1=new Member(name);
            if(currentMember.getWatchMap().get(m1)==null)
                currentMember.getWatchMap().put(m1,new Watch(currentMember,m1));
            if(!members.contains(currentMember.getWatchMap().get(m1)))
                members.add(currentMember.getWatchMap().get(m1));
            adapter.notifyDataSetChanged();
            cursor.close();
        }
       /* if (requestCode == TAG_MEMBER  && resultCode==RESULT_OK) {
            Uri contact=data.getData();
            String [] projection = {Phone.DISPLAY_NAME,Phone.NUMBER};
            Cursor cursor= getContentResolver().query(contact,projection,null,null,null);
            cursor.moveToFirst();

            String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
            String number = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));

            members.add(name +" - "+number);
            adapter.notifyDataSetChanged();
        }*/
    }

    public void showPopup(final View view){
        //Toast.makeText(this,"Showing popup",Toast.LENGTH_SHORT).show();;
        view.post(new Runnable() {
            @Override
            public void run() {
                showPopupMenu(view);
            }
        });


    }

    public void showPopupMenu(final View v) {
        Watch w1=members.get((Integer) v.getTag());
        if(w1!=null)
            selectedMember =w1.getTarget();
        if(currentMember.getWatchMap().get(selectedMember)==null)
            currentMember.getWatchMap().put(selectedMember,new Watch(currentMember,selectedMember));

        PopupMenu pMenu= new PopupMenu(MainActivity.this,v);
        pMenu.getMenuInflater().inflate(R.menu.popup_menu,pMenu.getMenu() );
        pMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.startWatching:
                        Toast.makeText(MainActivity.this,"Watching "+v.getTag(),Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.configureTrack:

                        v.post(new Runnable() {
                            @Override
                            public void run() {
                                configureTrack(v);
                            }
                        });

                        return true;
                }
                return false;
            }
        });
        pMenu.show();
    }

    public void configureTrack(View v) {
        Watch w1=members.get((Integer) v.getTag());
        if(w1!=null)
            selectedMember =w1.getTarget();

        if(currentMember.getWatchMap().get(selectedMember)==null)
            currentMember.getWatchMap().put(selectedMember,new Watch(currentMember,selectedMember));
        //LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View configurePopup=getLayoutInflater().inflate(R.layout.configure_track_popup,null);

        final PopupWindow popup= new PopupWindow(MainActivity.this);

                popup.setContentView(configurePopup);
        popup.setHeight(RelativeLayout.LayoutParams.WRAP_CONTENT);
        popup.setWidth(RelativeLayout.LayoutParams.MATCH_PARENT);
        popup.setFocusable(true);

        Button okConfigure= (Button) configurePopup.findViewById(R.id.okConfigure);
        okConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setNewConfiguration(configurePopup);
                popup.dismiss();
            }
        });
        Button cancelConfigure= (Button) configurePopup.findViewById(R.id.cancelConfigure);
        cancelConfigure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        Spinner timeSpinner= (Spinner) configurePopup.findViewById(R.id.timeSpinner);
        String items[]=getResources().getStringArray(R.array.time_interval);
        ArrayAdapter adapter1= new ArrayAdapter(MainActivity.this,R.layout.support_simple_spinner_dropdown_item,items);
        timeSpinner.setAdapter(adapter1);
        if(selectedMember!=null && currentMember.getWatchMap().get(selectedMember)!=null)
        {
            timeSpinner.setSelection(adapter1.getPosition(String.valueOf(currentMember.getWatchMap().get(selectedMember).getRefreshInterval())));
            EditText et= (EditText) configurePopup.findViewById(R.id.safeDistanceText);
            et.setText(String.valueOf(currentMember.getWatchMap().get(selectedMember).getDistanceAllowed()));
        }
        popup.showAtLocation(configurePopup, Gravity.CENTER,0,0);

    }

    private void setNewConfiguration(View v) {


        Spinner intervalSpinner= (Spinner) v.findViewById(R.id.intervalSpinner);
        Spinner timeSpinner= (Spinner) v.findViewById(R.id.timeSpinner);
        timeSpinner.getSelectedItemPosition();
        int intervalFactor=  Integer.parseInt(timeSpinner.getSelectedItem().toString());

        // TODO int intervalPos=intervalSpinner.getSelectedItemPosition();
        /*if(intervalPos==1)
            intervalFactor=60*intervalFactor;
        if(intervalPos==2)
            intervalFactor=60*24*intervalFactor;
        */
        currentMember.getWatchMap().get(selectedMember).setRefreshInterval(intervalFactor);
        EditText et= (EditText) v.findViewById(R.id.safeDistanceText);
        if(!TextUtils.isEmpty(et.getText()))
            currentMember.getWatchMap().get(selectedMember).setDistanceAllowed(Integer.parseInt(et.getText().toString()));

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ListView l = (ListView) parent;
        Toast.makeText(MainActivity.this,""+parent.getItemAtPosition(position),Toast.LENGTH_SHORT).show();
    }


    class MyListAdapter extends  ArrayAdapter
    {
        TextView statusView;
        int statusRes;
        public MyListAdapter( Context context,  int resource,int textViewRes,int statusRes,
                              List list){
            super(context,resource,textViewRes,list);
            this.statusRes=statusRes;
        }

        @Nullable
        @Override
        public String getItem(int position) {
            int i = 9;
            return ((Watch)super.getItem(position)).getTarget().getDisplayName();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view= super.getView(position, convertView, parent);
            if(statusRes==0)
                statusView= (TextView) view.findViewById(R.id.statusLabel);
            else
                statusView= (TextView) view.findViewById(statusRes);
            final Watch currWatch= ((Watch)super.getItem(position));
            statusView.setText(currWatch.getStatusMessage());

            final SurfaceView sView= (SurfaceView) view.findViewById(R.id.surfaceView);


            View pBtn=view.findViewById(R.id.button_popup);
            pBtn.setTag(position);
            final Switch startTrackSwitch= (Switch) view.findViewById(R.id.startTrackSwitch);
            startTrackSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    currWatch.setTracking(isChecked);
                    if(isChecked) {
                        currWatch.setStatusMessage((getResources().getString(R.string.watchingMessage)));
                         //Toast.makeText(MainActivity.this,"Started watching ",Toast.LENGTH_SHORT).show();
                        sView.setBackgroundColor(Color.parseColor("#458b00"));

                    }
                    else {
                        currWatch.setStatusMessage(getResources().getString(R.string.notWatchingMessage));
                        //Toast.makeText(MainActivity.this, "Watching stopped ", Toast.LENGTH_SHORT).show();
                        sView.setBackgroundColor(Color.GRAY);
                    }
                    statusView.setText(currWatch.getStatusMessage());
                    adapter.notifyDataSetChanged();
                }
            });
            return view;
        }
    }
}
