import scala.io.Source

/**
  * Created by kristian on 07/01/2017.
  */
object Main {
  def padString(toPad: String, paddedLength: Int): String = {
    if(paddedLength <= toPad.length)
      return toPad
    padString(toPad+" ", paddedLength)
  }

  def printFormatted(T: Any, V: Any, N: Any, RS: Any, MinV: Any, MaxV: Any) : Unit = {
    println(padString(T.toString, 11) + padString(V.toString, 9) + padString(N.toString, 3) + padString(RS.toString, 9) + padString(MinV.toString, 9) + padString(MaxV.toString, 9))
  }

  def toFiveDecimals(valueToRound: Double): Double = {
    "%.5f".format(valueToRound).toDouble
  }

  def analyzeSequence(sequence: Seq[(Int, Double)], timeWindow: Int): Unit = {
    val (startTime, value) = sequence.head
    val filteredSequence = sequence.filter(x => x._1 < (startTime+timeWindow)).map(x => x._2)
    val n = filteredSequence.length
    val rs = filteredSequence.sum
    val minV = filteredSequence.min
    val maxV = filteredSequence.max
    printFormatted(startTime, value, n, toFiveDecimals(rs), minV,maxV)
  }

  def analyzeLines(linesIterator: Iterator[String], slidingWindow: Int) = {
    printFormatted("T", "V", "N", "Rs", "MinV", "MaxV")

    linesIterator
      .map(x => x.trim.split("\\s+"))
      .map(e => (e(0).toInt,e(1).toDouble))
      .sliding(20, 1)
      .foreach(x => analyzeSequence(x, slidingWindow))
  }

  def main(args: Array[String]): Unit = {
    val linesIterator = Source.fromFile("./data_scala.txt")
      .getLines

    var slidingWindow = 60
    if(args.length == 1) try{
      slidingWindow = args(0).toInt
    } catch{
      case _: Throwable =>
    }

    analyzeLines(linesIterator, slidingWindow)
  }
}