package shrotes.tyrdevs.com.shrotes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import shrotes.tyrdevs.com.shrotes.DataVariables.TableData;


public class EditSubjectName extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notes);

        Intent intent = getIntent();
        name = intent.getStringExtra("periodName");
       // x = Integer.parseInt(intent.getStringExtra("day"));
       // y = Integer.parseInt(intent.getStringExtra("period"));
        this.setTitle(name);
        init();

    }

    EditText shortname,fullname,faculty;
    String name;
    DBControl mydb;
    int x,y;

    private void init() {
        shortname = (EditText) findViewById(R.id.editTextshortname);
        fullname = (EditText) findViewById(R.id.editTextfullname);
        faculty = (EditText) findViewById(R.id.editTextfacultyname);
        mydb = new DBControl(EditSubjectName.this);

        Button edit = (Button) findViewById(R.id.buttonedit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableData data = new TableData();
                data.putShortName(shortname.getText().toString());
                data.putData_Name(fullname.getText().toString());
                data.putFaculty(faculty.getText().toString());
                mydb.periodsData(data, name);
              //  mydb.inputTableData(data,"" + x, "" + y);

            }
        });
    }


}
