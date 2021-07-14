package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.AppointmentBookParser;
import java.io.*;

/**
 * Creates a TextParser Object with a filename String sent in as a constructor argument.
 */
public class TextParser implements AppointmentBookParser<AppointmentBook> {
    AppointmentBook apptbook;
    String fileName;

    TextParser(String fileName){
        try{
            File file = new File(fileName);
            ParserException e = new ParserException(fileName);
            if(!file.exists()){
               throw(e);
            }
        }
        catch(ParserException e){
            System.err.println("Cannot parse an empty file");
            System.exit(1);
        }
        this.fileName = fileName;
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
    public AppointmentBook parse() {
        //File file = new File(fileName);
        //BufferedReader br = null;

        try{
            File file = new File(fileName);
            BufferedReader br;
            ParserException e = new ParserException(fileName);
            if(!file.exists()){
               throw(e);
            }
            br = new BufferedReader(new FileReader(fileName));
            AppointmentBook apptbook = new AppointmentBook();
            Appointment appt = new Appointment();
            String line;

            int i = 0;
            apptbook.owner = br.readLine();
            if(apptbook.owner == null)
                return apptbook;
            while((line = br.readLine()) != null){
                if(i == 0) {
                    appt.description = line;
                    i = 1;
                }
                else if(i == 1){
                    appt.beginTime = line;
                    i = 2;
                }
                else
                    {
                    appt.endTime = line;
                    apptbook.addAppointment(appt);
                    i = 0;
                }
            }
            br.close();
            return apptbook;
        }
        catch (IOException e){
            System.err.println("The file you were looking for does not exist!");
            System.exit(1);
        }
        catch (ParserException e){
            System.err.println("Cannot parse an empty file");
            System.exit(1);
        }
        /*
        try{
            if(br != null)
                br.close();
        }
        catch(IOException e){
            System.err.println("Didn't work");
            System.exit(1);
        }
         */
       return apptbook;
    }
}
