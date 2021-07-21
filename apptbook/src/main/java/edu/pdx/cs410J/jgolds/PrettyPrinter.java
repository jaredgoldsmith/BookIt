package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.text.*;

/**
 * Creates a TextDumper object that takes in a file name that's an argument in
 * Project2's arguments. When the dump function is called, an appointment book
 * object is sent in as an argument and is written to the file.
 */

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {

    protected String fileName;

    PrettyPrinter(String fileName) {
        this.fileName = fileName;
    }

    /**
     *
     * @param apptBook
     *  An Appointment book object sent in from Project2's main function.
     *
     * Uses BufferedWriter and FileWriter objects to write the contents of
     * the appointment book to an external text file, first writing in the
     * owner of the appointment book, followed by looping through the appointment
     * collection.
     */
    @Override
    public void dump(AppointmentBook apptBook)
    {
        //ArrayList<Appointment> appointments = new ArrayList<>();
        //PrettyPrinter pretty = new PrettyPrinter();
        //appointments =
        try {
            Appointment appt;
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            long minuteDifference;
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm a MM/dd/yyyy");
            String prettyBegin;
            String prettyEnd;

            bw.write(apptBook.owner + "'s Appointment Book" + "\n");
            //bw.write("Appointment list in chronological order:");
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

    public void prettyDisplay(AppointmentBook apptBook){
        String prettyBegin;
        String prettyEnd;
        long minuteDifference;
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a MM/dd/yyyy");
        System.out.println(apptBook.owner + "'s Appointment Book");
       Appointment appt;
       for(int i = 0; i < apptBook.appointments.size(); ++i){
           appt = apptBook.appointments.get(i);
           System.out.println("Appointment #" + (i+1) + ": " + appt.description);
           prettyBegin = sdf.format(apptBook.appointments.get(i).getBeginTime());
           prettyEnd = sdf.format(apptBook.appointments.get(i).getEndTime());
           minuteDifference = (apptBook.appointments.get(i).getEndTime().getTime() - apptBook.appointments.get(i).getBeginTime().getTime())/1000/60;
           System.out.println("Appointment starts at " + prettyBegin + " and ends at " + prettyEnd + ",");
           System.out.println("for a total of " + minuteDifference + " minutes");
       }
    }

    public ArrayList<Appointment> sortAppointments(ArrayList<Appointment> appointmentsArg){
        ArrayList<Appointment> appointments= new ArrayList<>();
        for(int k = 0; k < appointmentsArg.size(); ++k){
            appointments.add(k, appointmentsArg.get(k));
        }
        //appointments = appointmentsArg;
        System.out.println("Before sorting dez: " + appointmentsArg.get(0).description);
        //int [] orderOfAppointments = new int[appointments.size()];
        //long greater = 0;
        int i;
        int j;
        //int flag;
        Appointment hold;
        //Appointment hold2 = new Appointment();

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
}
