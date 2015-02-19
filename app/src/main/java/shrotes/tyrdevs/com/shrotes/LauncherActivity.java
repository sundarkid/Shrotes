package shrotes.tyrdevs.com.shrotes;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class LauncherActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        init();

    }

    Button register,login,goOffline;
    NotificationManager mgr;
    NotificationCompat.Builder myBuilder;
    int notificationId;

    private void init() {
        // Mapping and Listeners
        register = (Button) findViewById(R.id.buttonSignUpActivity);
        login = (Button) findViewById(R.id.buttonLoginActivity);
        goOffline = (Button) findViewById(R.id.buttonGoOffline);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });
        goOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),WorkingActivity.class);
                startActivity(intent);
            }
        });

        // Notification matter

        // Creating a notification manager
        mgr = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        // This pending intent will open after notification click
        PendingIntent i=PendingIntent.getActivity(this, 0,
                new Intent(this, WorkingActivity.class),
                0);

        myBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("First Notification").setContentText("First Message").setSmallIcon(R.drawable.notes_icon);

        notificationId = 0001;

        mgr.notify(notificationId,myBuilder.build());

        // End Notification matter

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_launcher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_upload:
                mgr.notify(notificationId,myBuilder.build());
                Log.d("Message", "notification");
                break;
            case R.id.menu_edit_time_table:
                Intent intent = new Intent(getApplicationContext(), QrCode.class);
                startActivity(intent);
                break;
            case R.id.menu_scanner:
                Intent intent1 = new Intent(getApplicationContext(), QrScanner.class);
                startActivity(intent1);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}

