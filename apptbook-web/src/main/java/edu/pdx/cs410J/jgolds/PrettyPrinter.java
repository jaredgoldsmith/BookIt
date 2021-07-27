package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void dump(AppointmentBook apptBook) throws IOException {
        PrintWriter pw = new PrintWriter(this.writer);
        //pw.println("Appointments for " + apptBook.getOwnerName());
/*
        for (Appointment appointment : book.getAppointments()) {
            pw.println("  " + appointment.getDescription());
        }
        try {

 */
            //Appointment appt;
            long minuteDifference;
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a MM/dd/yyyy");
            String prettyBegin;
            String prettyEnd;
            int i = 1;
            Appointment appt = new Appointment();

            pw.println("\n\n\n" + apptBook.owner + "'s Appointment Book" + "\n");
        //for (Appointment appt : apptBook.appointments) {
            for(i = 0; i < apptBook.appointments.size(); ++i){
               /* appt = apptBook.appointments.get(i);
                System.out.println("Dez is: )
                pw.write("Appointment #" + (i+1) + ": " + appt.getDescription()+"\n");
                prettyBegin = sdf.format(appt.getBeginTime());
                prettyEnd = sdf.format(appt.getEndTime());
                minuteDifference = (appt.getEndTime().getTime() - appt.getBeginTime().getTime())/1000/60;
                pw.write("Appointment starts at " + prettyBegin + " and ends at " + prettyEnd + ",\n");
                pw.write("for a total of " + minuteDifference + " minutes" +"\n\n");

                */
                appt = apptBook.appointments.get(i);
                pw.println("Appointment #" + (i+1) + ": " + appt.description);
                prettyBegin = sdf.format(apptBook.appointments.get(i).getBeginTime());
                prettyEnd = sdf.format(apptBook.appointments.get(i).getEndTime());
                minuteDifference = (apptBook.appointments.get(i).getEndTime().getTime() - apptBook.appointments.get(i).getBeginTime().getTime())/1000/60;
                pw.println("Appointment starts at " + prettyBegin + " and ends at " + prettyEnd + ",");
                pw.println("for a total of " + minuteDifference + " minutes\n");
            }
            pw.println("\n\n\n");
            pw.close();
            /*
        }
        catch(IOException e){
            System.err.println("The file you were looking for does not exist");
            System.exit(1);
        }
    }

             */
        pw.flush();
    }
    /**
     *
     * @param appointmentsArg
     *  Takes in an ArrayList of appointments
     * @return
     *  Returns a sorted version of the appointments parameter
     */
    public ArrayList<Appointment> sortAppointments(ArrayList<Appointment> appointmentsArg){
        ArrayList<Appointment> appointments= new ArrayList<>();
        for(int k = 0; k < appointmentsArg.size(); ++k){
            appointments.add(k, appointmentsArg.get(k));
        }
        int i;
        int j;
        Appointment hold;

        for(i = 0; i < appointments.size() -1; ++i) {
            for (j = 0; j < appointments.size() - i -1; ++j) {
                if((appointments.get(j).compareTo(appointments.get(j+1)) > 0)){
                    hold = appointments.get(j);
                    appointments.set(j, appointments.get(j+1));
                    appointments.set(j+1, hold);
                }
            }
        }
        return appointments;
    }
    public ArrayList<Appointment> sortAppointmentsByDate(ArrayList<Appointment> appointmentsArg, String startTime, String endTime) {
        ArrayList<Appointment> appointments = new ArrayList<>();
        DateFormat dtf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        Date begintime = null;
        try {
            begintime = dtf.parse(startTime);
        } catch (ParseException e) {
            System.err.println("Incorrect start date conversion");
            System.exit(1);
        }
        Date endtime = null;
        try {
            endtime = dtf.parse(endTime);
        } catch (ParseException e) {
            System.err.println("Incorrect end date conversion");
            System.exit(1);
        }
        if(endtime == null){
            System.err.println("Failed converting string into date");
            System.exit(1);
        }
        if(begintime == null){
            System.err.println("Failed converting string into date");
            System.exit(1);
        }
        int i = 0;
        for (int k = 0; k < appointmentsArg.size(); ++k) {
            if((appointmentsArg.get(k).startOfAppointment.getTime() >= begintime.getTime()) && (appointmentsArg.get(k).endOfAppointment.getTime() <= endtime.getTime())) {
                appointments.add(i, appointmentsArg.get(k));
                ++i;
            }
        }
        return appointments;
    }


}