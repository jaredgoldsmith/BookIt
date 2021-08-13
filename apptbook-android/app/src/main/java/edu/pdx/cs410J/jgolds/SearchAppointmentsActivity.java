package edu.pdx.cs410J.jgolds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.pdx.cs410J.ParserException;

public class SearchAppointmentsActivity extends AppCompatActivity {
    public static final String ALL_FIELDS_NEED_TO_BE_ENTERED = "All fields need to be entered";
    private AppointmentBook apptBook;
    private String searchStartTime;
    private String searchEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointments);
        Button submitForDisplay = findViewById(R.id.searchSubmitAppointment);
        submitForDisplay.setOnClickListener(view -> findAndPrintFile());
    }

    public void findAndPrintFile() {
        TextView prettyprint = findViewById(R.id.searchPrettyPrint);
        prettyprint.setText("");
        try {
            String prettyBook = prettyAppointment();
            if (prettyBook == null || prettyBook.equals("")) {
                displayErrorMessage("Nothing to display so far");
            }
        } catch (ParserException e) {
            displayErrorMessage("Nothing to display so far");
        }
        EditText owner = findViewById(R.id.searchOwner);
        owner.getText().clear();
        EditText startDate = findViewById(R.id.searchStartDate);
        startDate.getText().clear();
        EditText startTime = findViewById(R.id.searchStartTime);
        startTime.getText().clear();
        EditText startAmPm = findViewById(R.id.searchStartAmPm);
        startAmPm.getText().clear();
        EditText endDate = findViewById(R.id.searchEndDate);
        endDate.getText().clear();
        EditText endTime = findViewById(R.id.searchEndTime);
        endTime.getText().clear();
        EditText endAmPm = findViewById(R.id.searchEndAmPm);
        endAmPm.getText().clear();
    }

    public void getSearchDates(){
        DateTimeHelper helper = new DateTimeHelper();

        EditText startDate = findViewById(R.id.searchStartDate);
        String startDateString = startDate.getText().toString();
        if(startDateString == null || startDateString.equals("")){
            displayErrorMessage(ALL_FIELDS_NEED_TO_BE_ENTERED);
            return;
        }
        if(!helper.parseDates(startDateString)) {
            displayErrorMessage("Incorrect date format");
            return;
        }
        EditText startTime = findViewById(R.id.searchStartTime);
        String startTimeString = startTime.getText().toString();
        if(startTimeString == null || startTimeString.equals("")){
            displayErrorMessage(ALL_FIELDS_NEED_TO_BE_ENTERED);
            return;
        }
        if(!helper.parseTimes(startTimeString)){
            displayErrorMessage("Incorrect time format");
            return;
        }
        EditText startAmPm = findViewById(R.id.searchStartAmPm);
        String startAmPmString = startAmPm.getText().toString();
        if(startAmPmString == null || startAmPmString.equals("")){
            displayErrorMessage(ALL_FIELDS_NEED_TO_BE_ENTERED);
            return;
        }
        if(!(startAmPmString.equals("am") || startAmPmString.equals("pm"))){
            displayErrorMessage("Needs to be 'am' or 'pm' following the time");
            return;
        }
        this.searchStartTime = startDateString + " " + startTimeString + " " + startAmPmString;
        EditText endDate = findViewById(R.id.searchEndDate);
        String endDateString = endDate.getText().toString();
        if(endDateString == null || endDateString.equals("")){
            displayErrorMessage(ALL_FIELDS_NEED_TO_BE_ENTERED);
            return;
        }
        if(!helper.parseDates(endDateString)){
            displayErrorMessage("Incorrect date format");
            return;
        }
        EditText endTime = findViewById(R.id.searchEndTime);
        String endTimeString = endTime.getText().toString();
        if(endTimeString == null || endTimeString.equals("")){
            displayErrorMessage(ALL_FIELDS_NEED_TO_BE_ENTERED);
            return;
        }
        if(!helper.parseTimes(endTimeString)){
            displayErrorMessage("Incorrect time format");
            return;
        }
        EditText endAmPm = findViewById(R.id.searchEndAmPm);
        String endAmPmString = endAmPm.getText().toString();
        if(endAmPmString == null || endAmPmString.equals("")){
            displayErrorMessage(ALL_FIELDS_NEED_TO_BE_ENTERED);
            return;
        }
        if(!(endAmPmString.equals("am") || endAmPmString.equals("pm"))){
            displayErrorMessage("Needs to be 'am' or 'pm' following the time");
            return;
        }
        this.searchEndTime = endDateString + " " + endTimeString + " " + endAmPmString;
        //File contextDirectory = getApplicationContext().getDataDir();
        //this.apptBook = new AppointmentBook(bookOwner);
        //apptBook.addAppointment(this.appt);
        //File file = new File(contextDirectory, bookOwner + ".txt");

    }
    public String prettyAppointment() throws ParserException {
        String prettyBook = "";
        EditText owner = findViewById(R.id.searchOwner);

        String fileName = owner.getText().toString();
        String ownerString = fileName;
        if (fileName.equals("")) {
            displayErrorMessage("Owner field needs to be filled in. \nMake sure to enter all the fields within the gray boxes");
            return null;
        }
        fileName += ".txt";
        File ownerFile = getAppointmentFile(fileName);
        if (!ownerFile.exists()) {
            displayErrorMessage("The owner entered does not have an appointment book with us");
            return null;
        }
        getSearchDates();
        TextParser parser = new TextParser(ownerFile);
        this.apptBook = new AppointmentBook(ownerString);
        try {
            this.apptBook = parser.parse();
        } catch (ParserException e) {
            displayErrorMessage("Couldn't parse this file");
            return null;
        }
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = parser.sortAppointments(this.apptBook.appointments);
        this.apptBook.appointments = appointments;
        if(this.searchStartTime == null)
            return null;
        if(this.searchEndTime == null)
            return null;
        appointments = parser.sortAppointmentsByDate(this.apptBook.appointments,this.searchStartTime,this.searchEndTime);
        this.apptBook.appointments = appointments;
        String prettyBegin;
        String prettyEnd;
        long minuteDifference;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mma, MM/dd/yyyy");
        prettyBook += "\n\n\n\n\n" + apptBook.owner + "'s Appointment Book\n";
        Appointment appt;
        for (int i = 0; i < apptBook.appointments.size(); ++i) {
            appt = apptBook.appointments.get(i);
            prettyBook += "Appointment #" + (i + 1) + ": " + appt.description + "\n";
            prettyBegin = sdf.format(apptBook.appointments.get(i).getBeginTime());
            prettyEnd = sdf.format(apptBook.appointments.get(i).getEndTime());
            minuteDifference = (apptBook.appointments.get(i).getEndTime().getTime() - apptBook.appointments.get(i).getBeginTime().getTime()) / 1000 / 60;
            prettyBook += "Appointment starts at " + prettyBegin + " and ends at " + prettyEnd + ", ";
            prettyBook += "for a total of " + minuteDifference + " minutes\n\n";
        }
        if (apptBook.appointments.size() == 0) {
            displayErrorMessage("There aren't any appointments in this timeframe for " + ownerString);
            return null;
        }
        TextView prettyprint = findViewById(R.id.searchPrettyPrint);
        prettyprint.setText(prettyBook);
        prettyprint.setMovementMethod(new ScrollingMovementMethod());

        return prettyBook;
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
