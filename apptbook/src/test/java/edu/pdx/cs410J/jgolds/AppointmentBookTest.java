package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AppointmentBookTest {

    @Test
    void getOwnerStringNeedsToBeImplemented() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13");
        appointment.addEndTime("7/17/2020", "4:13");
        appointmentBook.addAppointment(appointment);
        String owner = "Buck";
        assertThat(appointmentBook.getOwnerName(), equalTo(owner));

        //assertThrows(UnsupportedOperationException.class, appointment::getBeginTimeString);
    }

    @Test
    void getAppointmentsNeedsToBeImplemented() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13");
        appointment.addEndTime("7/17/2020", "4:13");
        appointmentBook.addAppointment(appointment);
        assertThat(appointmentBook.getAppointments(), equalTo(appointmentBook.appointments));
    }

    private AppointmentBook getApp() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13");
        appointment.addEndTime("7/17/2020", "4:13");
        appointmentBook.addAppointment(appointment);
        return appointmentBook;
    }

    @Test
    void testToString() {
        AppointmentBook appBook = getApp();
        assertThat(appBook.toString(), equalTo("Buck's appointment book with 1 appointments"));
    }

}
