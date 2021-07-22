package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;
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

    private ArrayList<Appointment> sortAppointments(ArrayList<Appointment> appointmentsArg){

        ArrayList<Appointment> appointments= new ArrayList<>();
        appointments.add(0, appointmentsArg.get(0));
        appointments.add(1, appointmentsArg.get(1));
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
        return appointments;
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
        ArrayList<Appointment> appointments = sortAppointments(apptBook.appointments);

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
