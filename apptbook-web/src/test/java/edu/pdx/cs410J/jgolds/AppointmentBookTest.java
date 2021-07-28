package edu.pdx.cs410J.jgolds;
import edu.pdx.cs410J.ParserException;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.PrintWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.util.*;

public class AppointmentBookTest {

    @Test
    void appointmentBookCanBeDumpedAndParsed() throws IOException, ParserException {
        AppointmentBook book1 = new AppointmentBook("Owner");
        String description = "Description";
        Appointment appt = new Appointment(description);
        appt.addBeginTime("02/24/1982","3:33", "pm");
        appt.addEndTime("02/24/1982","4:33", "pm");

        book1.addAppointment(appt);

        appt.setStartOfAppointment(appt.beginTime);
        System.out.println(appt.startOfAppointment.getTime());
        appt.setEndOfAppointment(appt.endTime);
        System.out.println(appt.endOfAppointment.getTime());
        System.out.println(appt.toString());
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book1);

        StringReader sr = new StringReader(sw.toString());
        TextParser parser = new TextParser(sr);

        AppointmentBook book2 = parser.parse();
        assertThat(book2.getOwnerName(), equalTo(book1.getOwnerName()));

        Appointment appointment = book2.getAppointments().iterator().next();
        assertThat(appointment.getDescription(), equalTo(description));

    }
    @Test
    void getOwnerStringNeedsToBeImplemented() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13", "am");
        appointment.addEndTime("7/17/2020", "4:13", "am");
        appointmentBook.addAppointment(appointment);
        String owner = "Buck";
        assertThat(appointmentBook.getOwnerName(), IsEqual.equalTo(owner));
    }

    @Test
    void getAppointmentsNeedsToBeImplemented() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13", "am");
        appointment.addEndTime("7/17/2020", "4:13", "am");
        appointmentBook.addAppointment(appointment);
        assertThat(appointmentBook.getAppointments(), IsEqual.equalTo(appointmentBook.appointments));
    }

    @Test
    void addAppointmentNeedsToBeImplemented(){
        AppointmentBook appointmentBook = new AppointmentBook();
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13", "am");
        appointment.addEndTime("7/17/2020", "4:13", "am");
        appointmentBook.addAppointment(appointment);
        appointmentBook.addOwner("Buck");
        assertThat(appointmentBook.getOwnerName(), IsEqual.equalTo("Buck"));

    }

    private AppointmentBook getApp() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13", "am");
        appointment.setStartOfAppointment(appointment.beginTime);
        appointment.addEndTime("7/17/2020", "4:13", "am");
        appointment.setEndOfAppointment(appointment.endTime);
        appointmentBook.addAppointment(appointment);
        Appointment appt = new Appointment();
        appt.addDescription("Bom");
        appt.addBeginTime("7/17/2020", "3:13", "am");
        appt.setStartOfAppointment(appt.beginTime);
        appt.addEndTime("7/17/2020", "4:13", "am");
        appt.setEndOfAppointment(appt.endTime);
        appointmentBook.addAppointment(appt);
        return appointmentBook;
    }

    @Test
    void testBookDisplay(){
        AppointmentBook myApp = getApp();
    }

    @Test
    void testToString() {
        AppointmentBook appBook = getApp();
    }

    @Test
    void testSorter() {
        AppointmentBook apptBook = getApp();
        AppointmentBook apptBook2 = new AppointmentBook();
        Appointment appt = new Appointment();
        appt = apptBook.appointments.get(0);
        long getTime = appt.getBeginTime().getTime();
        appt = apptBook.appointments.get(1);
        getTime = appt.getBeginTime().getTime();
        PrettyPrinter printer = new PrettyPrinter(new PrintWriter(System.out));
        ArrayList<Appointment> appointments = printer.sortAppointments(apptBook.appointments);

        appt = appointments.get(0);
        getTime = appt.getBeginTime().getTime();
        appt = appointments.get(1);
        getTime = appt.getBeginTime().getTime();

        getTime = apptBook.appointments.get(0).getBeginTime().getTime();
        getTime = apptBook.appointments.get(1).getBeginTime().getTime();

        apptBook.appointments = appointments;
        getTime = apptBook.appointments.get(0).getBeginTime().getTime();
        getTime = apptBook.appointments.get(1).getBeginTime().getTime();
    }
}