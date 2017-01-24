package apps.chandan.com.proximitysensor1.com.proximitysensor1.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sitir on 27-12-2016.
 */

public class DBHelper extends SQLiteOpenHelper {

    static String dbName="proximity_db";
    public DBHelper (Context context){
        super(context,dbName,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table proximity (id integer primary key, )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
