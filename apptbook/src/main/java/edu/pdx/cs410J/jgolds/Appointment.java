package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointment;
import java.util.*;
import java.text.*;

/**
 * Creates an Appointment object, which inherits the AbstractAppointment class
 */
public class Appointment extends AbstractAppointment {
  /**
   * beginTime represents the date and time of the start of the appointment
   */
  protected String beginTime;
  /**
   * endTime represents the date and time of the end of the appointment
   */
  protected String endTime;
  /**
   * description represents the description of the appointment
   */
  protected String description;

  private static final SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy h:mm a");

  @Override
  public Date getBeginTime(){
    //Date begindate = new Date();
      //DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm a");
      //Date begindate = null;
    System.out.println("The begin date is: " + this.beginTime);
    try {
      Date date = dateformat.parse(this.beginTime);
      //begindate = df.parse(this.beginTime);
      //return begindate;
      return date;
    }
    catch(ParseException e){
      System.out.println("Incorrect begin date parsing");
      System.exit(1);
    }
    Date date = new Date();
    //Date begindate = new Date();
    return date;
  }

  @Override
  public Date getEndTime(){
    //Date enddate = new Date();
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy h:mm a");
    Date enddate = null;
    try {
      enddate = df.parse(this.endTime);
      return enddate;
    }
    catch(ParseException e){
      System.out.println("Incorrect end date parsing");
    }
    //Date enddate = new Date();
    return enddate;
  }

  /**
   *
   * @param description
   *  takes in a String representing the description of the appointment and stores it
   *  the description field
   */
  public void addDescription(String description){
    this.description = description;
  }

  /**
   *
   * @param beginDate
   *  represents the date of the start of the appointment
   * @param beginTime
   *  represents the time of the start of the appointment
   * These two parameters are concatenated and stored in the beginTime field
   */
  public void addBeginTime(String beginDate, String beginTime, String beginAmPm){
    this.beginTime = beginDate + " " + beginTime + " " + beginAmPm;
  }

  /**
   *
   * @param endDate
   *  represents the date of the start of the appointment
   * @param endTime
   *  represents the time of the start of the appointment
   * These two parameters are concatenated and stored in the endTime field
   */
  public void addEndTime(String endDate, String endTime, String endAmPm){
    this.endTime = endDate + " " + endTime + " " + endAmPm;
  }

  /**
   *
   * @return
   *  beginTime is returned to the calling function
   */
  @Override
  public String getBeginTimeString() {

    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(getBeginTime());
  }

  /**
   *
   * @return
   *  endTime is returned to the calling function
   */
  @Override
  public String getEndTimeString() {
    return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(getEndTime());
  }

  /**
   *
   * @return
   *  description is returned to the calling function
   */
  @Override
  public String getDescription() {
    return description;
  }
}
