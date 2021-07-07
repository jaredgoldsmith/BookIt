package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointment;

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
  public void addBeginTime(String beginDate, String beginTime){
    this.beginTime = beginDate + " " + beginTime;
  }

  /**
   *
   * @param endDate
   *  represents the date of the start of the appointment
   * @param endTime
   *  represents the time of the start of the appointment
   * These two parameters are concatenated and stored in the endTime field
   */
  public void addEndTime(String endDate, String endTime){
    this.endTime = endDate + " " + endTime;
  }

  /**
   *
   * @return
   *  beginTime is returned to the calling function
   */
  @Override
  public String getBeginTimeString() {
    return beginTime;
  }

  /**
   *
   * @return
   *  endTime is returned to the calling function
   */
  @Override
  public String getEndTimeString() {
    return endTime;
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
