package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.ParserException;
//import static org.hamcrest.Matchers.equalTo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.*;

/**
 * The main class for the CS410J appointment book Project
 */
public class Project2 {
  public static final String INCORRECT_DATE_FORMATTING = "Incorrect date formatting";
  public static final String INCORRECT_TIME_FORMATTING = "Incorrect time formatting";
  public static final String NOT_ENOUGH_ARGS = "There are not enough arguments, there should be at least 6, " +
          "not including the -print or -README arguments";
  /**
   * Creates a new <code>Project1</code>
   *
   * @param args
   *        1. The owner of AppointmentBook, made up of a collection of Appointments
   *        2. Description of the appointment
   *        3. Date of the start of the appointment
   *        4. Time of the start of the appointment
   *        5. Date of the end of the appointment
   *        6. Time of the end of the appointment
   * Optional parameters of -print and -README can precede the other arguments. -print will
   * display the toString method from AbstractAppointment class. -README will read the contents
   * of the README.txt file and exit once complete
   */
  public static void main(String[] args) throws IOException, ParserException{
    Appointment appointment = new Appointment();
    AppointmentBook appointmentBook = new AppointmentBook();
    AppointmentBook appt2;
    String filename = "text.txt";
    File file = new File(filename);
    boolean textFile = false;
    Project2 project = new Project2();
    String owner = null;
    String description = null;
    String beginDate = null;
    String beginTime = null;
    String endDate = null;
    String endTime = null;
    boolean print = false;
    boolean isThereTextFile = false;

    for (String arg: args) {
      if(arg.equals("-README")) {
        displayReadMe();
        System.exit(0);
      }
      else if(arg.equals("-print"))
        print = true;
      else if(arg.equals("-textFile")){
       textFile = true;
       isThereTextFile = true;
      }
      else if(textFile){
        textFile = false;
        file = new File(arg);
      }
      else if(owner == null) {
        owner = arg;
        appointmentBook.addOwner(owner);
      }
      else if(description == null) {
        if(!project.checkDescription(arg)){
          System.err.println("Description argument is missing from command line");
          System.exit(1);
        }
        else {
          description = arg;
          appointment.addDescription(description);
        }
      }
      else if(beginDate == null) {
        project.parseDates(arg);
        beginDate = arg;
      }
      else if(beginTime == null) {
        project.parseTimes(arg);
        beginTime = arg;
        appointment.addBeginTime(beginDate,beginTime);
      }
      else if(endDate == null) {
        project.parseDates(arg);
        endDate = arg;
      }
      else if(endTime == null) {
        project.parseTimes(arg);
        endTime = arg;
        appointment.addEndTime(endDate,endTime);
        appointmentBook.addAppointment(appointment);
      }
      else{
        System.err.println("There are too many arguments!");
        System.exit(1);
      }
    }
    if(owner == null){
      System.err.println("There aren't any command line arguments. There should be at least 6. Mandatory " +
              "arguments should begin with owner, then a description, then a begin date, a begin time, " +
              "an end date, and lastly an end time. There can also be a -print argument, a -README argument, " +
              "and a -textFile argument " +
              "to precede the mandatory arguments. The -print argument will print out a description of the " +
              "appointment, the -README argument will display the contents within the README file and exit, and " +
              "the -textFile command needs to be followed by a filename and this will either add the appointment book created" +
              " with the arguments sent in through the command line, or if the file already exists with an appointment " +
              "book, then the program will add the appointment created with the command line arguments.");
      System.exit(1);
    }
    if(endTime == null){
      System.err.println(NOT_ENOUGH_ARGS);
      System.exit(1);
    }
    if(isThereTextFile) {
      if (!file.exists()) {
        TextDumper textdump = new TextDumper(filename);
        textdump.dump(appointmentBook);
      } else {
        TextParser textparse = new TextParser(filename);
        appt2 = textparse.parse();
        if(!appt2.owner.equals(appointmentBook.owner)){
          System.err.println("The owner of the appointmentbook is not the same as the one on file.");
          System.exit(1);
        }
        appt2.addAppointment(appointment);
    //    System.out.println("Owner from main is: " + appt2.owner);
        TextDumper textdump = new TextDumper(filename);
        textdump.dump(appt2);
      }
      if (print) {
        System.out.println(appointment.toString());
      }
    }
    System.exit(1);
  }

