package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

    @Test
    void emptyFileCannotBeParsed() {
        //InputStream resource = getClass().getResourceAsStream("emptyFile.txt");
        //assertNotNull(resource);
        ParserException e = null;
        String noFile = "empty.txt";
        TextParser parser = new TextParser(noFile);
        //assertThrows(ParserException.class, parser::parse);
        //assertThat(e.equals(parser.parse()));

        //TextParser parser = new TextParser(new InputStreamReader(resource));
        //assertThrows(ParserException.class, parser::parse);
    }

    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed() throws IOException, ParserException {
        String owner = "Owner";
        String fileName = "text.txt";
        AppointmentBook book = new AppointmentBook(owner);


        //StringWriter sw = new StringWriter();
        TextDumper dumper = new TextDumper(fileName);
        dumper.dump(book);
        //dumper.dump(book);

        TextParser parser = new TextParser(fileName);
        book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(owner));
    }

    @Test
    void appointmentBookOwnerCanBeDumpedToFileAndParsed(@TempDir File dir) throws IOException, ParserException {
        File textFile = new File(dir, "appointments.txt");

        String owner = "Owner";
        AppointmentBook book = new AppointmentBook(owner);

        //TextDumper dumper = new TextDumper(new FileWriter(textFile));
        //dumper.dump(book);

        //TextParser parser = new TextParser(new FileReader(textFile));
        //book = parser.parse();

        assertThat(book.getOwnerName(), equalTo(owner));
    }
}