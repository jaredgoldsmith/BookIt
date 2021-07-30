package edu.pdx.cs410J.jgolds;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>AppointmentBook</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 */
public class AppointmentBookServlet extends HttpServlet
{
    static final String OWNER_PARAMETER = "owner";
    static final String DESCRIPTION_PARAMETER = "description";
    static final String START_PARAMETER = "start";
    static final String END_PARAMETER = "end";

    private final Map<String, AppointmentBook> books = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all of the entries in the dictionary
     * are written to the HTTP response.
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String owner = getParameter(OWNER_PARAMETER, request );
        String description = getParameter(DESCRIPTION_PARAMETER, request);
        String startTime = getParameter(START_PARAMETER, request);
        String endTime = getParameter(END_PARAMETER, request);
        if (owner == null) {
            missingRequiredParameter(response, OWNER_PARAMETER);
        }
        else if(description == null && startTime != null && endTime != null){
            AppointmentBook apptBook = this.books.get(owner);
            if(apptBook == null){
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            writeSearchAppointments(owner, startTime, endTime, response);
        }
        /*
        else if(startTime == null){
            missingRequiredParameter(response, START_PARAMETER);
        }
        else if(endTime == null){
            missingRequiredParameter(response, END_PARAMETER);
        }

         */
        else {
            writeAppointmentBook(owner, response);
        }
    }

    /**
     * Handles an HTTP POST request by storing the appointment entry for the
     * appointment book request parameters.  It writes the appointmentbook
     * entry to the HTTP response.
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType( "text/plain" );

        String owner = getParameter(OWNER_PARAMETER, request );
        if (owner == null) {
            missingRequiredParameter(response, OWNER_PARAMETER);
            return;
        }

        String description = getParameter(DESCRIPTION_PARAMETER, request );
        String startTime = getParameter(START_PARAMETER, request);
        String endTime = getParameter(END_PARAMETER, request);

        AppointmentBook book = this.books.get(owner);
        if (book == null) {
            book = createAppointmentBook(owner);
        }
        Appointment appointment = new Appointment(description);
        appointment.beginTime = startTime;
        appointment.setStartOfAppointment(appointment.beginTime);
        appointment.endTime = endTime;
        appointment.setEndOfAppointment(appointment.endTime);
        book.addAppointment(appointment);

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        this.books.clear();

        PrintWriter pw = response.getWriter();
        pw.println("All Appointment Book entries deleted");
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = "Missing some arguments";
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    private void writeSearchAppointments(String owner, String startTime, String endTime, HttpServletResponse response)throws IOException{
        AppointmentBook book = this.books.get(owner);
        if(book == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
        AppointmentBook copyBook = new AppointmentBook(owner);
        copyBook.appointments = book.appointments;
        PrettyPrinter printer = new PrettyPrinter(new PrintWriter(System.out));
        ArrayList<Appointment> appointments = printer.sortAppointments(copyBook.appointments);
        copyBook.appointments = appointments;
        appointments = printer.sortAppointmentsByDate(copyBook.appointments, startTime, endTime);
        copyBook.appointments = appointments;
        PrintWriter pw = response.getWriter();
        TextDumper dumper = new TextDumper(pw);
        dumper.dump(copyBook);
        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }
    private void writeAppointmentBook(String owner, HttpServletResponse response) throws IOException {
        AppointmentBook book = this.books.get(owner);

        if (book == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } else {
            PrintWriter pw = response.getWriter();
            TextDumper dumper = new TextDumper(pw);
            dumper.dump(book);
            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    AppointmentBook getAppointmentBook(String owner) {
        return this.books.get(owner);
    }

    /**
     *  Creates an appointment book and adds an owner to the book
     * @param owner
     *  Takes in the owner of the appointment book
     * @return
     *  Returns the book back to the calling routine
     */
    public AppointmentBook createAppointmentBook(String owner) {
        AppointmentBook book = new AppointmentBook(owner);
        this.books.put(owner, book);
        return book;
    }
}