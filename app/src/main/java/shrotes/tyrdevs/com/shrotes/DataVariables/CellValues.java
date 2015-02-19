package shrotes.tyrdevs.com.shrotes.DataVariables;

/**
 * Created by Sundareswaran on 30-01-2015.
 */
public class CellValues {
    String name;
    String shortName;
    int day;
    int period;

    public void putName(String name){
        this.name = name;
    }
    public void putShortName(String shortName){
        this.shortName = shortName;
    }

    public String getName(){
        return name;
    }
    public String getShortName(){
        return shortName;
    }

    public int getDay(){return day;}
    public int getPeriod(){return period;}

    public void putDay(int day){
        this.day = day;
    }
    public void putPeriod(int period){
        this.period = period;
    }
    public String myToString(){
        String str = "--!-+" + name + "--!-+" + day + period;
        return str;
    }
}
