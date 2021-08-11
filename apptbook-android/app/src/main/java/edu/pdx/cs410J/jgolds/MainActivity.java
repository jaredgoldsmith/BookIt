package edu.pdx.cs410J.jgolds;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Appointment appt = new Appointment("Teach Java");
                appt.addBeginTime("3/13/2020", "3:13", "pm");
                appt.addEndTime("3/13/2020", "4:13", "pm");
                Snackbar.make(view, appt.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button launchCalculator = findViewById(R.id.launch_calculator);
        Button launchAddAppointment = findViewById(R.id.launch_add_appointment);
        launchAddAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AppointmentActivity.class);
                startActivityForResult(intent, GET_SUM_FROM_CALCULATOR); }
        }
        );
        launchCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivityForResult(intent, GET_SUM_FROM_CALCULATOR);
            }
        });
/*
        this.sums = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        try {
            loadSumsFromFile();
        } catch (IOException e) {
            toast("While reading file: " + e.getMessage());
        }
        ListView listOfSums = findViewById(R.id.sums);
        listOfSums.setAdapter(this.sums);
        listOfSums.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getAdapter().getItem(i);
                String message = "Selected item " + i + ": " + item;
                toast(message);
            }
        });

 */
    }

    private void toast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GET_SUM_FROM_CALCULATOR && data != null) {
            /*double sum = data.getDoubleExtra(CalculatorActivity.EXTRA_SUM, 0.0);
            this.sums.add(sum);
            try {
                writeSumsToFile();
            } catch (IOException e) {
                toast("While writing to file: " + e.getMessage());
            }


             */
            //Appointment appointment = (Appointment) data.getSerializableExtra(CalculatorActivity.EXTRA_APPOINTMENT);
            Appointment appointment = (Appointment) data.getSerializableExtra(AppointmentActivity.EXTRA_APPOINTMENT);
            this.appt = appointment;
            toast("Got appointment: " + appointment);
            try{
                writeAppointmentToFile();
            }
            catch(IOException e){
                toast("While writing to file: " + e.getMessage());
            }
        }
    }

    private void writeAppointmentToFile() throws IOException {
        File appointmentFile = getAppointmentFile();
        try (
                PrintWriter pw = new PrintWriter(new FileWriter(appointmentFile))
        ) {
            /*
            for (int i = 0; i < this.sums.getCount(); i++) {
                Double sum = this.sums.getItem(i);
                pw.println(sum);
            }

             */
            pw.println(this.appt.description);
            pw.flush();
        }
    }

    @NonNull
    private File getAppointmentFile() {
        File contextDirectory = getApplicationContext().getDataDir();
        File sumsFile = new File(contextDirectory, "appointment.txt");
        return sumsFile;
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GET_SUM_FROM_CALCULATOR && data != null) {
            Appointment appointment = (Appointment) data.getSerializableExtra(AppointmentActivity.EXTRA_APPOINTMENT);
            toast("Got appointment: " + appointment);
        }
    }

 */
    private void loadSumsFromFile() throws IOException {
        File sumsFile = getSumsFile();
        if (!sumsFile.exists()) {
            return;
        }

        try (
                BufferedReader br = new BufferedReader(new FileReader(sumsFile))
        ) {
            String line = br.readLine();
            while(line != null) {
                Double sum = Double.parseDouble(line);
                this.sums.add(sum);
                line = br.readLine();
            }
        }
    }

    private void writeSumsToFile() throws IOException {
        File sumsFile = getSumsFile();
        try (
                PrintWriter pw = new PrintWriter(new FileWriter(sumsFile))
        ) {
            for (int i = 0; i < this.sums.getCount(); i++) {
                Double sum = this.sums.getItem(i);
                pw.println(sum);
            }
            pw.flush();
        }
    }

    @NonNull
    private File getSumsFile() {
        File contextDirectory = getApplicationContext().getDataDir();
        File sumsFile = new File(contextDirectory, "sums.txt");
        return sumsFile;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}