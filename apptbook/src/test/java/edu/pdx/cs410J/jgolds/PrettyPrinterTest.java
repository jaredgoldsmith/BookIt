package edu.pdx.cs410J.jgolds;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import java.io.IOException;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;


public class PrettyPrinterTest {

    private AppointmentBook getApp() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Therapist appointment");
        appointment.addBeginTime("7/17/2020", "3:13", "am");
        appointment.addEndTime("7/17/2020", "4:13", "am");
        appointmentBook.addAppointment(appointment);
        Appointment appt = new Appointment();
        appt.addDescription("Cool date with Sally");
        appt.addBeginTime("7/17/2020", "3:13", "am");
        appt.addEndTime("7/17/2020", "4:13", "am");
        appointmentBook.addAppointment(appt);
        /*
        Appointment appt = new Appointment();
        appt.addDescription("Zed is great");
        appt.addBeginTime("03/14/1982", "5:05");
        appointment.addEndTime("03/14/1982", "6:13");
        appointmentBook.addAppointment(appt);

         */

        return appointmentBook;
    }
    @Test
    void testSortingFunction(){
        AppointmentBook apptBook = getApp();
        AppointmentBook apptBook2 = new AppointmentBook();
        Appointment appt = new Appointment();
        PrettyPrinter printer = new PrettyPrinter("txt.txt");
        appt = apptBook.appointments.get(0);
        long getTime = appt.getBeginTime().getTime();
        //System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        appt = apptBook.appointments.get(1);
        getTime = appt.getBeginTime().getTime();
        //System.out.println(apptBook.appointments.get(1).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        //System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        ArrayList<Appointment> appointments = printer.sortAppointments(apptBook.appointments);
        //System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);

        appt = appointments.get(0);
        getTime = appt.getBeginTime().getTime();
        //System.out.println(appt.description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        appt = appointments.get(1);
        getTime = appt.getBeginTime().getTime();
        //System.out.println(appt.description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);

        //System.out.println("Before switch");
        //appt = apptBook.appointments.get(0);
        getTime = apptBook.appointments.get(0).getBeginTime().getTime();
        //System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        getTime = apptBook.appointments.get(1).getBeginTime().getTime();
        //System.out.println(apptBook.appointments.get(1).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        appointments = printer.sortAppointments(apptBook.appointments);
        apptBook.appointments = appointments;
        printer.dump(apptBook);

       // apptBook.appointments = appointments;
        //System.out.println("After switch");
        //appt = apptBook.appointments.get(0);
        getTime = apptBook.appointments.get(0).getBeginTime().getTime();
        //System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        getTime = apptBook.appointments.get(1).getBeginTime().getTime();
        //System.out.println(apptBook.appointments.get(1).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
    }

    @Test
    void testPrintingToFile(){
        String fileName = "Rando.txt";
        AppointmentBook apptBook = getApp();
        ArrayList<Appointment> appointments = new ArrayList<>();
        PrettyPrinter printer = new PrettyPrinter(fileName);
        appointments = printer.sortAppointments(apptBook.appointments);
        apptBook.appointments = appointments;
        printer.dump(apptBook);
    }

    @Test
    void testPrintingToStandardOut(){
        String fileName = "Rando.txt";
        AppointmentBook apptBook = getApp();
        ArrayList<Appointment> appointments = new ArrayList<>();
        PrettyPrinter printer = new PrettyPrinter(fileName);
        appointments = printer.sortAppointments(apptBook.appointments);
        apptBook.appointments = appointments;
        printer.prettyDisplay(apptBook);
    }


}
