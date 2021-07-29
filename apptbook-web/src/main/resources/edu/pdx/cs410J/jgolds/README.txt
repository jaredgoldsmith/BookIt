
README

Jared Goldsmith jgolds@pdx.edu
Project 4: Working with REST API and Servlet
CS510: Advanced Programming in Java
Instructor: David Whitlock


For this project, we extend the appointment book application to support an appointment book server that
provides REST-ful web services to an appointment book client. Instead of having the option of writing to
a text file, we write to our web application instead, working with HTTP-based network communication, utilizing
Jetty to communicate the command line client with the server. We have two different URLs we need to support,
one for the appointment book of a specific owner, the other an appointment book with only the appointments
with the range of dates given as arguments through the command line. If the -search argument is added, we need
pretty print the appointment book with the appointments within the range. If all arguments but the owner
argument are missing, with the addition of the -host and -port arguments, then we need to pretty print the
entire appointment book of the owner argument. In this program, multiple appointment books will be kept track
of on the server side.