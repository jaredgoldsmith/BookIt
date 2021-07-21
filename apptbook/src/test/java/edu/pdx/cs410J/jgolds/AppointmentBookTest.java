package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;
//import java.util.Collection;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class AppointmentBookTest {

    @Test
    void getOwnerStringNeedsToBeImplemented() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13", "am");
        appointment.addEndTime("7/17/2020", "4:13", "am");
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
        appointment.addBeginTime("7/17/2020", "3:13", "am");
        appointment.addEndTime("7/17/2020", "4:13", "am");
        appointmentBook.addAppointment(appointment);
        assertThat(appointmentBook.getAppointments(), equalTo(appointmentBook.appointments));
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
        assertThat(appointmentBook.getOwnerName(), equalTo("Buck"));

    }

    private AppointmentBook getApp() {
        AppointmentBook appointmentBook = new AppointmentBook("Buck");
        Appointment appointment = new Appointment();
        appointment.addDescription("Tom");
        appointment.addBeginTime("7/17/2020", "3:13", "am");
        appointment.addEndTime("7/17/2020", "4:13", "am");
        appointmentBook.addAppointment(appointment);
        Appointment appt = new Appointment();
        appt.addDescription("Bom");
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
    void testBookDisplay(){
        AppointmentBook myApp = getApp();
        System.out.println(myApp.appointments);
        System.out.println(myApp.owner);
    }

    @Test
    void testToString() {
        AppointmentBook appBook = getApp();
        //assertThat(appBook.toString(), equalTo("Buck's appointment book with 1 appointments"));
    }
    private ArrayList<Appointment> sortAppointments(ArrayList<Appointment> appointmentsArg){

        ArrayList<Appointment> appointments= new ArrayList<>();
        appointments.add(0, appointmentsArg.get(0));
        appointments.add(1, appointmentsArg.get(1));
        //appointments = appointmentsArg;
        System.out.println("Before sorting dez: " + appointmentsArg.get(0).description);
        int [] orderOfAppointments = new int[appointments.size()];
        long greater = 0;
        int i = 0;
        int j = 0;
        int flag = 0;
        Appointment hold = new Appointment();
        Appointment hold2 = new Appointment();

        for(i = 0; i < appointments.size() -1; ++i) {
            for (j = 0; j < appointments.size() - i -1; ++j) {
                if (appointments.get(j).getBeginTime().getTime() == appointments.get(j+1).getBeginTime().getTime()){
                    if (appointments.get(j).getEndTime().getTime() == appointments.get(j+1).getEndTime().getTime()){
                        if (appointments.get(j).description.compareTo(appointments.get(j+1).description) > 0){
                            hold = appointments.get(j);
                            appointments.set(j, appointments.get(j+1));
                            appointments.set(j+1, hold);
                        }
                    }
                    else if (appointments.get(j).getEndTime().getTime() > appointments.get(j+1).getEndTime().getTime()){
                        hold = appointments.get(j);
                        appointments.set(j, appointments.get(j+1));
                        appointments.set(j+1, hold);
                    }

                }
                else if (appointments.get(j).getBeginTime().getTime() > appointments.get(j+1).getBeginTime().getTime()){
                    hold = appointments.get(j);
                    appointments.set(j, appointments.get(j+1));
                    appointments.set(j+1, hold);
                }
            }
        }
        System.out.println("After sorting dez: " + appointmentsArg.get(0).description);
        return appointments;
    }
    @Test
    void testSorter() {
        AppointmentBook apptBook = getApp();
        AppointmentBook apptBook2 = new AppointmentBook();
        Appointment appt = new Appointment();
        appt = apptBook.appointments.get(0);
        long getTime = appt.getBeginTime().getTime();
        System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        appt = apptBook.appointments.get(1);
        getTime = appt.getBeginTime().getTime();
        System.out.println(apptBook.appointments.get(1).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        ArrayList<Appointment> appointments = sortAppointments(apptBook.appointments);
        System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);

        appt = appointments.get(0);
        getTime = appt.getBeginTime().getTime();
        System.out.println(appt.description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        appt = appointments.get(1);
        getTime = appt.getBeginTime().getTime();
        System.out.println(appt.description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);

        System.out.println("Before switch");
        //appt = apptBook.appointments.get(0);
        getTime = apptBook.appointments.get(0).getBeginTime().getTime();
        System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        getTime = apptBook.appointments.get(1).getBeginTime().getTime();
        System.out.println(apptBook.appointments.get(1).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);

        apptBook.appointments = appointments;
        System.out.println("After switch");
        //appt = apptBook.appointments.get(0);
        getTime = apptBook.appointments.get(0).getBeginTime().getTime();
        System.out.println(apptBook.appointments.get(0).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);
        getTime = apptBook.appointments.get(1).getBeginTime().getTime();
        System.out.println(apptBook.appointments.get(1).description + " starts at " + appt.getBeginTime() + " which is in millis is " + getTime);




    }
}
