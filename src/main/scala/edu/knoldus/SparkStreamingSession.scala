package edu.knoldus

import org.apache.spark.streaming.StreamingContext
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by pallavi on 8/3/18.
 */
class SparkStreamingSession {
  def getSession(): StreamingContext = {
    val conf = new SparkConf()
      .setMaster("local[2]")
      .setAppName("Spark-twitter")
    val sc = new SparkContext(conf)

    import org.apache.spark.streaming._


    val ssc = new StreamingContext(sc, Seconds(5))
    ssc

  }

}
