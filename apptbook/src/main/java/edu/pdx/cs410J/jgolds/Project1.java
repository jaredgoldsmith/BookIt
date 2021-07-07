package edu.pdx.cs410J.jgolds;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {
  public static final String INCORRECT_DATE_FORMATTING = "Incorrect date formatting";
  public static final String INCORRECT_TIME_FORMATTING = "Incorrect time formatting";

  public static void main(String[] args) throws IOException{
    //Appointment appointment = new Appointment(args[1],args[2],args[3],args[4],args[5]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    //AppointmentBook appointmentBook= new AppointmentBook(args[0]);
    String owner = null;
    String description = null;
    String beginDate = null;
    String beginTime = null;
    String endDate = null;
    String endTime = null;
    boolean print = false;
    /*arguments  = parseArgs(args);
    parseDates(arguments[2]);
    parseDates(arguments[4]);
    parseTimes(arguments[3]);
    parseTimes(arguments[5]);
    Appointment appointment = new Appointment();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
*/
    //System.err.println("Missing command line arguments");
    Appointment appointment = new Appointment();
    for (String arg: args) {
      if(arg.equals("-README")) {
        displayReadMe();
        System.exit(0);
      }
      //System.out.println(arg);
      else if(arg.equals("-print"))
        print = true;
      else if(owner == null) {
        owner = arg;
      }
      else if(description == null) {
        description = arg;
        appointment.addDescription(description);
      }
      else if(beginDate == null) {
        parseDates(arg);
        beginDate = arg;
      }
      else if(beginTime == null) {
        parseTimes(arg);
        beginTime = arg;
        appointment.addBeginTime(beginDate,beginTime);
      }
      else if(endDate == null) {
        parseDates(arg);
        endDate = arg;
      }
      else if(endTime == null) {
        parseTimes(arg);
        endTime = arg;
        appointment.addEndTime(endDate,endTime);
      }
      else{
        System.err.println("There are too many arguments!");
        System.exit(1);
      }
    }
    if(owner == null){
      System.err.println("There aren't any command line arguments. There should be at least 6. Mandatory " +
              "arguments should begin with owner, then a description, then a begin date, a begin time, " +
              "an end date, and lastly an end time. There can also be a -print argument or a -README argument " +
              "to precede the mandatory arguments. The -print argument will print out a description of the " +
              "appointment and the -README argument will display the contents within the README file and exit");
      System.exit(1);
    }
    if(endTime == null){
      System.err.println("There are not enough arguments, there should be at least 6, " +
              "not including the -print or -README arguments");
      System.exit(1);
    }
    if (print) {
      System.out.println(appointment.toString());
    }
    /*
    for(int i = 0; i < 8; ++i){
      if(arguments[i] == null)
        continue;
      else if(arguments[i] == "-print")
        System.out.println(arguments[1]);
      else if(arguments[i] == "-README")
        System.out.println("REAAAAAAADDDD");
      else
        System.out.println(arguments[i]);
    }
    */

    //System.out.println("Description is: " + appointment.getDescription());
    //System.out.println(appointment.toString());
    System.exit(1);
  }

  private static void parseTimes(String time){
    char []array = new char[time.length()];
    for (int i = 0; i < time.length(); ++i){
      array[i] = time.charAt(i);
    }
    System.out.println("Date of " + time + " is length " + time.length());
    for(int i = 0; i < time.length(); ++i)
      System.out.print(array[i]);

    if(time.length() < 4 || time.length() > 5)
      incorrectTimeFormatting();
    if(time.length() == 4){
      for(int i = 0; i < 4; ++i){
        if(i == 0) {
          checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
          System.out.println("1");
        }
        else if(i == 1 && array[i] != ':') {
          incorrectTimeFormatting();
          System.out.println("2");
        }
        else if(i ==2) {
          checkZeroThroughFive(array[i], INCORRECT_TIME_FORMATTING);
          System.out.println("3");
        }
        else if(i==3){
          checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
          System.out.println("4");
        }
      }
    }
    else{
      for(int i = 0; i < 5; ++i){
        if(i == 0)
          checkZeroThroughTwo(array[i], INCORRECT_TIME_FORMATTING);
        else if(i == 1) {
          checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
          if(array[0] == '2')
            checkZeroThroughThree(array[1], INCORRECT_TIME_FORMATTING);
        }
        else if(i == 2 && array[i] != ':')
          incorrectTimeFormatting();
        else if(i == 3)
          checkZeroThroughFive(array[i], INCORRECT_TIME_FORMATTING);
        else if(i == 4)
          checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
      }
    }
  }

  //Turn the date String into a character array and parse the different possible scenerios
  //that will work and that won't work
  private static void parseDates(String date) {
    if(date == null)
    {
      System.err.println("Error, no date entered to parse");
      System.exit(1);
    }
    char[] array = new char[date.length()];
    for (int i = 0; i < date.length(); ++i)
      array[i] = date.charAt(i);
    System.out.println("Date of " + date + " is length " + date.length());
    for(int i = 0; i < date.length(); ++i)
      System.out.print(array[i]);


    if (date.length() < 8 || date.length() > 10)
      incorrectDateFormatting();
    else if (date.length() == 8) {
      if (array[1] != '/' || array[3] != '/')
        incorrectDateFormatting();
      for (int i = 0; i < 8; ++i) {
        /*if (i == 1 || i == 3)
          continue;
          */
        if(i == 0 || i == 2) {
          checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
        }
        else {
          checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
        }
      }
    }
    else if (date.length() == 9){
      if(array[4] != '/')
        incorrectDateFormatting();
      if(array[1] == '/'){
        for(int i = 0; i < 9; ++i){
          if(i == 0)
            checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
          /*else if(i == 1 || i == 4)
            continue;

           */
          else if(i == 2)
            checkZeroThroughThree(array[i], INCORRECT_DATE_FORMATTING);
          else {
            checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
          }
        }
      }
      else if(array[2] == '/'){
        for(int i = 0; i < 9; ++i){
          if(i == 0)
            checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
          else if(i == 1) {
            if (array[0] == '0')
              checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
            else
              checkZeroThroughTwo(array[i], INCORRECT_DATE_FORMATTING);
          }
          else if(i == 3){
            checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
          }
          else {
            checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
          }
        }
      }
      else
        incorrectDateFormatting();
    }
    else{
      for(int i = 0; i < 10; ++i){
        if(i == 0)
          checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
        else if(i == 1) {
          if (array[0] == '0')
            checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
          else
            checkZeroThroughTwo(array[i], INCORRECT_DATE_FORMATTING);
        }
        else if((i == 2 || i == 5) && array[i] != '/')
          System.err.println("Incorrect date formatting");
        else if(i == 3)
          checkZeroThroughThree(array[i], INCORRECT_DATE_FORMATTING);
        else{
          checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
        }
      }
      if((array[0] == '0' && array[1] == '0') || array[3] == '0' && array[4] == '0')
        incorrectDateFormatting();
    }
  }

  private static void incorrectDateFormatting(){
    System.err.println(INCORRECT_DATE_FORMATTING);
    System.exit(1);
  }

  private static void incorrectTimeFormatting(){
    System.err.println(INCORRECT_TIME_FORMATTING);
    System.exit(1);
  }

  private static void errorMessage(String message){
    System.err.println(message);
    System.exit(1);
  }

  private static void checkZeroThroughFive(char ch, String message){
    if(ch < '0' || ch > '5')
      errorMessage(message);
  }
  private static void checkZeroAndOne(char ch, String message){
    if(ch < '0' || ch > '1')
      errorMessage(message);
  }

  private static void checkZeroThroughTwo(char ch, String message){
    if(ch < '0' || ch > '2')
    errorMessage(message);
  }
  private static void checkZeroThroughThree(char ch, String message){
    if(ch < '0' || ch > '3')
      errorMessage(message);
  }

  private static void checkOneThroughNine(char ch, String message){
    if(ch < '1' || ch > '9')
     errorMessage(message);
  }
  private static void checkZeroThroughNine(char ch, String message){
    if(ch < '0' || ch > '9')
      errorMessage(message);
  }
/*
  private static String [] parseArgs(String [] args){
    String [] arguments = new String[8];
    int count = 0;
    int index = 0;
    if(args.length < 6)
      System.err.println("Was expecting more arguments");
    else if(args.length > 8)
      System.err.println("Too many arguments");
    else if(args.length == 6){
      for(int i = 0; i < 6; ++i)
      {
        if(args[i] == "-print" || args[i] == "-README"){
          System.err.println("Was expecting more arguments, -print and -README should be in addition to the 6 arguments expected");
        }
        arguments[i] = args[i];
      }
    }
    else if(args.length == 7){
      for(int i = 0; i < 7; ++i){
        if(args[i].equals("-print") || args[i].equals("-README")){
          if(args[i].equals("-print"))
            arguments[6] = "-print";
          else
            arguments[7] = "-README";
          ++count;
        }
        else{
          arguments[index] = args[i];
          ++index;
        }
        if(count > 1)
          System.err.println("Was expecting more arguments, -print and -README should be in addition to the 6 arguments expected");
      }
    }
    else{
      for(int i = 0; i < 8; ++i){
        if(args[i].equals("-print") || args[i].equals("-README")){
          if(args[i].equals("-print"))
            arguments[6] = "-print";
          else
            arguments[7] = "-README";
          ++count;
        }
        else{
          arguments[index] = args[i];
          ++index;
        }
        if(count > 2)
          System.err.println("Was expecting more arguments, -print and -README should be in addition to the 6 arguments expected");
      }
    }
    return arguments;
  }
*/
  private static void displayReadMe() throws IOException {
    try (
            InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      //assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      while(line != null) {
        System.out.println(line);
        line = reader.readLine();
      }
    /*catch(Exception e){
      System.err.println("The resource text file was not found");
      System.exit(1);
    }
    */
  }
  }
}