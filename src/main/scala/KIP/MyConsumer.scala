package nD

import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.{DataFrame, Dataset, SparkSession}


object MyConsumer extends App {
  val spark = SparkSession
    .builder
    .appName("SparkKafkaPOC")
    .master("local")
    .getOrCreate()

  spark.sparkContext.setLogLevel("ERROR")
  import spark.implicits._
  val df: DataFrame = spark
    .readStream
    .format("kafka")
    .option("kafka.bootstrap.servers", "localhost:9092")
    .option("subscribe", "mytopic")
      .option("startoffset" ,"earliest")
    .load()

  df.writeStream.outputMode("append")
    .format("console")
    .start()

  val data: Dataset[(String, String)] = df.selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)").as[(String, String)]

  val query: StreamingQuery = data.writeStream
    .outputMode("append")
    .format("console")
    .start()
  query.awaitTermination()
}
