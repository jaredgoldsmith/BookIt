package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 * Creates a PrettyPrinter object that takes in a file name that's an argument in
 * Project3's command line arguments. When the dump function is called, an appointment book
 * object is sent in as an argument and is written to the file to look nicer than the textFile
 * command. Can also be used to print to the standard out in the same pretty format with the prettyDisplay
 * function. This object also has a sort function to sort all of the appointments in chronological
 * order with the beginTime of the appointment. If this is the same another appointment, chronological
 * order with the endTime of the appointment. If these are equal, lexigraphically with the description.
 */

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

    protected String fileName;

    PrettyPrinter(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @param apptBook
     *  An Appointment book object sent in from Project3's main function.
     *
     * Uses BufferedWriter and FileWriter objects to write the contents of
     * the appointment book to an external text file, first writing in the
     * owner of the appointment book, followed by looping through the appointment
     * collection.
     */
    @Override
    public void dump(AppointmentBook apptBook)
    {
        try {
            Appointment appt;
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            long minuteDifference;
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a MM/dd/yyyy");
            String prettyBegin;
            String prettyEnd;

            bw.write(apptBook.owner + "'s Appointment Book" + "\n");
            for(int i = 0; i < apptBook.appointments.size(); ++i){
                appt = apptBook.appointments.get(i);
                bw.write("Appointment #" + (i+1) + ": " + appt.description+"\n");
                prettyBegin = sdf.format(apptBook.appointments.get(i).getBeginTime());
                prettyEnd = sdf.format(apptBook.appointments.get(i).getEndTime());
                minuteDifference = (apptBook.appointments.get(i).getEndTime().getTime() - apptBook.appointments.get(i).getBeginTime().getTime())/1000/60;
                bw.write("Appointment starts at " + prettyBegin + " and ends at " + prettyEnd + ",\n");
                bw.write("for a total of " + minuteDifference + " minutes" +"\n\n");
            }
            bw.close();
        }
        catch(IOException e){
            System.err.println("The file you were looking for does not exist");
            System.exit(1);
        }
    }

    /**
     * @param apptBook
     *  Takes in an AppointmentBook object and prints to standard out
     *  in the same pretty format as the dump function
     */
    public void prettyDisplay(AppointmentBook apptBook){
        String prettyBegin;
        String prettyEnd;
        long minuteDifference;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mma, MM/dd/yyyy");
        System.out.println("\n\n\n\n\n" + apptBook.owner + "'s Appointment Book");
       Appointment appt;
       for(int i = 0; i < apptBook.appointments.size(); ++i){
           appt = apptBook.appointments.get(i);
           System.out.println("Appointment #" + (i+1) + ": " + appt.description);
           prettyBegin = sdf.format(apptBook.appointments.get(i).getBeginTime());
           prettyEnd = sdf.format(apptBook.appointments.get(i).getEndTime());
           minuteDifference = (apptBook.appointments.get(i).getEndTime().getTime() - apptBook.appointments.get(i).getBeginTime().getTime())/1000/60;
           System.out.println("Appointment starts at " + prettyBegin + " and ends at " + prettyEnd + ",");
           System.out.println("for a total of " + minuteDifference + " minutes\n");
       }
       System.out.println("\n\n\n\n\n\n\n");
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
}
