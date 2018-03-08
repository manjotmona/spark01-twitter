package edu.knoldus

import java.sql.Connection

import org.apache.log4j.Logger
import org.apache.spark.streaming.Seconds
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import twitter4j.Status


/**
 * Created by pallavi on 4/3/18.
 */
object Application extends App {

  val logObject = Logger.getLogger(this.getClass)
  val sparkCustomSession = new SparkStreamingSession()
  val ssc = sparkCustomSession.getSession()

  val tweets: DStream[Status] =
    TwitterUtils.createStream(ssc, None)

  val statuses = tweets.map(status => status.getText())
  statuses.print()

  val hashTags = tweets.flatMap(status => status.getHashtagEntities)

  val hashTagPairs = hashTags.map(hashtag => ("#" + hashtag.getText, 1))

  val topCounts10 = hashTagPairs.reduceByKeyAndWindow((l, r) => { l + r }, Seconds(10))

  val sortedTopCounts10 = topCounts10.transform(rdd =>
    rdd.sortBy(hashtagPair => hashtagPair._2, false))

  sortedTopCounts10.foreachRDD(rdd => {
    val topList = rdd.take(3)
    logObject.info("\n\n\n\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
    topList.foreach { case (tag, count) => logObject.info("\n\n" + "%s (%d tweets)\n\n".format(tag, count)) }
  })

  val getJDBC = new GetSQLConnection()
  val connection: Connection = getJDBC.getConnection()

  sortedTopCounts10.foreachRDD(rdd => {
    val topList = rdd.take(10)
    topList.foreach { case (tag, count)
    => val query = "INSERT INTO TwitterTable (status, count) VALUES (?,?)"
      val st = connection.prepareStatement(query)
      st.setString(1, tag)
      st.setInt(2, count)
      st.executeUpdate()
      st.close()
    }
  })


  ssc.start()
  ssc.awaitTermination()

  connection.close()

}
