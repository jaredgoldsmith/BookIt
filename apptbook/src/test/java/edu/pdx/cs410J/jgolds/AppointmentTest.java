package edu.pdx.cs410J.jgolds;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.text.*;
//import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Unit tests for the {@link Appointment} class.
 *
 * You'll need to update these unit tests as you build out your program.
 */
public class AppointmentTest {

  @Test
  void getDateDate(){
    Appointment appt = new Appointment();
    appt.addBeginTime("3/3/2020", "4:44", "am");
    Date somedate = appt.getBeginTime();
    System.out.println(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(somedate));
  }

  @Test
  void getBeginTimeStringNeedsToBeImplemented() {
    Appointment appointment = new Appointment();
    appointment.addBeginTime("3/12/2020", "3:33", "am");
    String beginTime = "3/12/20, 3:33 AM";
    assertThat(appointment.getBeginTimeString(), equalTo(beginTime));
  }

  @Test
  void getEndTimeStringNeedsToBeImplemented(){
    Appointment appointment = new Appointment();
    appointment.addEndTime("3/12/2020", "4:33", "am");
    String endTime = "3/12/20, 4:33 am";
    //assertThat(appointment.getEndTimeString(), equalTo(endTime));
  }

  @Test
  void getDescriptionNeedsToBeImplemented() {
    Appointment appointment = new Appointment();
    appointment.addDescription("Fancy meeting for fancy folks");
    String description = "Fancy meeting for fancy folks";
    assertThat(appointment.getDescription(), equalTo(description));
  }

  @Test
  void initiallyAllAppointmentsHaveTheSameDescription() {
    Appointment appointment = new Appointment();
    assertThat(appointment.getDescription(), is(nullValue()));
  }

  //@Test
  //void forProject1ItIsOkayIfGetBeginTimeReturnsNull() {
  //  Appointment appointment = new Appointment();
  //  assertThat(appointment.getBeginTime(), is(nullValue()));
  //}

  private Appointment getMeet() {
    Appointment appointment = new Appointment();
    appointment.addDescription("Fancy meeting for fancy folks");
    appointment.addBeginTime("7/4/2021", "4:11", "am");
    appointment.addEndTime("7/4/2021", "5:11", "am");
    return appointment;

  }

  @Test
  void appointmentStringTesting(){
    Appointment appointment = new Appointment();
    appointment.addDescription("Fancy meeting for fancy folks");
    appointment.addBeginTime("7/4/2021", "4:11", "am");
    appointment.addEndTime("7/4/2021", "5:11", "am");
    assertThat(appointment.toString(), equalTo("Fancy meeting for fancy folks from 7/4/21, 4:11 AM until 7/4/21, 5:11 AM"));
  }

  @Test
  void toStringContainsDescription() {
    Appointment appointment = getMeet();
    assertThat(appointment.toString(), containsString("Fancy meeting for fancy folks"));
  }
  @Test
  void toStringContainsBeginDate() {
    Appointment appointment = getMeet();
    assertThat(appointment.toString(), containsString("7/4/21"));
  }

  @Test
  void toStringContainsBeginTime() {
    Appointment appointment = getMeet();
    assertThat(appointment.toString(), containsString("4:11"));
  }

  @Test
  void toStringContainsEndDate() {
    Appointment appointment = getMeet();
    assertThat(appointment.toString(), containsString("7/4/21"));
  }

  @Test
  void toStringContainsEndTime() {
    Appointment appointment = getMeet();
    assertThat(appointment.toString(), containsString("5:11"));
  }

}
