package edu.pdx.cs410J.jgolds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentActivity extends AppCompatActivity {

    public static final String EXTRA_APPOINTMENT = "Appointment";
    private Appointment appt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        Button submitAppointment = findViewById(R.id.submitAppointment);
        submitAppointment.setOnClickListener(view -> sendAppointmentBack());
    }

    protected void sendAppointmentBack(){
        this.appt = getAppointment();
        Intent intent = new Intent();
        EditText owner = findViewById(R.id.owner);
        String bookOwner = owner.getText().toString();
        intent.putExtra(EXTRA_APPOINTMENT, this.appt);
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
        appointment.addBeginTime(endDate.getText().toString(),endTime.getText().toString(),endAmPm.getText().toString());
        return appointment;
    }
}