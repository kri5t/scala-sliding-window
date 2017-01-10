# scala-sliding-window
A sliding window analysis of lines in a file (stream)

* Runs over a sliding window of 20 lines at a time
* Analyzes the lines from head and the next 60 seconds
* Outputs the time, price ratios, number of measurements (in current 60s), rolling sum of the window, min value and max value

I have made an assumption that the 20 lines will not exceed the number of lines in the current 60s window.

The program tries to parse the input. If it cannot parse col1 to int and col2 to double the program ignores this line.

####Compiling:

scalac ./src/Main.scala -d {outputdir}

####Run:

{outputdir}/scala Main --window 60 --filepath {path_to_file}

If no parameters are defined it defaults to:
* --window = 60
* --filepath = ./data_scala.txt