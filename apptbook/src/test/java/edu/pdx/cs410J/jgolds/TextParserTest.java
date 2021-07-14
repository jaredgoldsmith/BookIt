package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
public class TextParserTest {

    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException {
        String owner = "Owner";
        String fileName = "text.txt";
        AppointmentBook book = new AppointmentBook(owner);
        TextDumper dumper = new TextDumper(fileName);
        TextParser parser = new TextParser(fileName);

        dumper.dump(book);
        book = parser.parse();
        assertThat(book.getOwnerName(), equalTo(owner));
    }

    @Test
    void appointmentBookOwnerCanBeDumpedToFileAndParsed(@TempDir File dir) throws IOException, ParserException {
        File textFile = new File(dir, "appointments.txt");
        String owner = "Owner";

        AppointmentBook book = new AppointmentBook(owner);
        assertThat(book.getOwnerName(), equalTo(owner));
    }
}