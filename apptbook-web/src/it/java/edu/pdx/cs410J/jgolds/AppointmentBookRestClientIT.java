package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AppointmentBookRestClient}
 */
@TestMethodOrder(MethodName.class)
class AppointmentBookRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AppointmentBookRestClient newAppointmentBookRestClient() {
    int port = Integer.parseInt(PORT);
    return new AppointmentBookRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllAppointmentBooks() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    client.removeAllAppointmentBooks();
  }
  @Test
  void test2CreateAppointmentBookWithOneAppointment() throws IOException, ParserException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    String owner = "Dave";
    String description = "Teach more Java";
    String startTime = "3/13/2020 3:33 pm";
    String endTime = "3/13/2020 3:33 pm";
    client.createAppointment(owner, description, startTime, endTime);

    AppointmentBook book = client.getAppointments(owner);
    assertThat(book.getOwnerName(), equalTo(owner));

    Appointment appointment = book.getAppointments().iterator().next();
    assertThat(appointment.getDescription(), equalTo(description));
  }

  @Test
  void testSearchingAppointments() throws IOException, ParserException{
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    String owner = "Dave";
    String description = "Teach more Java";
    String startTime = "3/13/2020 3:33 pm";
    String endTime = "3/13/2020 4:33 pm";
    client.createAppointment(owner, description, startTime, endTime);
    AppointmentBook book = client.getSearchedAppointments(owner, "3/12/2020 3:33 pm", "3/13/2020 4:44 pm");
    assertThat(book.appointments.get(0).getDescription(), equalTo(description));

  }

  @Test
  void test4MissingRequiredParameterReturnsPreconditionFailed() throws IOException {
    AppointmentBookRestClient client = newAppointmentBookRestClient();
    HttpRequestHelper.Response response = client.postToMyURL(Map.of());
    assertThat(response.getCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));

  }

}