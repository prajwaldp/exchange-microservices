package clickstream

import org.apache.spark._
import org.apache.spark.streaming._

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe


class PageView(val fromPage: String, val toPage: String, val mode: String, val n: Int) extends Serializable {
  override def toString(): String = {
    "%s\t%s\t%s\t%s\n".format(fromPage, toPage, mode, n)
  }
}

object PageView extends Serializable {
  def fromString(in: String): PageView = {
    val parts = in.split("\t")
    new PageView(parts(0), parts(1), parts(2), parts(3).toInt)
  }
}

object Main extends App {

  val conf = new SparkConf().setMaster("local[2]").setAppName("ClickstreamAnalytics")
  val ssc = new StreamingContext(conf, Seconds(1))

  ssc.checkpoint(".")

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "use_a_separate_group_id_for_each_stream",
    "auto.offset.reset" -> "latest",
    "enable.auto.commit" -> (false: java.lang.Boolean)
  )

  val topics = Array("clickstream")
  val stream = KafkaUtils.createDirectStream[String, String](
    ssc,
    PreferConsistent,
    Subscribe[String, String](topics, kafkaParams)
  )

  val pageViews = stream.map(record => PageView.fromString(record.value()))

  val pageCounts = pageViews.map(view => view.toPage).countByValue()
  val slidingPageCounts = pageViews.map(view => view.toPage).countByValueAndWindow(Seconds(10), Seconds(2))

  pageCounts.print()
  slidingPageCounts.print()

  ssc.start()
  ssc.awaitTermination()
}
