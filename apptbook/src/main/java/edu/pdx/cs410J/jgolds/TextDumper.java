package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookDumper;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.*;


public class TextDumper implements AppointmentBookDumper<AppointmentBook> {

    protected String fileName;

    TextDumper(String fileName) {
        this.fileName = fileName;
    }

    //@Override
    public void dump(AppointmentBook apptBook) throws IOException {
        try {
            //ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
            //out.writeObject(abstractAppointmentBook);
            //Appointment []appts = new Appointment[apptBook.appointments.size()];
            Appointment appt = new Appointment();

            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            bw.write(apptBook.owner + "\n");
            System.out.println("Hey owner from dump is: " + apptBook.owner);
            for(int i = 0; i < apptBook.appointments.size(); ++i){
                    appt = apptBook.appointments.get(i);
                    bw.write(appt.description+"\n");
                    bw.write(appt.beginTime + "\n");
                    bw.write(appt.endTime + "\n");
            }
            bw.close();
        }
        catch(IOException e){
            System.err.println("The file you were looking for does not exist");
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void addFileName(String fileName) {
        this.fileName = fileName;
    }
}
