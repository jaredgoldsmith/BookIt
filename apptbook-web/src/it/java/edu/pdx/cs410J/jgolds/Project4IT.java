package edu.pdx.cs410J.jgolds;


import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
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
/*
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
}