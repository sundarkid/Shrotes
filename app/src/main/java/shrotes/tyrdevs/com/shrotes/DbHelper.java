package shrotes.tyrdevs.com.shrotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

   private static final String TAG = DbHelper.class.getSimpleName();
   public static final String DBName = "GossipsDB.db";
   public static final int DBVersion = 1;
   public static final String Table = "Preference";

   public DbHelper(Context context) {
       super(context, DBName, null, DBVersion);

   }

   @Override
   public void onCreate(SQLiteDatabase db) {
       String table1 = "create table if not exists periods (id_no VARCHAR, name VARCHAR, short_name VARCHAR, faculty  VARCHAR)";
       String table2 = "create table if not exists events (id_no VARCHAR, name VARCHAR, period_id VARCHAR, date  VARCHAR)";
       String table3 = "create table if not exists personal_details (id_no VARCHAR, data_name VARCHAR, data_value VARCHAR, date  VARCHAR)";
       String table4  = "create table if not exists timetable (id_no VARCHAR, name VARCHAR, day VARCHAR NOT NULL, period  VARCHAR NOT NULL)";
       Log.d(TAG, " This is a log for table being created" + "subjectList");
       db.execSQL(table1);
       db.execSQL(table2);
       db.execSQL(table3);
       db.execSQL(table4);
   }


   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
       // TODO Auto-generated method stub

   }

}