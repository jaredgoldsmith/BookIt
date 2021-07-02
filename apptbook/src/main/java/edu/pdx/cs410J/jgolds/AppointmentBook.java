package edu.pdx.cs410J.jgolds;

import java.util.Collection;

public class AppointmentBook {
   protected String owner;
   protected Collection<Appointment> appointments;

   public AppointmentBook (){
      this.owner = null;
   }
   public AppointmentBook (String owner){
      this.owner = owner;
   }
   public String getOwnerName(){
      return owner;
   }

   public Collection<Appointment> getAppointments(){
      return appointments;
   }

   public void addAppointment(Appointment appointment){
      appointments.add(appointment);
   }
}
