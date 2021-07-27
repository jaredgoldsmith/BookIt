package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.ArrayList;
import java.util.Collection;

public class AppointmentBook extends AbstractAppointmentBook<Appointment> {
    protected String owner;
    protected ArrayList<Appointment> appointments = new ArrayList<>();

    public AppointmentBook(){}
    public AppointmentBook(String owner) {
        this.owner = owner;

    }

    @Override
    public String getOwnerName() {
        return this.owner;
    }

    @Override
    public Collection<Appointment> getAppointments() {
        return this.appointments;
    }

    @Override
    public void addAppointment(Appointment appt) {
        this.appointments.add(appt);
    }

    public void addOwner(String owner){
        this.owner = owner;
    }
}