  /**
   * Takes in a String that should be beginTime or endTime and makes sure it is
   * the correct format, i.e. 3:33 or 13:45 would both be appropriate
   */
  public boolean checkDescription(String description){
    char []descriptionArray = new char[description.length()];
    boolean doesDescriptionHaveAlphabet = false;
    for(int i = 0; i < description.length(); ++i)
      descriptionArray[i] = description.charAt(i);
    for(int i = 0; i < description.length(); ++i)
      System.out.println(descriptionArray[i]);
    for(int i = 0; i < description.length(); ++i){
      if(descriptionArray[i] >= 'A' && descriptionArray[i] <= 'z')
        doesDescriptionHaveAlphabet = true;
    }
    return doesDescriptionHaveAlphabet;
  }
  public void parseTimes(String time){
    char []array = new char[time.length()];
    for (int i = 0; i < time.length(); ++i){
      array[i] = time.charAt(i);
    }
    if(time.length() < 4 || time.length() > 5)
      incorrectTimeFormatting();
    if(time.length() == 4){
      for(int i = 0; i < 4; ++i){
        if(i == 0)
          checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
        else if(i == 1 && array[i] != ':')
          incorrectTimeFormatting();
        else if(i ==2)
          checkZeroThroughFive(array[i], INCORRECT_TIME_FORMATTING);
        else if(i==3)
          checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
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

  /**
   * Takes in a String that should be beginDate or endDate and makes sure it is
   * the correct format, i.e. 12/03/2020 or 05/3/2021 would both be appropriate
   */
  public void parseDates(String date) {
    if(date == null)
    {
      System.err.println("Error, no date entered to parse");
      System.exit(1);
    }
    char[] array = new char[date.length()];
    for (int i = 0; i < date.length(); ++i)
      array[i] = date.charAt(i);
    if (date.length() < 8 || date.length() > 10)
      incorrectDateFormatting();
    else if (date.length() == 8) {
      if (array[1] != '/' || array[3] != '/')
        incorrectDateFormatting();
      for (int i = 0; i < 8; ++i) {
        if (i == 1 || i == 3)
          continue;
        else if(i == 0 || i == 2) {
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
          else if(i == 1 || i == 4)
            continue;
          else if(i == 2)
            checkZeroThroughThree(array[i], INCORRECT_DATE_FORMATTING);
          else if(i == 3){
            if(array[2] == '3') {
              if (array[0] == '4' || array[0] == '6' || array[0] == '9') {
                if (array[i] != '0')
                  incorrectDateFormatting();
              }
                else if (array[0] == '2')
                  incorrectDateFormatting();
                else
                  checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
            }
          }
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
          else if(i == 2 || i ==4)
            continue;
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
      if(array[2] != '/' || array[5] != '/')
        incorrectDateFormatting();
      for(int i = 0; i < 10; ++i){
        if(i == 0)
          checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
        else if(i == 1) {
          if (array[0] == '0')
            checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
          else
            checkZeroThroughTwo(array[i], INCORRECT_DATE_FORMATTING);
        }
        else if(i == 2 || i == 5)
          continue;
        else if(i == 3)
          checkZeroThroughThree(array[i], INCORRECT_DATE_FORMATTING);
        else if(i == 4){
          if(array[3] == '3') {
            if (array[0] == '0' && array[1] == '2')
              incorrectDateFormatting();
            else if((array[0] == '0' && (array[1] == '4' || array[1] == '6' || array[1] == '9')) || (array[0] == '1' && array[1] == '1')){
              if(array[i] != '0')
                incorrectDateFormatting();
            }
            else
              checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
          }
        }
        else
          checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
      }
      if((array[0] == '0' && array[1] == '0') || array[3] == '0' && array[4] == '0')
        incorrectDateFormatting();
    }
  }

  /**
   * Prints out the constant string indicating incorrect date format and exits
   */
  private static void incorrectDateFormatting(){
    System.err.println(INCORRECT_DATE_FORMATTING);
    System.exit(1);
  }

  /**
   * Prints out the constant string indicating incorrect time format and exits
   */
  private static void incorrectTimeFormatting(){
    System.err.println(INCORRECT_TIME_FORMATTING);
    System.exit(1);
  }

  /**
   * Prints out the error message sent in as an argument and exits program
   */
  private static void errorMessage(String message){
    System.err.println(message);
    System.exit(1);
  }

  /**
   * Checks the character to make sure it is 0 through 5. If it fails, it will print
   * the error message sent in as an argument and exits program
   */
  private static void checkZeroThroughFive(char ch, String message){
    if(ch < '0' || ch > '5')
      errorMessage(message);
  }

  /**
   * Checks the character to make sure it is 0 or 1. If it fails, it will print
   * the error message sent in as an argument and exits program
   */
  private static void checkZeroAndOne(char ch, String message){
    if(ch < '0' || ch > '1')
      errorMessage(message);
  }

  /**
   * Checks the character to make sure it is 0 through 2. If it fails, it will print
   * the error message sent in as an argument and exits program
   */
  private static void checkZeroThroughTwo(char ch, String message){
    if(ch < '0' || ch > '2')
    errorMessage(message);
  }

  /**
   * Checks the character to make sure it is 0 through 3. If it fails, it will print
   * the error message sent in as an argument and exits program
   */
  private static void checkZeroThroughThree(char ch, String message){
    if(ch < '0' || ch > '3')
      errorMessage(message);
  }

  /**
   * Checks the character to make sure it is 1 through 9. If it fails, it will print
   * the error message sent in as an argument and exits program
   */
  private static void checkOneThroughNine(char ch, String message){
    if(ch < '1' || ch > '9')
     errorMessage(message);
  }

  /**
   * Checks the character to make sure it is 0 through 9. If it fails, it will print
   * the error message sent in as an argument and exits program
   */
  private static void checkZeroThroughNine(char ch, String message){
    if(ch < '0' || ch > '9')
      errorMessage(message);
  }

  /**
   * Reads from the README.txt until there are no more lines to read. If there is no
   * file, error statement will print and will exit program
   */
  private static void displayReadMe() throws IOException {
    try (
            InputStream readme = Project2.class.getResourceAsStream("README.txt")
    ) {
      if(readme == null){
        System.err.println("README file does not exist");
        System.exit(1);
      }
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      while(line != null) {
        System.out.println(line);
        line = reader.readLine();
      }
    }
  }
}