package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {
    @Test
    void cannotParseEmptyFile()throws IOException, ParserException {
        String emptyFile = "pfilt.txt";
        TextParser parser = new TextParser(emptyFile);
        assertThrows(ParserException.class, parser::parse);
    }
    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed(@TempDir File dir) throws IOException, ParserException {
        String fileName = "ptilt.txt";
        AppointmentBook book = getApp();
        TextDumper dumper = new TextDumper(fileName);
        dumper.dump(book);
        System.out.println(book.appointments);
        TextParser parser = new TextParser(fileName);
        AppointmentBook apptBook2 = parser.parse();
        System.out.println(apptBook2.appointments);
        System.out.println(book.appointments);
        assertThat(book.getOwnerName(), equalTo(apptBook2.getOwnerName()));
    }

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

}