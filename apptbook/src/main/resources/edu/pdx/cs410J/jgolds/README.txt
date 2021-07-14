Jared Goldsmith jgolds@pdx.edu
Project 2: Storing an Appointment book in a text file
CS510: Advanced Programming in Java
Instructor: David Whitlock


For this project, we were tasked with making an additional optional command to our appointment book program.
The additional command is -textFile file, file being the name of the text file. If this command is present,
then the program will write the appointment book created from Project 1 and add it to the text file. If the
text file already exists with an appointment book in it, then it will parse through the text file, using the
parse method from the TextParser object, which will return the appointment book within the text file. The
program will then add the new appointment created from the command line arguments to the appointment book and
will lastly add the appointment book back to the text file using the TextDumper object, specifically the dump
function which takes the appointment book as an argument. The program will return an error message if the owner
of the appointment book contained in the file is different than the owner argument coming in from the command
line. All the other error checking will occur from Program 1, as well, so the correct date and time formatting
need persist, as well as the mandatory six arguments in this order: owner, begin date of appointment, begin time
of appointment, end date of appointment, and end time of appointment.