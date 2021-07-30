package edu.pdx.cs410J.jgolds;
import edu.pdx.cs410J.ParserException;

import java.io.*;
import java.util.*;

/**
 * The main class that parses the command line and communicates with the
 * Appointment Book server using REST.
 */
public class Project4 {

    public static final String MISSING_ARGS = "Missing command line arguments";
    public static final String INCORRECT_DATE_FORMATTING = "Incorrect date formatting";
    public static final String INCORRECT_TIME_FORMATTING = "Incorrect time formatting";
    public static final String NOT_ENOUGH_ARGS = "There are not enough arguments for this project and structure";
    public static final String DESCRIPTION_ARGUMENT_IS_MISSING_FROM_COMMAND_LINE = "Description argument is missing from command line";

    public static void main(String... args) throws IOException{
        String hostName = null;
        String portString = null;
        String owner = null;
        String description = null;
        String startDate = null;
        String startTime = null;
        String startAmPm = null;
        String endDate = null;
        String endTime = null;
        String endAmPm = null;
        boolean isHostArg = false;
        boolean isPortArg = false;
        boolean isSearchArg = false;
        boolean isPrintArg = false;
        int port = 0;
        Appointment appt = new Appointment();
        AppointmentBook apptBook = new AppointmentBook();
        Project4 proj = new Project4();

        for (String arg : args) {
            if(arg.equals("-README")) {
                displayReadMe();
                System.exit(0);
            }
            else if (arg.equals("-host")) {
                isHostArg = true;
            } else if (isHostArg) {
                hostName = arg;
                isHostArg = false;
            } else if (arg.equals("-port")) {
                isPortArg = true;
            } else if (isPortArg) {
                portString = arg;
                isPortArg = false;
                try {
                    port = Integer.parseInt(portString);

                } catch (NumberFormatException ex) {
                    //usage("Port \"" + portString + "\" must be an integer");
                    System.err.println("Port must be an integer");
                    //Areturn;
                    System.exit(1);
                }
            } else if (arg.equals("-search")) {
                isSearchArg = true;
            } else if (arg.equals("-print")) {
                isPrintArg = true;
            } else if (owner == null) {
                owner = arg;
                apptBook.addOwner(owner);
            } else if (!isSearchArg && description == null) {
                description = arg;
                appt.addDescription(description);
                if (!proj.checkDescription(description)) {
                    System.err.println(DESCRIPTION_ARGUMENT_IS_MISSING_FROM_COMMAND_LINE);
                    System.exit(1);
                }
            } else if (startDate == null) {
                startDate = arg;
                proj.parseDates(startDate);
            } else if (startTime == null) {
                startTime = arg;
                proj.parseTimes(startTime);
            } else if (startAmPm == null) {
                startAmPm = arg;
                if (startAmPm.equals("am") || startAmPm.equals("pm")) {
                    appt.addBeginTime(startDate, startTime, startAmPm);
                    appt.setStartOfAppointment(appt.beginTime);
                } else {
                    incorrectTimeFormatting();
                    System.exit(1);
                }
            } else if (endDate == null) {
                endDate = arg;
                proj.parseDates(endDate);
            } else if (endTime == null) {
                endTime = arg;
                proj.parseTimes(endTime);
            } else if (endAmPm == null) {
                endAmPm = arg;
                if (endAmPm.equals("am") || endAmPm.equals("pm")) {
                    appt.addEndTime(endDate, endTime, endAmPm);
                    appt.setEndOfAppointment(appt.endTime);
                    Long difference = appt.endOfAppointment.getTime() - appt.startOfAppointment.getTime();
                    if (difference < 0) {
                        System.err.println("The end of the appointment cannot be before the beginning of the appointment");
                        System.exit(1);
                    }
                    apptBook.addAppointment(appt);
                } else {
                    incorrectTimeFormatting();
                    System.exit(1);
                }
            } else {
                System.err.println("There are too many arguments!");
                System.exit(1);
                //usage("Extraneous command line argument: " + arg);
                //return;
            }
        }
        if (owner == null) {
            System.err.println("There aren't any mandatory command line arguments. Need between 5 and 13 args total. Mandatory " +
                    "arguments should begin with owner, then a description, then a begin date, a begin time, " +
                    "am or pm for start time, an end date, an end time followed by an am or pm for end time. " +
                    "There can also be a -print argument, a -README argument, a -search argument (leave out " +
                    "description argument), a -host hostname argument, and + -port portname argument. The host " +
                    "and port arguments need to be both be present if at all. If the search argument is present then " +
                    " the program will print out the appointments between the date/time arguments for that particular " +
                    "owner, if there's already an appointment book for that owner. If you include host and port arguments, " +
                    "and only have the owner argument, the program will pretty print the appointment book for the owner " +
                    "included, if an appointment book exists for that owner.");
            System.exit(1);
        }
        if ((!isSearchArg && startDate != null && endAmPm == null) || (description != null && startDate == null)) {
            System.err.println(NOT_ENOUGH_ARGS);
            System.exit(1);
        }
        if(isSearchArg && (endAmPm == null)){
            System.err.println(NOT_ENOUGH_ARGS);
            System.exit(1);
        }

        if ((hostName != null && portString == null) || (hostName == null && portString != null)) {
            System.err.println("The host and the port both need to be present or not present");
            System.exit(1);
        }
        if (hostName != null) {
            AppointmentBookRestClient client = new AppointmentBookRestClient(hostName, port);

            try {
                if (description == null && startTime == null) {
                    // Get the text of the appointment book
                    AppointmentBook book = client.getAppointments(owner);
                    if (book == null) {
                        System.err.println("There is no appointment book for this owner");
                        System.exit(1);
                    }
                    PrettyPrinter pretty = new PrettyPrinter(new OutputStreamWriter(System.out));
                    ArrayList<Appointment> appointments = pretty.sortAppointments(book.appointments);
                    book.appointments = appointments;
                    pretty.dump(book);
                } else if (description == null) {
                    if (!isSearchArg) {
                        System.err.println(MISSING_ARGS);
                        System.exit(1);
                    } else {
                        AppointmentBook book = client.getSearchedAppointments(owner, appt.beginTime, appt.endTime);
                        if (book == null) {
                            System.err.println("There is no appointment book for this owner");
                            System.exit(1);
                        }
                        PrettyPrinter pretty = new PrettyPrinter(new OutputStreamWriter(System.out));
                        ArrayList<Appointment> appointments = pretty.sortAppointments(book.appointments);
                        book.appointments = appointments;
                        appointments = pretty.sortAppointmentsByDate(book.appointments, appt.beginTime, appt.endTime);
                        book.appointments = appointments;
                        pretty.dump(book);

                    }
                } else {
                    // Create a new appointment
                    client.createAppointment(owner, description, appt.beginTime, appt.endTime);
                }

            } catch (IOException | ParserException ex) {
                error("While contacting server: " + ex);
                System.exit(1);
                return;
            }
        }
        if (isPrintArg && !isSearchArg) {
            System.out.println(appt.toString());
        }
        System.exit(0);
    }

