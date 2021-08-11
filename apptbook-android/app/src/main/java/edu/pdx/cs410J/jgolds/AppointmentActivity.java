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
import edu.pdx.cs410J.ParserException;

public class AppointmentActivity extends AppCompatActivity {

    public static final String EXTRA_APPOINTMENT = "Appointment";
    private Appointment appt;
    private AppointmentBook apptBook;
    private String bookOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Button submitAppointment = findViewById(R.id.submitAppointment);
        submitAppointment.setOnClickListener(view -> sendAppointmentBack());
    }

    protected void sendAppointmentBack(){
        Appointment appointment = getAppointment();
        /*Intent intent = new Intent();
        EditText owner = findViewById(R.id.owner);
        this.bookOwner = owner.getText().toString();
        intent.putExtra(EXTRA_APPOINTMENT, appointment);
        setResult(RESULT_OK, intent);
        finish();

         */
        if(appointment != null){
            toast("Got appointment: " + appointment);
        }
        else{
            toast("No appointment was added");
        }

        EditText owner = findViewById(R.id.owner);
        owner.getText().clear();
        EditText description = findViewById(R.id.description);
        description.getText().clear();
        EditText startDate = findViewById(R.id.startDate);
        startDate.getText().clear();
        EditText startTime = findViewById(R.id.startTime);
        startTime.getText().clear();
        EditText startAmPm = findViewById(R.id.startAmPm);
        startAmPm.getText().clear();
        EditText endDate = findViewById(R.id.endDate);
        endDate.getText().clear();
        EditText endTime = findViewById(R.id.endTime);
        endTime.getText().clear();
        EditText endAmPm = findViewById(R.id.endAmPm);
        endAmPm.getText().clear();

    }
    private void toast(String message) {
        Toast.makeText(AppointmentActivity.this, message, Toast.LENGTH_LONG).show();
    }

    protected Appointment getAppointment(){
        Appointment appointment = new Appointment();
        DateTimeHelper helper = new DateTimeHelper();

        EditText description = findViewById(R.id.description);
        String descriptionString = description.getText().toString();
        if(descriptionString == null || descriptionString.equals("")){
            displayErrorMessage("Description is not filled in");
            return null;
        }
        appointment.addDescription(description.getText().toString());
        EditText startDate = findViewById(R.id.startDate);
        String startDateString = startDate.getText().toString();
        if(!helper.parseDates(startDateString)) {
            displayErrorMessage("Incorrect date format");
            return null;
        }
        EditText startTime = findViewById(R.id.startTime);
        String startTimeString = startTime.getText().toString();
        if(!helper.parseTimes(startTimeString)){
            displayErrorMessage("Incorrect time format");
            return null;
        }
        EditText startAmPm = findViewById(R.id.startAmPm);
        String startAmPmString = startAmPm.getText().toString();
        if(!(startAmPmString.equals("am") || startAmPmString.equals("pm"))){
            displayErrorMessage("Needs to be 'am' or 'pm' following the time");
            return null;
        }
        appointment.addBeginTime(startDate.getText().toString(),startTime.getText().toString(),startAmPm.getText().toString());
        EditText endDate = findViewById(R.id.endDate);
        String endDateString = endDate.getText().toString();
        if(!helper.parseDates(endDateString)){
            displayErrorMessage("Incorrect date format");
            return  null;
        }
        EditText endTime = findViewById(R.id.endTime);
        String endTimeString = endTime.getText().toString();
        if(!helper.parseTimes(endTimeString)){
            displayErrorMessage("Incorrect time format");
            return null;
        }
        EditText endAmPm = findViewById(R.id.endAmPm);
        String endAmPmString = endAmPm.getText().toString();
        if(!(endAmPmString.equals("am") || endAmPmString.equals("pm"))){
            displayErrorMessage("Needs to be 'am' or 'pm' following the time");
            return null;
        }
        appointment.addEndTime(endDate.getText().toString(),endTime.getText().toString(),endAmPm.getText().toString());
        Long difference = appointment.endOfAppointment.getTime() - appointment.startOfAppointment.getTime();
        if(difference < 0){
            displayErrorMessage("The end of the appointment cannot be before the beginning of the appointment");
            return null;
        }
        this.appt = appointment;

        //File contextDirectory = getApplicationContext().getDataDir();
        EditText owner = findViewById(R.id.owner);
        String ownerString = owner.getText().toString();
        if(ownerString == null || ownerString.equals("")){
            displayErrorMessage("Owner was not entered");
            return null;
        }
        this.bookOwner = owner.getText().toString();
        String fileName = this.bookOwner + ".txt";
        //this.apptBook = new AppointmentBook(bookOwner);
        //apptBook.addAppointment(this.appt);
        try {
            writeAppointmentToFile(fileName);
        }
        catch(IOException e){
            displayErrorMessage("Cannot open file");
        }
        //File file = new File(contextDirectory, bookOwner + ".txt");

        return appointment;
    }
    private void writeAppointmentToFile(String fileName) throws IOException {
        File file = getAppointmentFile(fileName);

        if(!file.exists()) {
            TextDumper dumper = new TextDumper(file);
            this.apptBook = new AppointmentBook(this.bookOwner);
            this.apptBook.addAppointment(this.appt);
            dumper.dump(this.apptBook);
        }
        else{
            TextParser parser = new TextParser(file);
            try {
                this.apptBook = new AppointmentBook();
                this.apptBook = parser.parse();
            }
            catch(ParserException e){
               displayErrorMessage("Couldn't parse the owner's file");
            }
            this.apptBook.addAppointment(this.appt);
            TextDumper dumper = new TextDumper(file);
            dumper.dump(this.apptBook);
        }
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
    private File getAppointmentFile(String fileName) {
        File contextDirectory = getApplicationContext().getDataDir();
        //File sumsFile =
        return new File(contextDirectory, fileName);
        //return sumsFile;
    }

    private void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}