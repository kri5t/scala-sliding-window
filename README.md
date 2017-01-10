# scala-sliding-window
A sliding window analysis of lines in a file (stream)

* Runs over a sliding window of 20 lines at a time
* Analyzes the lines from head and the next 60 seconds
* Outputs the time, price ratios, number of measurements (in current 60s), rolling sum of the window, min value and max value

I have made an assumption that the 20 lines will not exceed the number of lines in the current 60s window.