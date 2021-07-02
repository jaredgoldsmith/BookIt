package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;

import java.util.Collection;
public class AppointmentBookTest {

    @Test
    void getOwnerStringNeedsToBeImplemented() {
        AppointmentBook appointmentBook = new AppointmentBook();
        String owner;
        owner = appointmentBook.getOwnerName();
        //assertThrows(UnsupportedOperationException.class, appointment::getBeginTimeString);
    }

    @Test
    void getAppointmentsNeedsToBeImplemented() {
        AppointmentBook appointmentBook = new AppointmentBook();
        Collection<Appointment> appointments = appointmentBook.getAppointments();
    }
}
