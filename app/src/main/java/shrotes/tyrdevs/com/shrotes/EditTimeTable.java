package shrotes.tyrdevs.com.shrotes;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import shrotes.tyrdevs.com.shrotes.DataVariables.TableData;


public class EditTimeTable extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_time_table);
        this.setTitle("");
        // getting context first as I don't need the later context of the activity
        context = getApplicationContext();
        init();

    }

    CheckBox[] checkBoxDays;
    Button buttonNoOfPeriods, buttonDuration;
    LinearLayout linearLayoutPeriodNameGet;
    EditText editTextNoOfPeriod, editTextPeriodDuration;
    int noOfPeriods;
    Context context;
    DBControl myDB;

    private void init() {
        checkBoxDays = new CheckBox[6];
        // Initializing Checkboxes
        mapCheckBox();
        myDB = new DBControl(context);
        // Initializing buttons
        buttonNoOfPeriods = (Button) findViewById(R.id.buttonNoOfPeriod);
        buttonDuration = (Button) findViewById(R.id.buttonDurationOfPeriod);
        linearLayoutPeriodNameGet = (LinearLayout) findViewById(R.id.linearLayoutAddPeriodNames);
        editTextNoOfPeriod = (EditText) findViewById(R.id.editTextNoOfPeriods);
        editTextPeriodDuration = (EditText) findViewById(R.id.editTextDurationOfPeriod);
        // Setting Click listeners
        setClickListeners();
    }

    private void setClickListeners() {
        // Setting Click listeners
        buttonDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Do something
            }
        });
        buttonNoOfPeriods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!editTextNoOfPeriod.getText().toString().equals("") || Integer.parseInt(editTextNoOfPeriod.getText().toString()) > 0){
                    noOfPeriods = Integer.parseInt(editTextNoOfPeriod.getText().toString());
                    LinearLayout layout;
                    final EditText[] periodName, periodNameShort;
                    periodName = new EditText[noOfPeriods];
                    periodNameShort = new EditText[noOfPeriods];
                    linearLayoutPeriodNameGet.removeAllViews();
                    for(int i = 0; i < noOfPeriods ; i++){
                        // creating the edit boxes for each period
                        layout = new LinearLayout(context);
                        layout.setOrientation(LinearLayout.HORIZONTAL);
                        periodName[i] = new EditText(context);
                        periodName[i].setTextColor(Color.BLACK);
                        periodName[i].setHint("Name");
                        periodNameShort[i] = new EditText(context);
                        periodNameShort[i].setHint("short");
                        periodNameShort[i].setTextColor(Color.BLACK);
                        layout.addView(periodName[i]);
                        layout.addView(periodNameShort[i]);
                        linearLayoutPeriodNameGet.addView(layout);
                    }
                    final Button save = new Button(context);
                    save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                         //   save.setBackgroundColor(Integer.parseInt("0182B3", 16));
                            // Save the names in database
                            TableData[] data = new TableData[noOfPeriods];
                            for (int i = 0 ; i < noOfPeriods ;i++) {
                                data[i] = new TableData();
                                data[i].putPeriodName(periodName[i].getText().toString());
                                data[i].putShortName(periodNameShort[i].getText().toString());
                            }
                            // use names to push into database

                            myDB.inputValueEditPeriod(data);
                            TableData personalData = new TableData();
                            for(int i = 0 ; i < 6 ; i++){
                                if (checkBoxDays[i].isChecked())
                                    personalData.putData_Value(personalData.getData_value()+ "" + (i + 1));
                            }
                           // Inserting the days and no of periods data int DB
                            personalData.putData_Name("days");
                            myDB.personalData(personalData);
                            personalData.putData_Name("periods");
                            personalData.putData_Value("" + noOfPeriods);
                            myDB.personalData(personalData);
                            Intent intent = new Intent(getApplicationContext(), WorkingActivity.class);
                            startActivity(intent);
                        }
                    });
                    save.setText("Save");
                    save.setTextColor(Color.BLACK);
                    linearLayoutPeriodNameGet.addView(save);
                }

            }
        });
    }

    @Override
    protected void onPause() {
        finish();
        super.onPause();
    }

    private void mapCheckBox() {
        // Mapping the check boxes to the UI
        checkBoxDays[0] = (CheckBox) findViewById(R.id.checkBoxMonday);
        checkBoxDays[1] = (CheckBox) findViewById(R.id.checkBoxTuesday);
        checkBoxDays[2] = (CheckBox) findViewById(R.id.checkBoxWednesday);
        checkBoxDays[3] = (CheckBox) findViewById(R.id.checkBoxThursday);
        checkBoxDays[4] = (CheckBox) findViewById(R.id.checkBoxFriday);
        checkBoxDays[5] = (CheckBox) findViewById(R.id.checkBoxSaturday);
    }


}
