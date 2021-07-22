
Jared Goldsmith jgolds@pdx.edu
Project 3: Adding pretty print and working with Date objects
CS510: Advanced Programming in Java
Instructor: David Whitlock


For this project, we were tasked with making an additional optional command to our appointment book program.
The additional command is -pretty file or -pretty -, file being the name of the text file. If the -pretty file
command is present, then the program will sort the appointmentbook's appointments and print them in a pretty
format within the textfile following the -pretty command. The appointments are sorted by order of appointments,
with the appointment with the nearest start time being the first appointment in the book. If two appointments
have the same appointment start time, then the order will go for the nearest end time. If these too are equal,
then the order will go in lexigraphical order based on the description. If -pretty - is used instead, then the
same format for the -pretty file will instead be printed to standard out.
We were also tasked with using the date object in our appointment class and to override the functions from
AbstractAppointment class. We use the new date fields and covert them into DateFormat.SHORT format for the
toString method. Also to implement the Comparable interface in the Appointment class with the compareTo function,
comparing the appointments based on the criteria outlined above.