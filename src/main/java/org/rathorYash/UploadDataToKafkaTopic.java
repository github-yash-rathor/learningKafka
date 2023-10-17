package org.rathorYash;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class UploadDataToKafkaTopic {
    public static void main(String[] args) {
        // Define the Kafka producer properties
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092"); // Kafka broker(s) address
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // Create a Kafka producer
        Producer<String, String> producer = new KafkaProducer<>(props);

        // Define the topic to which you want to send messages
        String topic = "input-topic";
        String key = "kafka-input-topic";

        try {
            // Send a string message to the Kafka topic
            String message = "Hello";
            ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);

            producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (exception == null) {
                        System.out.println("Message sent successfully. Topic: " + metadata.topic()
                                + ", Partition: " + metadata.partition() + ", Offset: " + metadata.offset());
                    } else {
                        System.err.println("Error sending message: " + exception.getMessage());
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}

