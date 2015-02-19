package shrotes.tyrdevs.com.shrotes.DataVariables;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sundareswaran on 30-01-2015.
 */
public class TableData implements Parcelable{
    int day;
    int id;
    int period;
    int period_id;
    String date;
    String periodName;
    String shortName;
    String faculty;
    String data_name;
    String data_value;

    public String getPeriodName(){
        return periodName;
    }
    public void putPeriodName(String name){
        this.periodName = name;
    }
    public String getShortName(){
        return shortName;
    }
    public void putShortName(String name){
        this.periodName = name;
    }
    public int getDay(){
        return day;
    }
    public void putDay(int day){
        this.day = day;
    }
    public int getPeriod(){
        return period;
    }
    public void putPeriod(int period){
        this.period = period;
    }
    public int getPeriod_id(){
        return period_id;
    }
    public void putPeriod_id(int period_id){
        this.period_id = period_id;
    }
    public void put_id(int id){
        this.id = id;
    }
    public int getId(){
        return id;
    }
    public void putFaculty(String faculty){
        this.faculty = faculty;
    }
    public String getFaculty(){
        return faculty;
    }
    public void putDate(String date){
        this.date = date;
    }
    public String getDate(){
        return date;
    }
    public void putData_Value(String data_value){
        this.data_value = data_value;
    }
    public String getData_value(){
        return data_value;
    }
    public void putData_Name(String data_name){
        this.data_name = data_name;
    }
    public String getData_Name(){
        return data_name;
    }

    public static final Creator<TableData> CREATOR = new Creator<TableData>() {
        @Override
        public TableData createFromParcel(Parcel parcel) {
            return new TableData(parcel);
        }

        @Override
        public TableData[] newArray(int i) {
            return new TableData[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(periodName);
        parcel.writeString(shortName);
        parcel.writeString(data_name);
        parcel.writeString(data_value);
        parcel.writeString(faculty);
        parcel.writeString(date);
        parcel.writeInt(id);
        parcel.writeInt(period);
        parcel.writeInt(period_id);
        parcel.writeInt(day);
    }
    TableData(Parcel parcel){
        super();
        periodName = parcel.readString();
        shortName = parcel.readString();
        data_name = parcel.readString();
        data_value = parcel.readString();
        faculty = parcel.readString();
        date = parcel.readString();
        id = parcel.readInt();
        period = parcel.readInt();
        period_id = parcel.readInt();
        day = parcel.readInt();
    }
    public TableData(){
        super();
        periodName = "";
        shortName = "";
        data_name = "";
        data_value = "";
        faculty = "";
        date = "";
        id = -1;
        period = -1;
        period_id = -1;
        day = -1;
    }
}
