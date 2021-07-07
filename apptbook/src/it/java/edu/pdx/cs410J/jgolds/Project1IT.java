package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Integration tests for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {

  /**
   * Invokes the main method of {@link Project1} with the given arguments.
   */
  private MainMethodResult invokeMain(String... args) {
    return invokeMain( Project1.class, args );
  }

  /**
   * Tests that invoking the main method with no arguments issues an error
   */
  @Test
  void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain(Project1.class);
    assertThat(result.getExitCode(), equalTo(1));
    //assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
  }

  @Test
  void invokingMainWithNoArguments(){
    InvokeMainTestCase.MainMethodResult result = invokeMain(Project1.class);
    assertThat(result.getExitCode(), equalTo(1));
  }

  @Test
  void missingDescription() {
    MainMethodResult result = invokeMain(Project1.class, "Buck");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.NOT_ENOUGH_ARGS));
  }
  @Test
  void missingBeginDate() {
    MainMethodResult result = invokeMain(Project1.class, "Buck", "Fancy meet");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingBeginTime() {
    MainMethodResult result = invokeMain(Project1.class, "Buck", "Fancy meet", "5/22/2020");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.NOT_ENOUGH_ARGS));
  }
  @Test
  void missingEndDate() {
    MainMethodResult result = invokeMain(Project1.class, "Buck", "Fancy meet", "05/13/2000", "20:22");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.NOT_ENOUGH_ARGS));
  }

  @Test
  void missingEndTime() {
    MainMethodResult result = invokeMain(Project1.class, "Buck", "Fancy meet", "5/22/2020", "2:13", "4/4/2121");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.NOT_ENOUGH_ARGS));
  }

  @Test
  void wrongDateFormat() {
    MainMethodResult result = invokeMain(Project1.class, "Buck", "Fancy meet", "3/333/22", "3:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.INCORRECT_DATE_FORMATTING));
  }
  @Test
  void wrongTimeFormat() {
    MainMethodResult result = invokeMain(Project1.class, "Buck", "Fancy meet", "4/20/2020", "33:33", "5/5/2020", "4:44");
    assertThat(result.getTextWrittenToStandardError(), containsString(Project1.INCORRECT_TIME_FORMATTING));
  }

}