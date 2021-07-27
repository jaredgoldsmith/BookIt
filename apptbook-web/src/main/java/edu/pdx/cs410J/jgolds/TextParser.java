package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class TextParser implements AppointmentBookParser<AppointmentBook> {
    private final Reader reader;

    public TextParser(Reader reader) {
        this.reader = reader;
    }


    @Override
    public AppointmentBook parse() throws ParserException {
        BufferedReader br = new BufferedReader(this.reader);
        try {
            int i = 0;
            String owner = br.readLine();
            AppointmentBook book = new AppointmentBook(owner);
            Appointment appt = new Appointment();
            Project4 proj = new Project4();
            String [] split;
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if(i == 0) {
                    appt = new Appointment();
                    appt.description = line;
                    i = 1;
                }
                else if(i == 1){
                    appt.beginTime = line;
                    split = appt.beginTime.split(" ");
                    if(split[2].equals("am") || split[2].equals("pm"))
                    {
                        proj.parseDates(split[0]);
                        proj.parseTimes(split[1]);
                        appt.setStartOfAppointment(line);
                        i = 2;
                    }
                    else{
                        System.err.println("Text file contains faulty date format");
                        System.exit(1);
                    }
                }
                else{
                    appt.endTime = line;
                    split = appt.endTime.split(" ");
                    if(split[2].equals("am") || split[2].equals("pm")) {
                        proj.parseDates(split[0]);
                        proj.parseTimes(split[1]);
                        appt.setEndOfAppointment(line);
                        book.addAppointment(appt);
                        i = 0;
                    }
                    else{
                        System.err.println("Text file contains faulty date format");
                        System.exit(1);
                    }
                }
            }

            return book;

        } catch (IOException e) {
            throw new ParserException("While reading text", e);
        }

    }
}