package edu.pdx.cs410J.jgolds;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import edu.pdx.cs410J.jgolds.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    //private AppBarConfiguration appBarConfiguration;
    public static final int GET_SUM_FROM_CALCULATOR = 42;
    private ActivityMainBinding binding;
    private ArrayAdapter<Double> sums;
    private Appointment appt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        Button launchAddAppointment = findViewById(R.id.launch_add_appointment);
        launchAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AppointmentActivity.class);
                startActivityForResult(intent, GET_SUM_FROM_CALCULATOR); }
        }
        );

        Button displayBook = findViewById(R.id.launch_display);
        displayBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DisplayBookActivity.class);
                startActivity(intent); }
        }
);
        Button searchAppointments = findViewById(R.id.launch_search_appointments);
        searchAppointments.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Intent intent = new Intent(MainActivity.this, SearchAppointmentsActivity.class);
                   startActivity(intent); }
           }
        );
    }

    private void toast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        toast("Greetings!");

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent launchNewIntent = new Intent(MainActivity.this,READMEActivity.class);
            startActivity(launchNewIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}