    private static void error(String message) {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }

    public boolean checkDescription(String description) {
        char[] descriptionArray = new char[description.length()];
        boolean doesDescriptionHaveAlphabet = false;
        for (int i = 0; i < description.length(); ++i)
            descriptionArray[i] = description.charAt(i);
        for (int i = 0; i < description.length(); ++i) {
            if (Character.isLetter(descriptionArray[i])) {
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
    public void parseTimes(String time) {
        char[] array = new char[time.length()];
        for (int i = 0; i < time.length(); ++i) {
            array[i] = time.charAt(i);
        }
        if (time.length() < 4 || time.length() > 5)
            incorrectTimeFormatting();
        if (time.length() == 4) {
            for (int i = 0; i < 4; ++i) {
                if (i == 0)
                    checkOneThroughNine(array[i], INCORRECT_TIME_FORMATTING);
                else if (i == 1 && array[i] != ':')
                    incorrectTimeFormatting();
                else if (i == 2)
                    checkZeroThroughFive(array[i], INCORRECT_TIME_FORMATTING);
                else if (i == 3)
                    checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
            }
        } else {
            for (int i = 0; i < 5; ++i) {
                if (i == 0)
                    checkZeroAndOne(array[i], INCORRECT_TIME_FORMATTING);
                else if (i == 1) {
                    checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
                    if (array[0] == '1')
                        checkZeroThroughTwo(array[1], INCORRECT_TIME_FORMATTING);
                } else if (i == 2 && array[i] != ':')
                    incorrectTimeFormatting();
                else if (i == 3)
                    checkZeroThroughFive(array[i], INCORRECT_TIME_FORMATTING);
                else if (i == 4)
                    checkZeroThroughNine(array[i], INCORRECT_TIME_FORMATTING);
            }
        }
    }

    /**
     * Takes in a String that should be beginDate or endDate and makes sure it is
     * the correct format, i.e. 12/03/2020 or 05/3/2021 would both be appropriate
     */
    public void parseDates(String date) {
        if (date == null) {
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
                else if (i == 0 || i == 2) {
                    checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
                } else {
                    checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
                }
            }
        } else if (date.length() == 9) {
            if (array[4] != '/')
                incorrectDateFormatting();
            if (array[1] == '/') {
                for (int i = 0; i < 9; ++i) {
                    if (i == 0)
                        checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
                    else if (i == 1 || i == 4)
                        continue;
                    else if (i == 2)
                        checkZeroThroughThree(array[i], INCORRECT_DATE_FORMATTING);
                    else if (i == 3) {
                        if (array[2] == '3') {
                            if (array[0] == '4' || array[0] == '6' || array[0] == '9') {
                                if (array[i] != '0')
                                    incorrectDateFormatting();
                            } else if (array[0] == '2')
                                incorrectDateFormatting();
                            else
                                checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
                        }
                    } else {
                        checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
                    }
                }
            } else if (array[2] == '/') {
                for (int i = 0; i < 9; ++i) {
                    if (i == 0)
                        checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
                    else if (i == 1) {
                        if (array[0] == '0')
                            checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
                        else
                            checkZeroThroughTwo(array[i], INCORRECT_DATE_FORMATTING);
                    } else if (i == 2 || i == 4)
                        continue;
                    else if (i == 3) {
                        checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
                    } else {
                        checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
                    }
                }
            } else
                incorrectDateFormatting();
        } else {
            if (array[2] != '/' || array[5] != '/')
                incorrectDateFormatting();
            for (int i = 0; i < 10; ++i) {
                if (i == 0)
                    checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
                else if (i == 1) {
                    if (array[0] == '0')
                        checkOneThroughNine(array[i], INCORRECT_DATE_FORMATTING);
                    else
                        checkZeroThroughTwo(array[i], INCORRECT_DATE_FORMATTING);
                } else if (i == 2 || i == 5)
                    continue;
                else if (i == 3)
                    checkZeroThroughThree(array[i], INCORRECT_DATE_FORMATTING);
                else if (i == 4) {
                    if (array[3] == '3') {
                        if (array[0] == '0' && array[1] == '2')
                            incorrectDateFormatting();
                        else if ((array[0] == '0' && (array[1] == '4' || array[1] == '6' || array[1] == '9')) || (array[0] == '1' && array[1] == '1')) {
                            if (array[i] != '0')
                                incorrectDateFormatting();
                        } else
                            checkZeroAndOne(array[i], INCORRECT_DATE_FORMATTING);
                    }
                } else
                    checkZeroThroughNine(array[i], INCORRECT_DATE_FORMATTING);
            }
            if ((array[0] == '0' && array[1] == '0') || array[3] == '0' && array[4] == '0')
                incorrectDateFormatting();
        }
    }

    /**
     * Prints out the constant string indicating incorrect date format and exits
     */
    private static void incorrectDateFormatting() {
        System.err.println(INCORRECT_DATE_FORMATTING);
        System.exit(1);
    }

    /**
     * Prints out the constant string indicating incorrect time format and exits
     */
    private static void incorrectTimeFormatting() {
        System.err.println(INCORRECT_TIME_FORMATTING);
        System.exit(1);
    }

    /**
     * Prints out the error message sent in as an argument and exits program
     */
    private static void errorMessage(String message) {
        System.err.println(message);
        System.exit(1);
    }

    /**
     * Checks the character to make sure it is 0 through 5. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static void checkZeroThroughFive(char ch, String message) {
        if (ch < '0' || ch > '5')
            errorMessage(message);
    }

    /**
     * Checks the character to make sure it is 0 or 1. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static void checkZeroAndOne(char ch, String message) {
        if (ch < '0' || ch > '1')
            errorMessage(message);
    }

    /**
     * Checks the character to make sure it is 0 through 2. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static void checkZeroThroughTwo(char ch, String message) {
        if (ch < '0' || ch > '2')
            errorMessage(message);
    }

    /**
     * Checks the character to make sure it is 0 through 3. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static void checkZeroThroughThree(char ch, String message) {
        if (ch < '0' || ch > '3')
            errorMessage(message);
    }

    /**
     * Checks the character to make sure it is 1 through 9. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static void checkOneThroughNine(char ch, String message) {
        if (ch < '1' || ch > '9')
            errorMessage(message);
    }

    /**
     * Checks the character to make sure it is 0 through 9. If it fails, it will print
     * the error message sent in as an argument and exits program
     */
    private static void checkZeroThroughNine(char ch, String message) {
        if (ch < '0' || ch > '9')
            errorMessage(message);
    }
    /**
     * Reads from the README.txt until there are no more lines to read. If there is no
     * file, error statement will print and will exit program
     */
    private static void displayReadMe() throws IOException {
        try (
                InputStream readme = Project4.class.getResourceAsStream("README.txt")
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