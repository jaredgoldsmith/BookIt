package edu.pdx.cs410J.jgolds;

import edu.pdx.cs410J.AbstractAppointment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Appointment extends AbstractAppointment implements Comparable<Appointment>{

    public Appointment(){}
    public Appointment(String description) {
        this.description = description;
    }

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

    protected Date startOfAppointment;
    protected Date endOfAppointment;
    private static final SimpleDateFormat dateformat = new SimpleDateFormat("MM/dd/yyyy h:mm a");

    /**
     * Takes in an appointment and compares the current objects appointment. Checks to see
     * if the objects appointment starts before the parameters appointment. Returns 1 if
     * the object's appointment starts after the parameters, and -1 if not.
     * @param appointments
     *  Takes in appointments to compare to the object
     * @return
     *  Returns 1 if the object's appointment starts after the parameters, and -1 if not
     */
    public int compareTo(Appointment appointments){
        if (this.startOfAppointment.getTime() == appointments.startOfAppointment.getTime()){
            if (this.endOfAppointment.getTime() == appointments.endOfAppointment.getTime()){
                if (this.description.compareTo(appointments.description) > 0){
                    return 1;
                }
            }
            else if (this.endOfAppointment.getTime() > appointments.endOfAppointment.getTime()){
                return 1;
            }
        }
        else if (this.startOfAppointment.getTime() > appointments.startOfAppointment.getTime()){
            return 1;
        }
        return -1;
    }

    /**
     * Takes in a string of the date and converts it to a date object with a specific date pattern
     * @param start
     *  String representing the beginTime to be converted to date
     */
    public void setStartOfAppointment(String start){
        DateFormat dtf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        Date begintime = null;
        try {
            begintime = dtf.parse(start);
        } catch (ParseException e) {
            System.err.println("Incorrect start date conversion");
            System.exit(1);
        }
        if(begintime == null){
            System.err.println("Failed converting string into date");
            System.exit(1);
        }
        this.startOfAppointment = begintime;
    }

    /**
     * Takes in a string of the date and converts it to a date object, which is a field of object
     * @param end
     *  String representing the endTime to be converted to date object
     */
    public void setEndOfAppointment(String end){
        DateFormat dtf = new SimpleDateFormat("MM/dd/yyyy h:mm a");
        Date endtime = null;
        try {
            endtime = dtf.parse(end);
        } catch (ParseException e) {
            System.err.println("Incorrect end date conversion");
            System.exit(1);
        }
        if(endtime == null){
            System.err.println("Failed converting string into date");
            System.exit(1);
        }
        this.endOfAppointment = endtime;
    }

    /**
     * Returns the field date by taking the string and converting it to date object
     * @return
     *  Date object is returned after converting string to Date
     */
    @Override
    public Date getBeginTime(){
        try {
            startOfAppointment = dateformat.parse(this.beginTime);
            return startOfAppointment;
        }
        catch(ParseException e){
            System.err.println("Incorrect begin date parsing");
            System.exit(1);
        }
        startOfAppointment = new Date();
        return startOfAppointment;
    }

    /**
     * Returns the field date by taking the string and converting it to date object
     * @return
     *  Date object is returned after converting string to Date
     */
    @Override
    public Date getEndTime(){
        try {
            endOfAppointment = dateformat.parse(this.endTime);
            return endOfAppointment;
        }
        catch(ParseException e){
            System.err.println("Incorrect end date conversion");
            System.exit(1);
        }
        endOfAppointment = new Date();
        return endOfAppointment;
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
        //this.setStartOfAppointment(this.beginTime);
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
        //this.setEndOfAppointment(this.endTime);
    }

    /**
     *
     * @return
     *  beginTime is returned to the calling function
     */
    @Override
    public String getBeginTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(startOfAppointment);
    }

    /**
     *
     * @return
     *  endTime is returned to the calling function
     */

    @Override
    public String getEndTimeString() {
        return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(endOfAppointment);
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