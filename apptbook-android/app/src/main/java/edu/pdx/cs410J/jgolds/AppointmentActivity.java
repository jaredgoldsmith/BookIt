package edu.pdx.cs410J.jgolds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class AppointmentActivity extends AppCompatActivity {

    public static final String EXTRA_APPOINTMENT = "Appointment";
    private Appointment appt;
    private AppointmentBook apptBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Button submitAppointment = findViewById(R.id.submitAppointment);
        submitAppointment.setOnClickListener(view -> sendAppointmentBack());
    }

    protected void sendAppointmentBack(){
        Appointment appointment = getAppointment();
        Intent intent = new Intent();
        EditText owner = findViewById(R.id.owner);
        String bookOwner = owner.getText().toString();
        intent.putExtra(EXTRA_APPOINTMENT, appointment);
        setResult(RESULT_OK, intent);
        finish();
    }
    protected Appointment getAppointment(){
        Appointment appointment = new Appointment();

        EditText description = findViewById(R.id.description);
        appointment.addDescription(description.getText().toString());
        EditText startDate = findViewById(R.id.startDate);
        EditText startTime = findViewById(R.id.startTime);
        EditText startAmPm = findViewById(R.id.startAmPm);
        appointment.addBeginTime(startDate.getText().toString(),startTime.getText().toString(),startAmPm.getText().toString());
        EditText endDate = findViewById(R.id.endDate);
        EditText endTime = findViewById(R.id.endTime);
        EditText endAmPm = findViewById(R.id.endAmPm);
        appointment.addEndTime(endDate.getText().toString(),endTime.getText().toString(),endAmPm.getText().toString());
        this.appt = appointment;
        //File contextDirectory = getApplicationContext().getDataDir();
        EditText owner = findViewById(R.id.owner);
        String bookOwner = owner.getText().toString();
        String fileName = bookOwner + ".txt";
        this.apptBook = new AppointmentBook(bookOwner);
        apptBook.addAppointment(this.appt);
        try {
            writeSumsToFile(fileName);
        }
        catch(IOException e){
            displayErrorMessage("Cannot open file");
        }
        //File file = new File(contextDirectory, bookOwner + ".txt");

        return appointment;
    }
    private void writeSumsToFile(String fileName) throws IOException {
        File sumsFile = getSumsFile(fileName);

        TextDumper dumper = new TextDumper(sumsFile);
        dumper.dump(this.apptBook);
        /*
        try (
                //PrintWriter pw = new PrintWriter(new FileWriter(sumsFile))
        //        TextDumper dumper = new TextDumper(fileName);
        ) {
            //for (int i = 0; i < this.sums.getCount(); i++) {
            //    Double sum = this.sums.getItem(i);
            //    pw.println(sum);
            //}
            //pw.println(this.appt.description);
            //pw.flush();
        }

         */
    }
    @NonNull
    private File getSumsFile(String fileName) {
        File contextDirectory = getApplicationContext().getDataDir();
        //File sumsFile =
        return new File(contextDirectory, fileName);
        //return sumsFile;
    }

    private void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}