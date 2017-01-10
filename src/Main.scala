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

  def safeStringToInt(str: String): Option[Int] = {
    import scala.util.control.Exception._
    catching(classOf[NumberFormatException]) opt str.toInt
  }

  def safeStringToDouble(str: String): Option[Double] = {
    import scala.util.control.Exception._
    catching(classOf[NumberFormatException]) opt str.toDouble
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

  def tryParseValues (e: Array[String]): (Int, Double) = {
    val time = safeStringToInt(e(0))
    val priceRatio = safeStringToDouble(e(1))
    if(time.isDefined && priceRatio.isDefined)
      (time.get, priceRatio.get)
    else
      null
  }

  def analyzeLines(linesIterator: Iterator[String], slidingWindow: Int) = {
    printFormatted("T", "V", "N", "Rs", "MinV", "MaxV")

    linesIterator
      .map(x => x.trim.split("\\s+"))
      .map(tryParseValues)
      .filter(x => x != null)
      .sliding(20, 1)
      .foreach(x => analyzeSequence(x, slidingWindow))
  }

  def main(args: Array[String]): Unit = {
    var window = 60
    var filePath = "./data_scala.txt"

    args.sliding(2, 1).toList.collect {
      case Array("--window", argWindow: String) => window = argWindow.toInt
      case Array("-w", argWindow: String) => window = argWindow.toInt
      case Array("--filepath", argFilePath: String) => filePath = argFilePath
      case Array("-f", argFilePath: String) => filePath = argFilePath
    }

    val linesIterator = Source.fromFile(filePath)
      .getLines

    analyzeLines(linesIterator, window)
  }
}