package edu.pdx.cs410J.jgolds;


import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * An integration test for {@link Project4} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project4IT extends InvokeMainTestCase {
    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");
/*
    @Test
    void test0RemoveAllMappings() throws IOException {
        AppointmentBookRestClient client = new AppointmentBookRestClient(HOSTNAME, Integer.parseInt(PORT));
        client.removeAllAppointmentBooks();
    }
    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project4.class );
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString(Project4.MISSING_ARGS));
    }
    @Test
    void test3NoAppointmentBooksThrowsAppointmentBookRestException() {
        String owner = "Dave";
        try {
            invokeMain(Project4.class, HOSTNAME, PORT, owner);
            fail("Expected a RestException to be thrown");

        } catch (UncaughtExceptionInMain ex) {
            RestException cause = (RestException) ex.getCause();
            assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        }
    }

    @Test
    void test4AddAppointment() {
        String owner = "Dave";
        String description = "Still teaching Java";

        MainMethodResult result = invokeMain( Project4.class, HOSTNAME, PORT, owner, description );
        assertThat(result.getTextWrittenToStandardError(), result.getExitCode(), equalTo(0));

        result = invokeMain( Project4.class, HOSTNAME, PORT, owner );
        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(owner));
        assertThat(out, out, containsString(description));
    }

 */
@Test
void testNoCommandLineArguments() {
    MainMethodResult result = invokeMain(Project4.class);
    assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
}

    @Test
    void invokingMainWithNoArguments() {
        MainMethodResult result = invokeMain(Project4.class);
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
    }
    @Test
    void invokingWithTooManyArgs() {
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Too many args", "3/13/2020", "3:13", "am", "03/13/2021", "4:13", "pm", "pm");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("There are too many arguments!"));
    }
    @Test
    void missingDescription() {
        MainMethodResult result = invokeMain(Project4.class,"Buck", "bill", "3/3/2020");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.NOT_ENOUGH_ARGS));
    }

    @Test
    void checkDescriptionFormat(){
        MainMethodResult result = invokeMain(Project4.class,"Buck", "3/3/2020", "3/3/2020", "3:33", "am", "3/3/2020", "4:33", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));

    }


    @Test
    void missingBeginDate() {
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.NOT_ENOUGH_ARGS));
    }

    @Test
    void missingBeginTime() {
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "5/22/2020");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.NOT_ENOUGH_ARGS));
    }

    @Test
    void missingEndDate() {
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "05/13/2000", "12:22", "am");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.NOT_ENOUGH_ARGS));
    }

    @Test
    void missingEndTime() {
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "5/22/2020", "3:22");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.NOT_ENOUGH_ARGS));
    }

    @Test
    void wrongDateFormat() {
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "3/333/22", "3:33", "5/5/2020", "4:44");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.INCORRECT_DATE_FORMATTING));
    }

    @Test
    void wrongTimeFormat() {
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "4/20/2020", "33:33", "5/5/2020", "4:44");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(Project4.INCORRECT_TIME_FORMATTING));
    }

    @Test
    void correctDateFormatMonth(){
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "4/20/2020", "3:33", "am", "05/5/2020", "12:44", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(0));
    }

    @Test
    void correctDateFormatDay(){
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "4/4/2020", "3:33", "am", "05/05/2020", "12:44", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(0));
    }

    @Test
    void incorrectAmPm(){
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "4/30/2020", "3:33", "Pm", "05/05/2020", "12:44", "am");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
    }
    @Test
    void incorrectAmPmEnd(){
        MainMethodResult result = invokeMain(Project4.class, "Buck", "Fancy meet", "4/30/2020", "3:33", "am", "05/05/2020", "12:44", "Pm");
        assertThat(result.getExitCode(), CoreMatchers.equalTo(1));
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

}