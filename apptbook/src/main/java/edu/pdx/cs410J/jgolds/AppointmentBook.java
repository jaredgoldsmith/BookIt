package edu.pdx.cs410J.jgolds;

import java.util.Collection;
import java.util.*;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AbstractAppointment;

/**
 * Creates an AppointmentBook, which inherits AbstractAppointmentBook. Contains
 * a String to represent the owner, and a Collection of appointments.
 */
public class AppointmentBook extends AbstractAppointmentBook<AbstractAppointment>{
   protected String owner;
   protected Collection<AbstractAppointment> appointments = new ArrayList<>();
   public AppointmentBook(){
      this.owner = null;
      //this.appointments = null;
   }

   /**
    *
    * @param owner
    *    Optional constructor to take in the name of the owner when the object is being
    *    created
    */
   public AppointmentBook (String owner){
      this.owner = owner;
   }

   /**
    *
     * @return
    *    returns the owner
    */
   public String getOwnerName(){
      return owner;
   }

   /**
    *
    * @return
    *    returns the list of appointments stored in the object
    */
   public Collection<AbstractAppointment> getAppointments(){
      return appointments;
   }

   /**
    *
    * @param appointment
    *    adds an appointment to the list of appointments stored in the object
    */
   public void addAppointment(AbstractAppointment appointment){
      appointments.add(appointment);
   }

   /**
    *
     * @param owner
    *    adds the parameter to the owner field of the object
    */
   public void addOwner(String owner){
      this.owner = owner;
   }
}
