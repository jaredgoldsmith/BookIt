Jared Goldsmith jgolds@pdx.edu
Project 2: apptbook
CS510: Advanced Programming in Java

For this project, we have made it so that an appointment object can be created using command line
arguments. An appointment consists of a description of the appointment, the beginning date and time
of the appointment, as well as the end date and time of the appointment. For this project, the default
constructor is used. We can add the description with the addDescription function, which takes in one
string and copies it to the description data member of the object. We can add the beginning date and time
with the addBeginTime function, which takes in two string arguments, the first for the begin date and
the second for the begin time. This function concatenates these two arguments and copies them to the
beginTime data member of the object. There is also an addEndTime function, which is the same as the
addBeginTime, except will copy these arguments to the endTime data member.
In this project, there is also an appointmentBook class, which contains an owner and a collection of
appointments as its data members. Although the inherited functions are implemented in this class, this
class is not used in this project.