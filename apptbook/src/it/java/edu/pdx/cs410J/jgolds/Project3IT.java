package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Integration tests for the {@link Project3} main class.
 */


class Project3IT extends InvokeMainTestCase {
  /**
   * Invokes the main method of {@link Project3} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain(Project3.class, args);
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain(Project3.class);
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void invokingMainWithNoArguments() {
    MainMethodResult result = invokeMain(Project3.class);
    assertThat(result.getExitCode(), equalTo(1));
  }
  @Test
  void invokingWithTooManyArgs() {
    MainMethodResult result = invokeMain(Project3.class, "Buck", "Too many args", "3/13/2020", "3:13", "am", "03/13/2021", "4:13", "pm", "pm");
    assertThat(result.getTextWrittenToStandardError(), containsString("There are too many arguments!"));
  }
  @Test
  void missingDescription() {
    MainMethodResult result = invokeMain("Buck");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingDescriptionWithOtherArgs() {
    MainMethodResult result = invokeMain(Project3.class, "Buck", "3/13/2020", "3:13", "03/13/2021", "4:13");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.DESCRIPTION_ARGUMENT_IS_MISSING_FROM_COMMAND_LINE));
  }

  @Test
  void missingBeginDate() {
    MainMethodResult result = invokeMain(Project3.class, "Buck", "Fancy meet");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingBeginTime() {
    MainMethodResult result = invokeMain(Project3.class, "Buck", "Fancy meet", "5/22/2020");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingEndDate() {
    MainMethodResult result = invokeMain(Project3.class, "Buck", "Fancy meet", "05/13/2000", "12:22", "am");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingEndTime() {
    MainMethodResult result = invokeMain(Project3.class, "Buck", "Fancy meet", "5/22/2020", "3:22");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.NOT_ENOUGH_ARGS));
  }

  @Test
  void wrongDateFormat() {
    MainMethodResult result = invokeMain(Project3.class, "Buck", "Fancy meet", "3/333/22", "3:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.INCORRECT_DATE_FORMATTING));
  }

  @Test
  void wrongTimeFormat() {
    MainMethodResult result = invokeMain(Project3.class, "Buck", "Fancy meet", "4/20/2020", "33:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project3.INCORRECT_TIME_FORMATTING));
  }

  private AppointmentBook getApptBook() {
    Appointment appt = new Appointment();
    appt.description = "Computer Science Discipline";
    appt.beginTime = "12/30/2020 3:45 pm";
    appt.endTime = "12/30/2020 4:45 pm";
    AppointmentBook apptBook = new AppointmentBook("Bill");
    apptBook.addAppointment(appt);
    return apptBook;
  }

  @Test
  void wrongFileFormat() {
    MainMethodResult result = invokeMain(Project3.class, "-textFile", "lxt.t", "Buck", "Fancy meet", "4/20/2020", "3:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString("This is not a text file"));
  }

  @Test
  void wrongOwnerShouldFail() {
    try {
      AppointmentBook apptBook = getApptBook();
      BufferedWriter bw = new BufferedWriter(new FileWriter("pzmz.txt"));
      TextDumper dump = new TextDumper("pzmz.txt");
      dump.dump(apptBook);

      //bw.write(apptBook.owner + "\n");
      bw.close();
      MainMethodResult result = invokeMain(Project3.class, "-textFile", "pzmz.txt", "Buck", "Fancy meet", "4/20/2020", "3:33", "am", "5/5/2020", "4:44", "pm");
      assertThat(result.getTextWrittenToStandardError(), containsString("The owner of the appointment book is not the same as the one on file."));
    } catch (IOException e) {
      System.err.println("The file you were looking for does not exist");
      System.exit(1);
    }
  }

  @Test
  void parseShouldWorkWithCorrectFormat() throws ParserException {
    AppointmentBook apptBook = getApptBook();
    try {
      String fileName = "pcheb.txt";
      AppointmentBook appt2 = new AppointmentBook();
      BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
      TextDumper dump = new TextDumper(fileName);
      dump.dump(apptBook);
      TextParser parser = new TextParser(fileName);
      try {
        ParserException ex = new ParserException("Can't parse");
        appt2 = parser.parse();
        if(appt2.owner == null)
          throw ex;
      } catch (ParserException ex) {
        System.err.println("Cannot parse");
        System.exit(1);
      }


      assertThat(apptBook.owner, equalTo(appt2.owner));
    } catch (IOException e) {
      System.err.println("The file you were looking for does not exist");
      System.exit(1);
    }
  }
}
