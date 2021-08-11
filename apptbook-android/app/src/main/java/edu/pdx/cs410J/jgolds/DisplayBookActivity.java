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

import edu.pdx.cs410J.ParserException;

import java.text.SimpleDateFormat;
import java.util.*;

public class DisplayBookActivity extends AppCompatActivity {
    private Appointment appt;
    private AppointmentBook apptBook;
    private String bookOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_book);

        Button submitForDisplay = findViewById(R.id.submitForDisplay);
        submitForDisplay.setOnClickListener(view -> findAndPrintFile());
    }

    public void findAndPrintFile(){
        TextView prettyprint = findViewById(R.id.prettyPrint);
        prettyprint.setText("");
        try {
            String prettyBook = prettyAppointment();
            if(prettyBook == null || prettyBook.equals("")){
                displayErrorMessage("Nothing to display so far");
            }
        }
        catch(ParserException e){
            displayErrorMessage("Nothing to display so far");
        }
        EditText owner = findViewById(R.id.ownerForDisplay);
        owner.getText().clear();
    }

    public String prettyAppointment()throws ParserException {
        String prettyBook = "";
        EditText owner = findViewById(R.id.ownerForDisplay);

        String fileName = owner.getText().toString();
        String ownerString = fileName;
        if(fileName.equals("")){
            displayErrorMessage("Owner field needs to be filled in");
            return null;
        }
        fileName += ".txt";
        File ownerFile = getAppointmentFile(fileName);
        if(!ownerFile.exists()){
            displayErrorMessage("The owner entered does not have an appointment book with us");
            return null;
        }
        TextParser parser = new TextParser(ownerFile);
        this.apptBook = new AppointmentBook(ownerString);
        try {
            this.apptBook = parser.parse();
        }
        catch(ParserException e){
           displayErrorMessage("Couldn't parse this file");
           return null;
        }
        ArrayList<Appointment> appointments = new ArrayList<>();
        appointments = parser.sortAppointments(this.apptBook.appointments);
        this.apptBook.appointments = appointments;
        String prettyBegin;
        String prettyEnd;
        long minuteDifference;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mma, MM/dd/yyyy");
        prettyBook += "\n\n\n\n\n" + apptBook.owner + "'s Appointment Book\n";
        Appointment appt;
        for(int i = 0; i < apptBook.appointments.size(); ++i){
            appt = apptBook.appointments.get(i);
            prettyBook += "Appointment #" + (i+1) + ": " + appt.description + "\n";
            prettyBegin = sdf.format(apptBook.appointments.get(i).getBeginTime());
            prettyEnd = sdf.format(apptBook.appointments.get(i).getEndTime());
            minuteDifference = (apptBook.appointments.get(i).getEndTime().getTime() - apptBook.appointments.get(i).getBeginTime().getTime())/1000/60;
            prettyBook += "Appointment starts at " + prettyBegin + " and ends at " + prettyEnd + ", ";
            prettyBook += "for a total of " + minuteDifference + " minutes\n\n";
        }
        if(apptBook.appointments.size() == 0){
            displayErrorMessage("There aren't any appointments to display for " + ownerString);
            return null;
        }
        TextView prettyprint = findViewById(R.id.prettyPrint);
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