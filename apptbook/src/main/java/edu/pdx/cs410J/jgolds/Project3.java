package edu.pdx.cs410J.jgolds;

import java.io.*;
import java.util.*;
import edu.pdx.cs410J.ParserException;
/**
 * The main class for Project3 for CS410 Advanced Programming in Java
 */
public class Project3 {
  public static final String INCORRECT_DATE_FORMATTING = "Incorrect date formatting";
  public static final String INCORRECT_TIME_FORMATTING = "Incorrect time formatting";
  public static final String NOT_ENOUGH_ARGS = "There are not enough arguments, there should be at least 8, " +
          "not including the -print, -README, -textFile file, or -pretty file (-) arguments";
  public static final String DESCRIPTION_ARGUMENT_IS_MISSING_FROM_COMMAND_LINE = "Description argument is missing from command line";

  /**
   * Creates a new <code>Project3</code>
   *
   * @param args
   *        1. The owner of AppointmentBook, made up of a collection of Appointments
   *        2. Description of the appointment
   *        3. Date of the start of the appointment
   *        4. Time of the start of the appointment
   *        5. Either am or pm for the starting time of the appointment
   *        6. Date of the end of the appointment
   *        7. Time of the end of the appointment
   *        8. Either am or pm for the end time of the appointment
   *
   * Optional parameters of -print, -README, -textFile file, and -pretty file (or -pretty -) can precede the other arguments.
   * -print will display the toString method from AbstractAppointment class. -README will read the contents
   * of the README.txt file and exit once complete. -textFile file will read and write the appointment book
   * into an external text file defined by the argument following textFile. -pretty file will print out the
   * appointment book in a prettier format, and the appointments will be sorted in chronological order.
   * -pretty - will display the appointment book to standard out using the prettier and sorted format used
   * for the -pretty file command.
   */

