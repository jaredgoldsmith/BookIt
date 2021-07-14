package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {

    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException {
        String owner = "Owner";
        AppointmentBook book = new AppointmentBook(owner);
        String fileName = "text.txt";
        TextDumper dumper = new TextDumper(fileName);
        dumper.dump(book);
        String dumpedText = book.getOwnerName();
        assertThat(dumpedText, containsString(owner));
    }

    @Test
    void dumpNewAppointment()throws IOException{
        Appointment appt = new Appointment();
        appt.addDescription("Good times had by all");
        appt.addBeginTime("02/24/1982", "3:55");
        appt.addEndTime("02/24/1982", "4:55");
        AppointmentBook apptbook = new AppointmentBook();
        apptbook.addOwner("owner");
        apptbook.addAppointment(appt);
        TextDumper dumper = new TextDumper("text.txt");
        dumper.dump(apptbook);
        TextParser parser = new TextParser("text.txt");
        AppointmentBook appt2 = new AppointmentBook();
        appt2 = parser.parse();
        assertThat(apptbook.getOwnerName(), equalTo(appt2.getOwnerName()));
    }
}
