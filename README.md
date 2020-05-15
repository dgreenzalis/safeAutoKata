# safeAutoKata
Kata for SafeAuto's junior developer role

To run program, locate jar file within file system (in target directory) via terminal
In the directory where the jar is located, use the command "java -jar safeAutoKata-1.0.jar"

At the main menu, users will be prompted with a list of commands that can be executed (all are case insensitive):

AddDriver - allows users to register a driver to the input file

AddTrip - Allows a trip to be added to the input file with an auto generated id#. To add, users must include:
  -Name of the driver for the trip
  -Start date and time for the trip (in MM-DD-YYYY-HH:MM format)
  -End date and time for the trip (in MM-DD-YYYY-HH:MM format)
  -Total miles traveled for the trip
  
RemoveDriver - Allows a user to remove a user and all trips tied to that user in the input file

RemoveTrip - Allows a user to remove a specific trip based off an input trip ID#

Report - Generates a report of all drivers with their total miles traveled and average speed in miles per hour

Help - Shows explanations for each commands in program

Exit - Terminates the running of the program.

