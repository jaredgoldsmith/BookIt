package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointment;

public class Appointment extends AbstractAppointment {
  protected String beginTime;
  protected String endTime;
  protected String description;

  public Appointment(){
    this.beginTime = null;
    this.endTime = null;
    this.description = null;
  }

  public void addDescription(String description){
    this.description = description;
  }

  public void addBeginTime(String beginDate, String beginTime){
    this.beginTime = beginDate + " " + beginTime;
  }

  public void addEndTime(String endDate, String endTime){
    this.endTime = endDate + " " + endTime;
  }

  @Override
  public String getBeginTimeString() {
    return beginTime;
  }

  @Override
  public String getEndTimeString() {
    return endTime;
  }

  @Override
  public String getDescription() {
    return description;
  }
}
