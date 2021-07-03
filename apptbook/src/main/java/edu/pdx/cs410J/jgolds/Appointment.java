package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {
  protected String beginDate;
  protected String beginTime;
  protected String endDate;
  protected String endTime;
  protected String description;

  public Appointment(){
    this.beginDate = null;
    this.beginTime = null;
    this.endDate = null;
    this.endTime = null;
    this.description = null;
  }
  public Appointment(String beginDate, String beginTime, String endDate, String endTime, String description){
    this.beginDate = beginDate;
    this.beginTime = beginTime;
    this.endDate = endDate;
    this.endTime = endTime;
    this.description = description;
  }
  @Override
  public String getBeginTimeString() {
    return beginTime;
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getEndTimeString() {
    return endTime;
   // throw new UnsupportedOperationException("This method is not implemented yet");
  }

  @Override
  public String getDescription() {
    //return "This method is not implemented yet";
    return description;
  }
}
