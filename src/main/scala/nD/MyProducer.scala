package nD


import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}


object MyProducer extends App{
  val topic = "mytopic"

  private val props = new Properties()

  props.put("bootstrap.servers", "localhost:9092")
  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

  val producer = new KafkaProducer[String,String](props)
  try {
    producer.send(new ProducerRecord[String, String](topic, "title","Spark-Kafka-POC"))
    println("Message sent successfully")
    producer.close()
  }
  catch {
    case ex: Exception =>
      ex.printStackTrace()
  }
}
