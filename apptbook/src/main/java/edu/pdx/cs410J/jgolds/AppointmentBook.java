package edu.pdx.cs410J.jgolds;

import java.util.Collection;
import java.util.*;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AbstractAppointment;

public class AppointmentBook extends AbstractAppointmentBook<AbstractAppointment>{
   protected String owner;
   protected Collection<AbstractAppointment> appointments = new ArrayList<>();

   public AppointmentBook (){
      this.owner = null;
   }
   public AppointmentBook (String owner){
      this.owner = owner;
   }
   public String getOwnerName(){
      return owner;
   }

   public Collection<AbstractAppointment> getAppointments(){
      return appointments;
   }

   public void addAppointment(AbstractAppointment appointment){
      appointments.add(appointment);
   }
}
