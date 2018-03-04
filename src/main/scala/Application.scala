import org.apache.spark.{SparkConf, SparkContext}
//import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.twitter.TwitterUtils
import twitter4j.Status


/**
 * Created by pallavi on 4/3/18.
 */
object Application extends App{

  val conf = new SparkConf()
    .setMaster("local[2]")
    .setAppName("Spark-twitter")
   val sc = new SparkContext(conf)


//  val spark = SparkSession
//    .builder()
//    //.appName("SQL-example")
//    .config(conf)
//    .getOrCreate()

  import org.apache.spark.streaming._


  val ssc = new StreamingContext(sc, Seconds(5))


  //val lines = ssc.socketTextStream("localhost", 2222)

  val tweets: DStream[Status] =
    TwitterUtils.createStream(ssc, None)

//  val tweets = ssc.twitterStream()
//
//  println(tweets)

  val statuses = tweets.map(status => status.getText())
  statuses.print()

  val hashTags = tweets.flatMap(status => status.getHashtagEntities)

  val hashTagPairs = hashTags.map(hashtag => ("#" + hashtag.getText, 1))

  val topCounts10 = hashTagPairs.reduceByKeyAndWindow((l, r) => {l + r}, Seconds(10))

  val sortedTopCounts10 = topCounts10.transform(rdd =>
    rdd.sortBy(hashtagPair => hashtagPair._2, false))

  sortedTopCounts10.foreachRDD(rdd => {
    val topList = rdd.take(10)
    println("\nPopular topics in last 10 seconds (%s total):".format(rdd.count()))
    topList.foreach{case (tag, count) => println("%s (%d tweets)".format(tag, count))}
  })

  ssc.start()
  ssc.awaitTermination()



}
