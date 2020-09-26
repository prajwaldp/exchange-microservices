package clickstreamproducer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.Map.Entry;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.KafkaProducer;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;

import java.util.concurrent.*;

public class App {
  static Properties props = new Properties();

  static {
    props.put("bootstrap.servers", "localhost:9092");
    props.put("acks", "all");
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    props.put("group.id", "test-consumer-group");
    props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
  }

  static Producer<String, String> producer = new KafkaProducer<>(props);
  static Consumer<String, String> consumer = new KafkaConsumer<>(props);
  static int streamCount = 0;

  public String getGreeting() {
    return "Starting clickstream data simulator. Check the 'clickstream' topic on Kafka";
  }

  private static void checkKafkaBrokerConnection() {
    Map<String, List<PartitionInfo>> topics = consumer.listTopics();

    if (!topics.containsKey("clickstream")) {
      System.out.println("The topic clickstream does not exist. Please create it first");
    }
  }

  private static void sendToStream(String line) {
    ProducerRecord<String, String> pr = new ProducerRecord<String, String>("clickstream",
        Integer.toString(++streamCount), line);

    try {
      producer.send(pr).get();
    } catch (InterruptedException e1) {
      System.err.println("InterruptedException: " + e1);
    } catch (ExecutionException e2) {
      System.err.println("ExecutionException: " + e2);
    }
  }

  public static void streamSimulatedClickStream() throws IOException {

    FileInputStream inputStream = null;
    Scanner sc = null;

    try {
      inputStream = new FileInputStream("./data/clickstream-enwiki-2020-08.tsv");
      sc = new Scanner(inputStream, "UTF-8");
      while (sc.hasNextLine()) {
        sendToStream(sc.nextLine());
      }
    } catch (FileNotFoundException e) {
      System.err.println("The data source file does not exist");
    } finally {

      if (inputStream != null) {
        inputStream.close();
      }

      if (sc != null) {
        sc.close();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    System.out.println(new App().getGreeting());
    checkKafkaBrokerConnection();
    consumer.close(); // Don't need it anymore

    streamSimulatedClickStream();
    producer.close();
  }
}
