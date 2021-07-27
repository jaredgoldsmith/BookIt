package edu.pdx.cs410J.jgolds;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import java.util.*;

public class AppointmentBookTest {

    @Test
    void appointmentBookCanBeDumpedAndParsed() throws IOException, ParserException {
        AppointmentBook book1 = new AppointmentBook("Owner");
        String description = "Description";
        Appointment appt = new Appointment(description);
        appt.addBeginTime("02/24/1982","3:33", "pm");
        appt.addEndTime("02/24/1982","4:33", "pm");

        book1.addAppointment(appt);

        appt.setStartOfAppointment(appt.beginTime);
        System.out.println(appt.startOfAppointment.getTime());
        appt.setEndOfAppointment(appt.endTime);
        System.out.println(appt.endOfAppointment.getTime());
        System.out.println(appt.toString());
        StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(sw);
        dumper.dump(book1);

        StringReader sr = new StringReader(sw.toString());
        TextParser parser = new TextParser(sr);

        AppointmentBook book2 = parser.parse();
        assertThat(book2.getOwnerName(), equalTo(book1.getOwnerName()));

        Appointment appointment = book2.getAppointments().iterator().next();
        assertThat(appointment.getDescription(), equalTo(description));

    }
}