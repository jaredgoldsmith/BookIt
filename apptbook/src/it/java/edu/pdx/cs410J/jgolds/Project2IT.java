package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


/**
 * Integration tests for the {@link Project2} main class.
 */

class Project2IT extends InvokeMainTestCase {
  
  /**
   * Invokes the main method of {@link Project2} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project2.class, args );
  }
  /**
   * Tests that invoking the main method with no arguments issues an error
   */

  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain(Project2.class);
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void invokingMainWithNoArguments(){
    MainMethodResult result = invokeMain(Project2.class);
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void missingDescription() {
    MainMethodResult result = invokeMain("Buck");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project2.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingDescriptionWithOtherArgs() {
    MainMethodResult result = invokeMain(Project2.class, "Buck", "3/13/2020", "3:13", "03/13/2021", "4:13");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project2.DESCRIPTION_ARGUMENT_IS_MISSING_FROM_COMMAND_LINE));
  }

  @Test
  void missingBeginDate() {
    MainMethodResult result = invokeMain(Project2.class, "Buck", "Fancy meet");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project2.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingBeginTime() {
    MainMethodResult result = invokeMain(Project2.class, "Buck", "Fancy meet", "5/22/2020");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project2.NOT_ENOUGH_ARGS));
  }
  @Test
  void missingEndDate() {
    MainMethodResult result = invokeMain(Project2.class, "Buck", "Fancy meet", "05/13/2000", "20:22");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project2.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingEndTime() {
    MainMethodResult result = invokeMain(Project2.class, "Buck", "Fancy meet", "5/22/2020", "2:13", "4/4/2121");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project2.NOT_ENOUGH_ARGS));
  }

  @Test
  void wrongDateFormat() {
    MainMethodResult result = invokeMain(Project2.class, "Buck", "Fancy meet", "3/333/22", "3:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project2.INCORRECT_DATE_FORMATTING));
  }

  @Test
  void wrongTimeFormat() {
    MainMethodResult result = invokeMain(Project2.class, "Buck", "Fancy meet", "4/20/2020", "33:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project2.INCORRECT_TIME_FORMATTING));
  }

  @Test
  void wrongFileFormat() {
    MainMethodResult result = invokeMain(Project2.class, "-textFile", "txt.t", "Buck", "Fancy meet", "4/20/2020", "3:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString("This is not a text file"));
  }

  @Test
  void wrongOwnerShouldFail() {
    MainMethodResult result = invokeMain(Project2.class, "-textFile", "text.txt", "Buck", "Fancy meet", "4/20/2020", "3:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString("The owner of the appointment book is not the same as the one on file."));
  }

}