package edu.pdx.cs410J.jgolds;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import javax.swing.text.html.parser.Parser;
import java.io.IOException;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
public class TextDumperTest {

    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException {
        String owner = "Owner";
        AppointmentBook book = new AppointmentBook(owner);
        String fileName = "text.txt";

        //StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(fileName);
        dumper.dump(book);

        //sw.flush();

        //String dumpedText = sw.toString();
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
        try {
            appt2 = parser.parse();

        }
        catch(ParserException e){
            System.err.println("Unable to parse correctly");
            System.exit(0);

        }


        assertThat(apptbook.getOwnerName(), equalTo(appt2.getOwnerName()));
    }
}
