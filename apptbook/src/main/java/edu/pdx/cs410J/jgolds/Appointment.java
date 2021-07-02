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
  public Appointment(String begin, String end, String description){
    this.beginTime = begin;
    this.endTime = end;
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
