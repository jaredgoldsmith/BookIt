package edu.pdx.cs410J.jgolds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.AppointmentBookParser;
import java.io.*;
import java.lang.Object;
import java.util.ArrayList;

import android.content.*;

/**
 * Creates a TextParser Object with a filename String sent in as a constructor argument.
 */
public class TextParser implements AppointmentBookParser<AppointmentBook> {
    AppointmentBook apptbook;
    File fileName;
    String fileString;

    TextParser(File fileName){
        this.fileName = fileName;
        this.fileString = fileName.getName();
    }

    /**
     *
     *
     * @return
     *   returns an appointment book that is filled in by parsing the text file, whose
     *   string is sent in when the textParser object is created. Goes through line by line
     *   starting with the owner of the appointment book and then going through all of the
     *   appointments stored in the text file. If the text file doesn't exist, then the
     *   parsing will not take place.
     */
    @Override
    public AppointmentBook parse() throws ParserException{
        //Project3 proj = new Project3();
        String [] split;
        //File file = new File(fileName);

        try{
            BufferedReader br;
            ParserException e = new ParserException(fileString);
            //if(!file.exists())
             //   throw(e);
            br = new BufferedReader(new FileReader(fileName));
            if(br == null)
                throw e;
            AppointmentBook apptbook = new AppointmentBook();
            Appointment appt = new Appointment();
            String line;

            int i = 0;
            apptbook.owner = br.readLine();
            if(apptbook.owner == null)
                return apptbook;
            while((line = br.readLine()) != null){
                if(i == 0) {
                    appt = new Appointment();
                    appt.addDescription(line);
                    i = 1;
                }
                else if(i == 1){
                    appt.beginTime = line;
                    split = appt.beginTime.split(" ");
                    if(split[2].equals("am") || split[2].equals("pm"))
                    {
         //               proj.parseDates(split[0]);
          //              proj.parseTimes(split[1]);
                        appt.setStartOfAppointment(line);
                        i = 2;
                    }
                    else{
                        System.err.println("Text file contains faulty date format");
                        System.exit(1);
                    }
                }
                else
                {
                    appt.endTime = line;
                    split = appt.endTime.split(" ");
                    if(split[2].equals("am") || split[2].equals("pm")) {
           //             proj.parseDates(split[0]);
            //            proj.parseTimes(split[1]);
                        appt.setEndOfAppointment(line);
                        apptbook.addAppointment(appt);
                        i = 0;
                    }
                    else{
                        System.err.println("Text file contains faulty date format");
                        System.exit(1);
                    }
                }
            }
            if(i != 0){
                System.err.println("Text file format is incorrect");
                System.exit(1);
            }
            br.close();
            return apptbook;
        }
        catch (ParserException e){
            throw new ParserException("Can't parse empty file", e);
        }
        catch(IOException e){
            System.err.println("Didn't work");
            System.exit(1);
        }
        return apptbook;
    }

    public ArrayList<Appointment> sortAppointments(ArrayList<Appointment> appointmentsArg){
        ArrayList<Appointment> appointments= new ArrayList<>();
        for(int k = 0; k < appointmentsArg.size(); ++k){
            appointments.add(k, appointmentsArg.get(k));
        }
        int i;
        int j;
        Appointment hold;

        for(i = 0; i < appointments.size() -1; ++i) {
            for (j = 0; j < appointments.size() - i -1; ++j) {
                if((appointments.get(j).compareTo(appointments.get(j+1)) > 0)){
                    hold = appointments.get(j);
                    appointments.set(j, appointments.get(j+1));
                    appointments.set(j+1, hold);
                }
            }
        }
        return appointments;
    }
}
