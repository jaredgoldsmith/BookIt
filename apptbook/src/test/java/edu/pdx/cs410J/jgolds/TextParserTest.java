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
        String emptyFile = "bext.txt";
        File empty = new File(emptyFile);
       // if(!empty.exists()){
            //TextParser parser = new TextParser(emptyFile);
            //assertThrows(IOException.class, parser::parse);
        //}
        //else
            //{
            TextParser parser = new TextParser(emptyFile);
            assertThrows(ParserException.class, parser::parse);
        //}

        //assertThrows(ParserException.class, parser::parse);

    }
    @Test
    void appointmentBookOwnerCanBeDumpedAndParsed(@TempDir File dir) throws IOException, ParserException {
        String owner = "Owner";
        String fileName = "fext.txt";
        //File file = new File(dir, fileName);
        //if(file.exists()) {
            AppointmentBook book = new AppointmentBook(owner);
            TextDumper dumper = new TextDumper(fileName);
        //    TextParser parser = new TextParser(fileName);

            dumper.dump(book);
        TextParser parser = new TextParser(fileName);

        book = parser.parse();
            assertThat(book.getOwnerName(), equalTo(owner));
        //}
        /*
        else{
            AppointmentBook book = new AppointmentBook(owner);
            TextDumper dumper = new TextDumper(fileName);
            dumper.dump(book);

        }*/
    }

    @Test
    void appointmentBookOwnerCanBeDumpedToFileAndParsed(@TempDir File dir) throws IOException, ParserException {
        File textFile = new File(dir, "appointments.txt");
        String owner = "Owner";

        AppointmentBook book = new AppointmentBook(owner);
        assertThat(book.getOwnerName(), equalTo(owner));
    }
}