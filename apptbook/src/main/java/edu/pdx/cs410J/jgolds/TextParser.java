package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
import edu.pdx.cs410J.ParserException;

import javax.swing.text.html.parser.Parser;
import java.io.*;

public class TextParser implements AppointmentBookParser {
    AppointmentBook apptbook;
    String fileName;

    TextParser(String fileName){
        this.fileName = fileName;
    }
    @Override
    public AppointmentBook parse()throws ParserException {
        //AbstractAppointmentBook apptbook= (AbstractAppointmentBook) in.readObject();

        BufferedReader br = null;
        try{
            //ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
            //AppointmentBook apptbook = (AppointmentBook) in.readObject();
            //ParserException e = null;
           // if(apptbook == null)
            //    throw(e);
            //if(br.readLine() == null)
            br = new BufferedReader(new FileReader(fileName));
            AppointmentBook apptbook = new AppointmentBook();
            Appointment appt = new Appointment();
            String description;
            String beginDate = null;
            String beginTime = null;
            String endDate = null;
            String endTime = null;
            String line = null;

            int i = 0;
            apptbook.owner = br.readLine();
            System.out.println("Parser owner is" + apptbook.owner);
            if(apptbook.owner == null)
                return apptbook;
            //line = apptbook.owner;
            while((line = br.readLine()) != null){
                //line = br.readLine();
                //if(line == null)
                 //   continue;
                if(i == 0) {

                    appt.description = line;
                    System.out.println("dez is: " + appt.description);
                    i = 1;
                }
                else if(i == 1){
                    appt.beginTime = line;
                    System.out.println("beginTime is: " + appt.beginTime);
                    i = 2;
                }
                else if(i == 2){
                    appt.endTime = line;
                    apptbook.addAppointment(appt);
                    System.out.println("endTime is: " + appt.endTime);
                    i = 0;
                }
            }
            if(br != null);
                br.close();
            return apptbook;
        }
        catch (IOException e){
            System.err.println("The file you were looking for does not exist!");
            System.exit(1);
        }
        /*catch (ClassNotFoundException e){
            System.err.println("The Appointmentbook object you were looking for does not exist!");
            System.exit(1);
        }
        catch (ParserException e){
            System.err.println("The Appointmentbook object you were looking for was empty!");
            System.exit(1);
        }*/
        try{
            if(br != null)
                br.close();
        }
        catch(IOException e){
            System.err.println("Didn't work");
            System.exit(1);
        }
       return apptbook;
    }

    public void addFileName(String fileName){
        this.fileName = fileName;
    }


}
