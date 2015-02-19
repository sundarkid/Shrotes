package shrotes.tyrdevs.com.shrotes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shrotes.tyrdevs.com.shrotes.DataVariables.TableData;


public class RegistrationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        init();

    }

    EditText editTextName, editTextClassGroup, editTextPassword, editTextPasswordReEnter;
    Button reset, register;
    String result;
    DBControl mydb;
    private void init() {
        // Mapping the UI elements
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextClassGroup = (EditText) findViewById(R.id.editTextClassGroup);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextPasswordReEnter = (EditText) findViewById(R.id.editTextPasswordReEnter);
        reset = (Button) findViewById(R.id.buttonRegistrationActivityReset);
        register = (Button) findViewById(R.id.buttonRegisterRegistrationActivity);
        mydb = new DBControl(getApplicationContext());
        // Creating Listeners
        createListeners();
    }

    private void createListeners() {
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPasswordReEnter.setText("");
                editTextPassword.setText("");
                editTextClassGroup.setText("");
                editTextName.setText("");
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextPassword.getText().toString().equals(editTextPasswordReEnter.getText().toString())){
                    if(!editTextName.getText().toString().equals("") && !editTextClassGroup.getText().toString().equals("")) {
                        new LongOperation().execute(null, null, null);
                    }
                 }else
                    Toast.makeText(getApplicationContext(),"Please enter the details correctly",Toast.LENGTH_SHORT).show();
            }});}

    // TODO THis is where internet connection takes place
    class LongOperation  extends AsyncTask<Void, Void, Void> {


        private ProgressDialog Dialog = new ProgressDialog(RegistrationActivity.this);

        @Override
        protected Void doInBackground(Void... voids) {
            HttpClient httpclient = new DefaultHttpClient();
        // TODO link to send the data to
            HttpPost httppost = new HttpPost("http://tmtbl.netai.net/register.php");

            try{
                List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(2);

                // TODO this is where the post data is created with key and value for sending
                namevaluepairs.add(new BasicNameValuePair("user_name", editTextName.getText().toString()));

                namevaluepairs.add(new BasicNameValuePair("pass_word", editTextPassword.getText().toString()));

                namevaluepairs.add(new BasicNameValuePair("classgroup", editTextPassword.getText().toString()));

                UrlEncodedFormEntity formentity;

                formentity = new UrlEncodedFormEntity(namevaluepairs);

                httppost.setEntity(formentity);

                HttpResponse response = httpclient.execute(httppost);
                // TODO the result is got from server and stored in result string
                result = EntityUtils.toString(response.getEntity());

            }catch(ClientProtocolException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Registration Error", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPreExecute() {
            Dialog.setMessage("Registering");
            Dialog.show();
        }



        protected void onPostExecute(Void unused) {
            //TODO Result string json data decode function
            jsonDecode();
            Dialog.dismiss();

            Toast.makeText(getApplicationContext(), "your unique id stored in database\n" + result, Toast.LENGTH_LONG).show();
            Log.d("result",result);
            Intent intent = new Intent(RegistrationActivity.this,LauncherActivity.class);
            startActivity(intent);
        }

    }


    public void jsonDecode(){

        //TODO Result string json data is decoded

        try {
            Log.d("Inside json decode", "");
            /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
            JSONObject jsonResponse = new JSONObject(result);

            /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
            /*******  Returns null otherwise.  *******/
            JSONArray jsonMainNode = jsonResponse.optJSONArray("Shrotes");

            /*********** Process each JSON Node ************/

            int lengthJsonArr = jsonMainNode.length();

            for(int i=0; i < lengthJsonArr; i++)
            {// decoding
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                String user = jsonChildNode.optString("username").toString();
                String password = jsonChildNode.optString("password").toString();
                String classgroup = jsonChildNode.optString("classgroup").toString();
                String uid = jsonChildNode.optString("uniqueid").toString();


                TableData data = new TableData();
                data.putData_Name("user");
                data.putData_Value(user);
                mydb.personalData(data);
                data.putData_Name("password");
                data.putData_Value(password);
                mydb.personalData(data);
                data.putData_Name("classgroup");
                data.putData_Value(classgroup);
                mydb.personalData(data);
                data.putData_Name("uniqueid");
                data.putData_Value(uid);
                mydb.personalData(data);
                result = uid;

            }

        } catch (JSONException e) {

            e.printStackTrace();
        }


    }


}
