package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;
import static org.hamcrest.Matchers.equalTo;
import java.io.IOException;
import java.util.ArrayList;
import edu.pdx.cs410J.ParserException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class TextDumperTest {
    @Test
    void dumperDumpsAppointmentBookOwner() throws IOException {
        String owner = "Owner";
        AppointmentBook book = new AppointmentBook(owner);
        String fileName = "pblam.txt";
        TextDumper dumper = new TextDumper(fileName);
        dumper.dump(book);
        String dumpedText = book.getOwnerName();
        assertThat(dumpedText, containsString(owner));
    }

    @Test
    void dumpNewAppointment()throws IOException, ParserException{
        Appointment appt = new Appointment();
        appt.addDescription("Good times had by all");
        appt.addBeginTime("02/24/1982", "3:55", "am");
        appt.addEndTime("02/24/1982", "4:55", "am");
        AppointmentBook apptbook = new AppointmentBook();
        apptbook.addOwner("owner");
        apptbook.addAppointment(appt);
        TextDumper dumper = new TextDumper("pfri.txt");
        dumper.dump(apptbook);
        TextParser parser = new TextParser("pfri.txt");
        AppointmentBook appt2 = new AppointmentBook();
        try {
            appt2 = parser.parse();
        }
        catch(ParserException e){
            System.out.println("Cannot parse an empty file");
            System.exit(1);
        }
        assertThat(apptbook.getOwnerName(), equalTo(appt2.getOwnerName()));
    }
    public ArrayList<Appointment> sortAppointments(ArrayList<Appointment> appointments){
        ArrayList<Appointment> sorted = new ArrayList<>();
        int [] orderOfAppointments = new int[appointments.size()];
        long greater = 0;
        int i = 0;
        Appointment hold = new Appointment();

        hold = appointments.get(i);
        for(i = 0; i < appointments.size(); ++i){
            if(appointments.get(i).getBeginTime().getTime() > greater)
                greater = appointments.get(i).getBeginTime().getTime();
        }
        return appointments;
    }
}
