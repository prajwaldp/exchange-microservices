package datagen;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaAbstraction {
    static KafkaProducer<String, String> producer;
    static Properties props;

    static {
        props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    }

    static void startProducer() {
        producer = new KafkaProducer<>(props);        
    }

    static void closeProducer() {
        producer.close();
    }

    public static void send(String key, String value) {
        producer.send(new ProducerRecord<String, String>("orders", key, value));
    }
}