  public static void main(String[] args) throws IOException, ParserException {
    Appointment appointment = new Appointment();
    AppointmentBook appointmentBook = new AppointmentBook();
    AppointmentBook appt2 = new AppointmentBook();
    String filename = null;
    String prettyFileName = null;
    File file = null;
    boolean textFile = false;
    Project3 project = new Project3();
    String owner = null;
    String description = null;
    String beginDate = null;
    String beginTime = null;
    String beginAmPm = null;
    String endDate = null;
    String endTime = null;
    String endAmPm = null;
    boolean print = false;
    boolean isThereTextFile = false;
    boolean pretty = false;
    boolean printPretty = false;
    boolean prettyPrintFile = false;

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
        filename = arg;
        if(filename.endsWith("txt"))
          file = new File(arg);
        else{
          System.err.println("This is not a text file. The -textFile command needs to be followed by a " +
                  "text file, which should end in .txt.");
          System.exit(1);
        }
      }
      else if(arg.equals("-pretty")){
        pretty = true;
      }
      else if(pretty){
        pretty = false;
        if(arg.equals("-")){
          printPretty = true;
        }
        else{
          prettyFileName = arg;
          if(prettyFileName.endsWith("txt")){
            prettyPrintFile = true;
          }
          else{
            System.err.println("This is not a text file. The -textFile command needs to be followed by a " +
                    "text file, which should end in .txt.");
            System.exit(1);
          }
        }
      }
      else if(owner == null) {
        owner = arg;
        appointmentBook.addOwner(owner);
      }
      else if(description == null) {
        if(!project.checkDescription(arg)){
          System.err.println(DESCRIPTION_ARGUMENT_IS_MISSING_FROM_COMMAND_LINE);
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
      }
      else if(beginAmPm == null) {
        if(arg.equals("am") || arg.equals("pm")){
          beginAmPm = arg;
          appointment.addBeginTime(beginDate, beginTime, beginAmPm);
        }
        else{
          incorrectTimeFormatting();
          System.exit(1);
        }
      }
      else if(endDate == null) {
        project.parseDates(arg);
        endDate = arg;
      }
      else if(endTime == null) {
        project.parseTimes(arg);
        endTime = arg;
      }
      else if(endAmPm == null) {
        if(arg.equals("am") || arg.equals("pm")){
          endAmPm = arg;
          appointment.addEndTime(endDate, endTime, endAmPm);
          Long difference = appointment.endOfAppointment.getTime() - appointment.startOfAppointment.getTime();
          if(difference < 0){
            System.err.println("The end of the appointment cannot be before the beginning of the appointment");
            System.exit(1);
          }
              appointmentBook.addAppointment(appointment);
        }
        else{
          incorrectTimeFormatting();
          System.exit(1);
        }
      }
      else{
        System.err.println("There are too many arguments!");
        System.exit(1);
      }
    }
    if(owner == null){
      System.err.println("There aren't any command line arguments. There should be at least 8 args. Mandatory " +
              "arguments should begin with owner, then a description, then a begin date, a begin time, " +
              "am or pm for start time, an end date, an end time followed by an am or pm for end time. " +
              "There can also be a -print argument, a -README argument, " + " a -textFile argument, and " +
              "a -pretty file (or -pretty -) " +
              "to precede the mandatory arguments. The -print argument will print out a description of the " +
              "appointment, the -README argument will display the contents within the README file and exit, and " +
              "the -textFile command needs to be followed by a filename and this will either add the appointment book created" +
              " with the arguments sent in through the command line, or if the file already exists with an appointment " +
              "book, then the program will add the appointment created with the command line arguments. The -pretty file " +
              "will write the appointmentBook into a file in a prettier format with the appointments sorted in chronological order. " +
              "The -pretty - arguments will print the appointmentBook to standard out in the pretty sorted format.");
      System.exit(1);
    }
    if(endAmPm == null){
      System.err.println(NOT_ENOUGH_ARGS);
      System.exit(1);
    }
    if(isThereTextFile) {
      if (!file.exists()) {
        TextDumper textdump = new TextDumper(filename);
        textdump.dump(appointmentBook);
        if(prettyPrintFile || printPretty) {
          PrettyPrinter printer = new PrettyPrinter(prettyFileName);
          ArrayList<Appointment> appts = printer.sortAppointments(appointmentBook.appointments);
          appointmentBook.appointments = appts;
          if(prettyPrintFile)
            printer.dump(appointmentBook);
          else
            printer.prettyDisplay(appointmentBook);
        }
      } else {
        TextParser textparse = new TextParser(filename);
        try {
          appt2 = textparse.parse();
        } catch (ParserException e) {
          System.out.println("Can't parse empty file!");
          System.exit(1);

        }
        if (appt2.owner == null || !project.checkDescription(appt2.owner)) {
          TextDumper textdump = new TextDumper(filename);
          textdump.dump(appointmentBook);
          appt2 = appointmentBook;
        } else {
          if (!appt2.owner.equals(appointmentBook.owner)) {
            System.out.println("Owner is: " + appt2.owner);
            System.err.println("The owner of the appointment book is not the same as the one on file.");
            System.exit(1);
          }
          appt2.addAppointment(appointment);
          TextDumper textdump = new TextDumper(filename);
          textdump.dump(appt2);
        }
        if(prettyPrintFile || printPretty) {
          PrettyPrinter printer = new PrettyPrinter(prettyFileName);
          ArrayList<Appointment> appts = printer.sortAppointments(appt2.appointments);
          appt2.appointments = appts;
          if(prettyPrintFile)
            printer.dump(appt2);
          else
            printer.prettyDisplay(appt2);
        }
      }
    }
    if(!isThereTextFile && (prettyPrintFile || printPretty)){
        PrettyPrinter printer = new PrettyPrinter(prettyFileName);
        ArrayList<Appointment> appts = printer.sortAppointments(appointmentBook.appointments);
        appointmentBook.appointments = appts;
        if(prettyPrintFile)
          printer.dump(appointmentBook);
        else
          printer.prettyDisplay(appointmentBook);
    }
    if (print) {
      System.out.println(appointment.toString());
    }
    System.exit(1);
  }

  /**
   * Takes in a String that checks to make sure that there are letters so we know that
   * the intended argument was for the description. If there are no letters, then we
   * know that the description was likely left out of the argument list.
   */
  public boolean checkDescription(String description){
    char []descriptionArray = new char[description.length()];
    boolean doesDescriptionHaveAlphabet = false;
    for(int i = 0; i < description.length(); ++i)
      descriptionArray[i] = description.charAt(i);
    for(int i = 0; i < description.length(); ++i){
      if(Character.isLetter(descriptionArray[i])){
        doesDescriptionHaveAlphabet = true;
        break;
      }
    }
    return doesDescriptionHaveAlphabet;
  }

  /**
   * Takes in a String that should be beginTime or endTime and makes sure it is
   * the correct format, i.e. 3:33 or 11:45 would both be appropriate
   */
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
          checkOneThroughNine(array[i], INCORRECT_TIME_FORMATTING);
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
          checkZeroAndOne(array[i], INCORRECT_TIME_FORMATTING);
        else if(i == 1) {
          checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
          if(array[0] == '1')
            checkZeroThroughTwo(array[1], INCORRECT_TIME_FORMATTING);
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
            InputStream readme = Project3.class.getResourceAsStream("README.txt")
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