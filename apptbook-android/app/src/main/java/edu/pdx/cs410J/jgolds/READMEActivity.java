package edu.pdx.cs410J.jgolds;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class READMEActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_readmeactivity);

        try {
            displayReadMe();
        }
        catch(IOException e){
            displayErrorMessage("IOException thrown");
        }
    }

    private void displayReadMe() throws IOException {
            String readmePage = "";
            readmePage += "Welcome to the Appointment Book App! This app is a a simple " +
                    "appointment book organizer. It can be used by just one person or by " +
                    "multiple people, whether it be a family or in a corporate setting. You " +
                    "are given three options in this app. The first option is to add an " +
                    "appointment. This will open a new appointment book if the owner of the " +
                    "appointment does not have an appointment book on file, otherwise it will " +
                    "add the appointment to the existing book. The other option is to print " +
                    "out the contents of the entire appointment book. You will just need to " +
                    "enter the name of the owner of the appointment book. The last option is " +
                    "to search for appointments based on a range of times. You will need to " +
                    "enter the owner of the appointment book, and the start and end time of " +
                    "the appointment range you want.\n\n" +
                    "Jared Goldsmith jgolds@pdx.edu\n" +
                    "CS510J: Advanced Programming with Java\n" +
                    "Portland State, Summer 2021";
        TextView prettyprint = findViewById(R.id.ReadmeDisplay);
        prettyprint.setText(readmePage);
    }
    private void displayErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}