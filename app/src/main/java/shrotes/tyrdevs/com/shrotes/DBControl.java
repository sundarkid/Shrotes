package shrotes.tyrdevs.com.shrotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import shrotes.tyrdevs.com.shrotes.DataVariables.CellValues;
import shrotes.tyrdevs.com.shrotes.DataVariables.TableData;

/**
 * Created by Sundareswaran on 30-01-2015.
 */
public class DBControl {
    DbHelper helper;
    SQLiteDatabase db;

    DBControl(Context context){
        helper = new DbHelper(context);
    }

    public void inputValueEditPeriod(TableData[] dataValues) {
        //opening database connection
        db = helper.getWritableDatabase();
        String tbName = "periods";
        for(int j = 0; j < dataValues.length ; j++ ){
            ContentValues cVal = new ContentValues();
            cVal.put("id_no", "" + dataValues[j].getId());
            cVal.put("name", "" + dataValues[j].getPeriodName());
            cVal.put("short_name", "" + dataValues[j].getShortName());
            cVal.put("faculty", "" + dataValues[j].getFaculty());
            db.insert(tbName, null, cVal);
        }
        // Closing database connection
        db.close();
    }
    public void inputValueEditTimeTable(TableData[] dataValues) {
        //opening database connection
        db = helper.getWritableDatabase();
        String tbName = "timetable";
        for(int j = 0; j < dataValues.length ; j++ ){
            // Insert user values in database
            ContentValues cVal = new ContentValues();
            cVal.put("id_no", "" + dataValues[j].getId());
            cVal.put("name", "" + dataValues[j].getPeriodName());
            cVal.put("day", "" + dataValues[j].getDay());
            cVal.put("period", "" + dataValues[j].getPeriod());
            db.insert(tbName, null, cVal);
        }
        // Closing database connection
        db.close();
    }
    public void inputValueEvents(TableData[] dataValues) {
        //opening database connection
        db = helper.getWritableDatabase();
        String tbName = "events";
        for(int j = 0; j < dataValues.length ; j++ ){
            // Insert user values in database
            ContentValues cVal = new ContentValues();
            cVal.put("id_no", "" + dataValues[j].getId());
            cVal.put("name", "" + dataValues[j].getPeriodName());
            cVal.put("period_id", "" + dataValues[j].getPeriod_id());
            cVal.put("date", "" + dataValues[j].getDate());
            db.insert(tbName, null, cVal);
        }
        // Closing database connection
        db.close();
    }
    public void inputPersonalDetails(TableData[] dataValues) {
        //opening database connection
        db = helper.getWritableDatabase();
        String tbName = "personal_details";
        for(int j = 0; j < dataValues.length ; j++ ){
            // Insert user values in database
            ContentValues cVal = new ContentValues();
            cVal.put("id_no", "" + dataValues[j].getId());
            cVal.put("data_name", "" + dataValues[j].getData_Name());
            cVal.put("data_value", "" + dataValues[j].getData_value());
            cVal.put("date", "" + dataValues[j].getDate());
            db.insert(tbName, null, cVal);
        }
        // Closing database connection
        db.close();
    }
    public void personalData(TableData dataValues) {
        //opening database connection
        db = helper.getWritableDatabase();
        String tbName = "personal_details";
            ContentValues cVal = new ContentValues();
            cVal.put("data_name", "" + dataValues.getData_Name());
            cVal.put("data_value", "" + dataValues.getData_value());
            db.insert(tbName, null, cVal);
        // Closing database connection
        db.close();
    }
   public ArrayList<TableData> getTimeTableData(){
       ArrayList<TableData> data = new ArrayList<TableData>();
       //opening database connection
       db = helper.getWritableDatabase();
       //Creating cursor
       Cursor cursor = db.rawQuery("Select * from timetable", null);
       //Adding values into the array list
       cursor.moveToFirst();
       while(!cursor.isAfterLast()){
           TableData result = new TableData();
           result.put_id(Integer.parseInt(cursor.getString(0)));
           result.putData_Name(cursor.getString(1));
           result.putDay(Integer.parseInt(cursor.getString(2)));
           result.putPeriod(Integer.parseInt(cursor.getString(3)));
           data.add(result);
           cursor.moveToNext();
       }
       db.close();
       return data;
   }
    public ArrayList<TableData> getPersonalDetails(){
        ArrayList<TableData> data = new ArrayList<TableData>();
        //opening database connection
        db = helper.getWritableDatabase();
        //Creating cursor
        Cursor cursor = db.rawQuery("Select * from personal_details", null);
        //Adding values into the array list
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            TableData result = new TableData();
            if(cursor.getString(0) != null)
                result.put_id(Integer.parseInt(cursor.getString(0)));
            else
                result.put_id(-1);

            result.putData_Name(cursor.getString(1));
            if(cursor.getString(2) != null && cursor.getString(1).equals("days"))
                result.putDay(Integer.parseInt(cursor.getString(2)));
            else if(cursor.getString(2) != null && cursor.getString(1).equals("periods"))
                result.putPeriod(Integer.parseInt(cursor.getString(2)));
            else
                result.putData_Value(cursor.getString(2));

            result.putDate(cursor.getString(3));
            data.add(result);
            cursor.moveToNext();
        }
        db.close();
        return data;
    }
    public void periodsData(TableData dataValues, String name) {
        //opening database connection
        //opening database connection
        db = helper.getWritableDatabase();

        String tbName = "periods";
        //Creating cursor
        Cursor cursor = db.rawQuery("Select * from periods", null);
        //Adding values into the array list
        db.delete(tbName,"name = ?", new String[]{name});
        ContentValues cVal = new ContentValues();
        cVal.put("name", "" + dataValues.getData_Name());
        cVal.put("short_name", "" + dataValues.getShortName());
        cVal.put("faculty", "" + dataValues.getFaculty());
        db.insert(tbName, null, cVal);
        // Closing database connection
        db.close();
    }
    public ArrayList<CellValues> getTabledata(){
        ArrayList<CellValues> data = new ArrayList<CellValues>();
        //opening database connection
        db = helper.getWritableDatabase();
        //Creating cursor
        Cursor cursor = db.rawQuery("Select * from timetable", null);
        //Adding values into the array list
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            CellValues result = new CellValues();
            result.putName(cursor.getString(1));
            if(cursor.getString(2) != null)
                 result.putDay(Integer.parseInt(cursor.getString(2)));
            if(cursor.getString(3) != null)
                result.putPeriod(Integer.parseInt(cursor.getString(3)));
            data.add(result);
            cursor.moveToNext();
        }
        db.close();
        return data;
    }
    public void inputTableData(TableData data, String day, String period){
        //opening database connection
        //opening database connection
        db = helper.getWritableDatabase();
        String tbName = "timetable";
        //Creating cursor
        Cursor cursor = db.rawQuery("Select * from periods", null);
        //Adding values into the array list
        db.delete(tbName,"day = ? AND period = ?", new String[]{day, period});
        ContentValues cVal = new ContentValues();
        cVal.put("name", "" + data.getData_Name());
        cVal.put("day", "" + data.getShortName());
        cVal.put("period", "" + data.getFaculty());
        db.insert(tbName, null, cVal);
        // Closing database connection
        db.close();

    }
}
