package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Appointment} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class AppointmentTest {

  @Test
  void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment();
    String beginTime;
    beginTime = appointment.getBeginTimeString();
    //assertThrows(UnsupportedOperationException.class, appointment::getBeginTimeString);
  }

  @Test
  void getEndTimeStringNeedsToBeImplemented(){
    Appointment appointment = new Appointment();
    String endTime = appointment.getEndTimeString();
  }

  @Test
  void getDescriptionNeedsToBeImplemented() {
    Appointment appointment = new Appointment();
    String description = appointment.getDescription();
  }
  @Test
  void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getDescription(), is(nullValue()));

    //assertThat(appointment.getDescription(), containsString("not implemented"));
  }

  @Test
  void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getBeginTime(), is(nullValue()));
  }

}
