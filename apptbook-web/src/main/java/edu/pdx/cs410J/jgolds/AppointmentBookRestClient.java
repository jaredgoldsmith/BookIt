package edu.pdx.cs410J.jgolds;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send dictionary entries.
 */
public class AppointmentBookRestClient extends HttpRequestHelper {
  private static final String WEB_APP = "apptbook";
  private static final String SERVLET = "appointments";

  private final String url;


  /**
   * Creates a client to the appointment book REST service running on the given host and port
   *
   * @param hostName The name of the host
   * @param port     The port
   */
  public AppointmentBookRestClient(String hostName, int port) {
    this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
  }

  /**
   * Returns the definition for the given owner
   */
  public AppointmentBook getAppointments(String owner) throws IOException, ParserException {
    Response response = get(this.url, Map.of("owner", owner));
    throwExceptionIfNotOkayHttpStatus(response);
    String text = response.getContent();
    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();
  }

  /**
   *  This function searches an already existing appointment book for appointments within
   *  the range of the dates sent through as arguments, and returns the book with those
   *  appointments.
   * @param owner
   *  Owner of appointment book
   * @param startTime
   *  Start time for searching for appointments, so appointments starting at or after this time
   * @param endTime
   *  End time for searching for appointments, so appointments ending at or before this time
   * @return
   *  Returns an appointment book with appointments in above date range
   * @throws IOException
   * @throws ParserException
   */
  public AppointmentBook getSearchedAppointments(String owner, String startTime, String endTime)throws IOException, ParserException{
    Response response = get(this.url, Map.of("owner", owner, "start", startTime, "end", endTime));
    throwExceptionIfNotOkayHttpStatus(response);
    String text = response.getContent();
    TextParser parser = new TextParser(new StringReader(text));
    return parser.parse();
  }

  /**
   *  Adds an appointment to the server from the command line
   * @param owner
   *  Owner of appointment book
   * @param description
   *  Description of the appointment being added
   * @param startTime
   *  Start time of the appointment being added
   * @param endTime
   *  End time of the appointment being added
   * @throws IOException
   */
  public void createAppointment(String owner, String description, String startTime, String endTime) throws IOException {
    Response response = postToMyURL(Map.of("owner", owner, "description", description,"start",startTime,"end",endTime));
    throwExceptionIfNotOkayHttpStatus(response);
  }

  @VisibleForTesting
  Response postToMyURL(Map<String, String> appointmentInfo) throws IOException {
    return post(this.url, appointmentInfo);
  }

  /**
   * Removes all appointment books from the server
   * @throws IOException
   */
  public void removeAllAppointmentBooks() throws IOException {
    Response response = delete(this.url, Map.of());
    throwExceptionIfNotOkayHttpStatus(response);
  }

  /**
   * The response when an exception is thrown when not valid http status
   * @param response
   *  Takes in the response object
   * @return
   *  returns the response object if everything the HTTP is alright, otherwise
   *  exits with an error
   */
  private Response throwExceptionIfNotOkayHttpStatus(Response response) {
    int code = response.getCode();
    if (code != HTTP_OK) {
      String message = response.getContent();
      System.err.println("A connection to the server has failed, likely an appointment book does not exist for this owner");
      System.exit(1);
      throw new RestException(code, message);
    }
    return response;
  }

}
