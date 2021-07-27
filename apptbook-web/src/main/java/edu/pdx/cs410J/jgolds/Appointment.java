package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {
    private final String description;

    public Appointment(String description) {
        this.description = description;
    }

    @Override
    public String getBeginTimeString() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public String getEndTimeString() {
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    @Override
    public String getDescription() {
        return description;
    }
}