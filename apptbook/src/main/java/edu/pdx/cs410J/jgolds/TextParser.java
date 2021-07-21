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
        /*try{
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
         */
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
    //@Override
    public AppointmentBook parse() throws ParserException{
        //File file = new File(fileName);
        //BufferedReader br = null;
        Project3 proj = new Project3();
        String [] split;
        File file = new File(fileName);
        //IOException ex = new IOException();

        try{
            //File file = new File(fileName);
            BufferedReader br;
            ParserException e = new ParserException(fileName);
            if(!file.exists())
               throw(e);
            br = new BufferedReader(new FileReader(fileName));
            AppointmentBook apptbook = new AppointmentBook();
            Appointment appt = new Appointment();
            String line;

            int i = 0;
            apptbook.owner = br.readLine();
            if(apptbook.owner == null)
                throw e;
            //if(apptbook.owner == null)
                //return apptbook;
            while((line = br.readLine()) != null){
                if(i == 0) {
                    appt = new Appointment();
                    appt.addDescription(line);
             //       System.out.println(line);
                    i = 1;
                }
                else if(i == 1){
                    appt.beginTime = line;
                    split = appt.beginTime.split(" ");
                    proj.parseDates(split[0]);
                    proj.parseTimes(split[1]);
            //        System.out.println(line);
                    i = 2;
                }
                else
                    {
                    appt.endTime = line;
                    split = appt.beginTime.split(" ");
                    proj.parseDates(split[0]);
                    proj.parseTimes(split[1]);
           //         System.out.println(line);
                    apptbook.addAppointment(appt);
                    i = 0;
                }
            }
            /*System.out.println("Parser start");
            for(i = 0; i < apptbook.appointments.size(); ++i)
                System.out.println(apptbook.appointments.get(i).description);
            System.out.println("Parser end");

             */
            br.close();
            return apptbook;
        }
        /*
        catch (IOException e){try {
            IOException exc = new IOException();
            throw new IOException("File doesn't exist", exc);
        }
        catch (IOException exc){
                System.exit(1);
            }
        }*/

        //catch (IOException e){
         //   System.exit(1);
        //}
        catch (ParserException e){
            throw new ParserException("Can't parse empty file", e);
            //System.err.println("Cannot parse an empty file");
            //System.exit(1);
        }
        /*
        try{
            if(br != null)
                br.close();
        }

         */
        catch(IOException e){
            System.err.println("Didn't work");
            System.exit(1);
        }
       return apptbook;
    }
}
