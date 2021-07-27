package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AppointmentBookDumper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class PrettyPrinter implements AppointmentBookDumper<AppointmentBook> {
    private final Writer writer;

    public PrettyPrinter(Writer writer) {
        this.writer = writer;
    }

    @Override
    public void dump(AppointmentBook book) throws IOException {
        PrintWriter pw = new PrintWriter(this.writer);
        pw.println("Appointments for " + book.getOwnerName());

        for (Appointment appointment : book.getAppointments()) {
            pw.println("  " + appointment.getDescription());
        }

        pw.flush();
    }
}