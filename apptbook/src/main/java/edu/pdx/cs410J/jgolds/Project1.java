package edu.pdx.cs410J.jgolds;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project1 {
  public static final String INCORRECT_DATE_FORMATTING = "Incorrect date formatting";
  public static final String INCORRECT_TIME_FORMATTING = "Incorrect time formatting";

  public static void main(String[] args) {
    String [] arguments;
    Appointment appointment = new Appointment(args[1],args[2],args[3],args[4],args[5]);  // Refer to one of Dave's classes so that we can be sure it is on the classpath
    AppointmentBook appointmentBook= new AppointmentBook(args[0]);
    arguments  = parseArgs(args);
    parseDates(arguments[2]);
    parseDates(arguments[4]);
    parseTimes(arguments[3]);
    parseTimes(arguments[5]);

    //System.err.println("Missing command line arguments");
    for (String arg : args) {
      System.out.println(arg);
    }
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
          checkZeroThroughNine(array[i]);
          System.out.println("1");
        }
        else if(i == 1 && array[i] != ':') {
          incorrectTimeFormatting();
          System.out.println("2");
        }
        else if(i ==2) {
          checkZeroThroughFive(array[i]);
          System.out.println("3");
        }
        else if(i==3){
          checkZeroThroughNine(array[i]);
          System.out.println("4");
        }
      }
    }
    else{
      for(int i = 0; i < 5; ++i){
        if(i == 0)
          checkZeroThroughTwo(array[i]);
        else if(i == 1) {
          checkZeroThroughNine(array[i]);
          if(array[0] == '2')
            checkZeroThroughThree(array[1]);
        }
        else if(i == 2 && array[i] != ':')
          incorrectTimeFormatting();
        else if(i == 3)
          checkZeroThroughFive(array[i]);
        else if(i == 4)
          checkZeroThroughNine(array[i]);
      }
    }
  }

  //Turn the date String into a character array and parse the different possible scenerios
  //that will work and that won't work
  private static void parseDates(String date) {
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
        if (i == 1 || i == 3)
          continue;
        else if(i == 0 || i == 2) {
          checkOneThroughNine(array[i]);
        }
        else {
          checkZeroThroughNine(array[i]);
        }
      }
    }
    else if (date.length() == 9){
      if(array[4] != '/')
        incorrectDateFormatting();
      if(array[1] == '/'){
        for(int i = 0; i < 9; ++i){
          if(i == 0)
            checkOneThroughNine(array[i]);
          else if(i == 1 || i == 4)
            continue;
          else if(i == 2)
            checkZeroThroughThree(array[i]);
          else {
            checkZeroThroughNine(array[i]);
          }
        }
      }
      else if(array[2] == '/'){
        for(int i = 0; i < 9; ++i){
          if(i == 0)
            checkZeroAndOne(array[i]);
          else if(i == 1) {
            if (array[0] == '0')
              checkOneThroughNine(array[i]);
            else
              checkZeroThroughTwo(array[i]);
          }
          else if(i == 2 || i == 4)
            continue;
          else if(i == 3){
            checkOneThroughNine(array[i]);
          }
          else {
            checkZeroThroughNine(array[i]);
          }
        }
      }
      else
        incorrectDateFormatting();
    }
    else{
      for(int i = 0; i < 10; ++i){
        if(i == 0)
          checkZeroAndOne(array[i]);
        else if(i == 1) {
          if (array[0] == '0')
            checkOneThroughNine(array[i]);
          else
            checkZeroThroughTwo(array[i]);
        }
        else if((i == 2 || i == 5) && array[i] != '/')
          System.err.println("Incorrect date formatting");
        else if(i == 3)
          checkZeroThroughThree(array[i]);
        else{
          checkZeroThroughNine(array[i]);
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

  private static void checkZeroThroughFive(char ch){
    if(ch < '0' || ch > '5')
      incorrectTimeFormatting();
  }
  private static void checkZeroAndOne(char ch){
    if(ch < '0' || ch > '1')
      incorrectDateFormatting();
  }

  private static void checkZeroThroughTwo(char ch){
    if(ch < '0' || ch > '2')
      incorrectDateFormatting();
  }
  private static void checkZeroThroughThree(char ch){
    if(ch < '0' || ch > '3')
      incorrectDateFormatting();
  }

  private static void checkOneThroughNine(char ch){
    if(ch < '1' || ch > '9')
      incorrectDateFormatting();
  }
  private static void checkZeroThroughNine(char ch){
    if(ch < '0' || ch > '9')
      incorrectDateFormatting();
  }

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


}