package edu.pdx.cs410J.jgolds;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

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
        return appointmentBook;
    }
    @Test
    void testSortingFunction(){
        AppointmentBook apptBook = getApp();
        AppointmentBook apptBook2 = new AppointmentBook();
        Appointment appt = new Appointment();
        PrettyPrinter printer = new PrettyPrinter("pdill.txt");
        appt = apptBook.appointments.get(0);
        long getTime = appt.getBeginTime().getTime();
        appt = apptBook.appointments.get(1);
        getTime = appt.getBeginTime().getTime();
        ArrayList<Appointment> appointments = printer.sortAppointments(apptBook.appointments);
        appt = appointments.get(0);
        getTime = appt.getBeginTime().getTime();
        appt = appointments.get(1);
        getTime = appt.getBeginTime().getTime();
        getTime = apptBook.appointments.get(0).getBeginTime().getTime();
        getTime = apptBook.appointments.get(1).getBeginTime().getTime();
        appointments = printer.sortAppointments(apptBook.appointments);
        apptBook.appointments = appointments;
        printer.dump(apptBook);
        getTime = apptBook.appointments.get(0).getBeginTime().getTime();
        getTime = apptBook.appointments.get(1).getBeginTime().getTime();
    }

    @Test
    void testPrintingToFile(){
        String fileName = "ppck.txt";
        AppointmentBook apptBook = getApp();
        ArrayList<Appointment> appointments = new ArrayList<>();
        PrettyPrinter printer = new PrettyPrinter(fileName);
        appointments = printer.sortAppointments(apptBook.appointments);
        apptBook.appointments = appointments;
        printer.dump(apptBook);
    }

    @Test
    void testPrintingToStandardOut(){
        String fileName = "pchet.txt";
        AppointmentBook apptBook = getApp();
        ArrayList<Appointment> appointments = new ArrayList<>();
        PrettyPrinter printer = new PrettyPrinter(fileName);
        appointments = printer.sortAppointments(apptBook.appointments);
        apptBook.appointments = appointments;
        printer.prettyDisplay(apptBook);
    }
}
