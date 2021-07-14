package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.IOException;
import java.io.*;

/**
 * Creates a TextDumper object that takes in a file name that's an argument in
 * Project2's arguments. When the dump function is called, an appointment book
 * object is sent in as an argument and is written to the file.
 */

public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    protected String fileName;

    TextDumper(String fileName) {
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
    public void dump(AppointmentBook apptBook)
    {
        try {
            Appointment appt;
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));

            bw.write(apptBook.owner + "\n");
            for(int i = 0; i < apptBook.appointments.size(); ++i){
                    appt = apptBook.appointments.get(i);
                    bw.write(appt.description+"\n");
                    bw.write(appt.beginTime + "\n");
                    bw.write(appt.endTime + "\n");
            }
            bw.close();
        }
        catch(IOException e){
            System.err.println("The file you were looking for does not exist");
            System.exit(1);
        }
    }
}
