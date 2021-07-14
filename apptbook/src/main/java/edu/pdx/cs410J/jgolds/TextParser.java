package edu.pdx.cs410J.jgolds;

//import edu.pdx.cs410J.AbstractAppointmentBook;
//import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AppointmentBookParser;
//import edu.pdx.cs410J.ParserException;

//import javax.swing.text.html.parser.Parser;
import java.io.*;

public class TextParser implements AppointmentBookParser<AppointmentBook> {
    AppointmentBook apptbook;
    String fileName;

    TextParser(String fileName){
        this.fileName = fileName;
    }
    @Override
    public AppointmentBook parse()
           // throws ParserException
           {
        File file = new File(fileName);
        if(!file.exists()){
            System.err.println("Cannot parse an empty file.");
            System.exit(1);
        }
        BufferedReader br = null;
        try{
            //ParserException e = null;
            //if(!file.exists()){
                //throw(e);
            //}
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
                    System.out.println("dez is: " + appt.description);
                    i = 1;
                }
                else if(i == 1){
                    appt.beginTime = line;
                    System.out.println("beginTime is: " + appt.beginTime);
                    i = 2;
                }
                else
                    //if(i == 2)
                    {
                    appt.endTime = line;
                    apptbook.addAppointment(appt);
                    System.out.println("endTime is: " + appt.endTime);
                    i = 0;
                }
            }
            //if(br != null);
            br.close();
            return apptbook;
        }
        /*catch(ParserException e){
           System.err.println("Cannot parse an empty file");
           System.exit(0);
        }
        */
        catch (IOException e){
            System.err.println("The file you were looking for does not exist!");
            System.exit(1);
        }
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
/*
    public void addFileName(String fileName){
        this.fileName = fileName;
    }
*/

}
