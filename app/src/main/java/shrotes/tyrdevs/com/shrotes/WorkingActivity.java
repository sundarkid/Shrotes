package shrotes.tyrdevs.com.shrotes;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

import shrotes.tyrdevs.com.shrotes.DataVariables.CellValues;
import shrotes.tyrdevs.com.shrotes.DataVariables.TableData;


public class WorkingActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_working);
        this.setTitle("Time table");
        context = getApplicationContext();



    }

    @Override
    protected void onResume() {
        init();
        super.onResume();
    }

    Context context;
    TableLayout table;
    static Boolean editable = Boolean.FALSE;
    String[] days = {"Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
    DBControl myDB;
    ProgressDialog dialog;
    // Initializing the variables
    private void init() {
        myDB = new DBControl(context);
        context = getApplicationContext();
        table = (TableLayout) findViewById(R.id.table_time_table);

        dialog = new ProgressDialog(WorkingActivity.this);
        ArrayList<TableData> dataArrayList = new ArrayList<TableData>();
        dataArrayList = myDB.getPersonalDetails();
        int noOfPeriods = -1;
        int[] daysList = new int[]{};
        int x = dataArrayList.size();
        for(int i =0 ; i < x; i++)
                if(dataArrayList.get(i).getData_Name().equals("periods") && dataArrayList.get(i).getPeriod() > 0){
                    noOfPeriods = dataArrayList.get(i).getPeriod();
                }else if(dataArrayList.get(i).getData_Name().equals("days") && dataArrayList.get(i).getDay() > 0){
                    daysList = new int[Integer.toString(dataArrayList.get(i).getDay()).length()];
                    for (int j = 0 ; j < Integer.toString(dataArrayList.get(i).getDay()).length() ; j++){
                        daysList[j] = Integer.toString(dataArrayList.get(i).getDay()).charAt(j) - '0';
                    }

                }
        if(noOfPeriods > 0 && days.length > 0)
            createTable(daysList, noOfPeriods);
    }

    private void createTable(int[] days, int periods) {
        table.removeAllViews();
        TableRow[] row = new TableRow[days.length];
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 0.3f);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 0.3f);
        ArrayList<CellValues> matrix = myDB.getTabledata();
        if(matrix.isEmpty() && matrix.size() < 0)
        for (int i = 0 ; i < days.length ; i++){
            row[i] = new TableRow(context);
            row[i].setLayoutParams(tableParams);
            Log.d("row","" + i);
            final TextView textViewDay = new TextView(context);
            textViewDay.setTextSize(20);
            textViewDay.setText(this.days[days[i] - 1]);
            textViewDay.setLayoutParams(params);
            textViewDay.setTextColor(Color.BLACK);
            textViewDay.setBackgroundResource(R.drawable.cell_shape_day);
            row[i].addView(textViewDay);
            for (int j = 0 ; j < periods ; j++){
                final TextView textView = new TextView(context);
                Log.d("view", "" + j);
                textView.setClickable(Boolean.TRUE);
                textView.setBackgroundResource(R.drawable.cell_shape_period);
                textView.setTextColor(Color.BLACK);
                textView.setTextSize(20);
                textView.setPadding(5, 5, 5, 5);
                textView.setLayoutParams(params);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!editable) {
                            Intent intent = new Intent(getApplicationContext(), EditSubjectName.class);
                            intent.putExtra("periodName", textView.getText().toString());
                            startActivity(intent);

                        }
                    }
                });
                row[i].addView(textView);
            }
            table.addView(row[i]);
        }
        else
        {
            dialog.setTitle("No data");
            dialog.show();

            for (int i = 0 ; i < days.length ; i++){
                row[i] = new TableRow(context);
                row[i].setLayoutParams(tableParams);
                Log.d("row","" + i);
                final TextView textViewDay = new TextView(context);
                textViewDay.setTextSize(20);
                textViewDay.setText(this.days[days[i] - 1]);
                textViewDay.setLayoutParams(params);
                textViewDay.setTextColor(Color.BLACK);
                textViewDay.setBackgroundResource(R.drawable.cell_shape_day);
                row[i].addView(textViewDay);
                for (int j = 0 ; j < periods ; j++){
                    final TextView textView = new TextView(context);
                    Log.d("view", "" + j);
                    textView.setClickable(Boolean.TRUE);
                    textView.setBackgroundResource(R.drawable.cell_shape_period);
                    textView.setTextColor(Color.BLACK);
                    textView.setTextSize(20);
                    textView.setPadding(5, 5, 5, 5);
                    textView.setLayoutParams(params);
                    final int x,y;
                    x = i;
                    y = j;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!editable) {
                                Intent intent = new Intent(getApplicationContext(), EditSubjectName.class);
                                intent.putExtra("periodName", textView.getText().toString());
                                intent.putExtra("day", "" + x);
                                intent.putExtra("period", "" + y);
                                startActivity(intent);

                            }
                        }
                    });
                    row[i].addView(textView);
                }
                table.addView(row[i]);
            }

        }

    }


    @Override
    public void onBackPressed() {
        dialog.dismiss();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_working, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_upload:
                upload();
                break;
            case R.id.menu_edit_time_table:
                startActivity(new Intent(context,EditTimeTable.class));
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void upload() {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                // TODO send to server
                String data = "";
                ArrayList<CellValues> values = new ArrayList<CellValues>();
                for(int i = 0 ; i < values.size() ; i++){
                    data = data + values.get(i).myToString();
                }


                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(null,null,null);

    }
}
