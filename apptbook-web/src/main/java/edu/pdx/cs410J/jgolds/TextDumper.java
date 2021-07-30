package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * Creates a TextDumper object that writes out an appointment book with
 * a printwriter mechanism. For this program, it will be used to write
 * out the appointment book to a given url
 */
public class TextDumper implements AppointmentBookDumper<AppointmentBook> {
    private final Writer writer;

    /**
     *
     * @param writer
     *  Constructor to take in a Writer object
     *  and copy it to the field
     */
    public TextDumper(Writer writer) {
        this.writer = writer;
    }

    /**
     * Writes appointment book with the printwriter. First prints the owner,
     * and on a new line the description, start time of the appointment and
     * then the end time of the appointment.
     * @param book
     *  The appointment book we are writing out
     * @throws IOException
     */
    @Override
    public void dump(AppointmentBook book) throws IOException {
        PrintWriter pw = new PrintWriter(this.writer);
        pw.println(book.getOwnerName());
        for(Appointment appointment : book.getAppointments()) {
            pw.println(appointment.getDescription());
            pw.println(appointment.beginTime);
            pw.println(appointment.endTime);
        }

        pw.flush();
    }
}