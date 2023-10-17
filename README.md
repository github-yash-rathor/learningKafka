### KafkaStringProducer

KafkaStringProducer is a Java application that demonstrates how to produce string messages to an Apache Kafka topic using the Kafka client library. This README will guide you through the setup and usage of the application.

#### Prerequisites

Before using KafkaStringProducer, ensure you have the following prerequisites:

- Java: Ensure you have Java 8 or later installed on your system.
- Apache Kafka: You should have Apache Kafka set up and running. You can download and install Kafka from the official website.
- Build Tool (Gradle): You can use Gradle to build and run the application. You can install Gradle from the official website.

#### Setup

1. Clone the Repository:

   Clone this repository to your local machine.

   `git clone https://github.com/your-username/KafkaStringProducer.git`

2. Update `build.gradle`:

   Open the `build.gradle` file in the project directory and ensure that the Kafka client library version is correct. You can find the latest version of the Kafka client library on the Maven Central Repository.
```
   dependencies {
   implementation 'org.apache.kafka:kafka-clients:2.8.1' // Update the version as needed
   // Add any other dependencies you might need for your project here
   }
```


3. Build the Project:

   Build the project using Gradle:

   `gradle build`

   This command will download the required dependencies and build the project.

#### Usage

To use KafkaStringProducer to send string messages to a Kafka topic, follow these steps:

1. Run the Producer:

   You can run the producer by executing the following command:

   `gradle run`

   This will execute the KafkaStringProducer class, which sends a sample string message to the Kafka topic.

2. Customize the Producer:

   To send your own string messages, you can modify the KafkaStringProducer class in your Java code. Update the Kafka producer properties, topic name, and the message you want to send.

```java

   // Define the Kafka producer properties
   Properties props = new Properties();
   props.put("bootstrap.servers", "localhost:9092"); // Kafka broker(s) address
   props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
   props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

   // Create a Kafka producer
   Producer<String, String> producer = new KafkaProducer<>(props);

   // Define the topic to which you want to send messages
   String topic = "my-topic";

   // Send a string message to the Kafka topic
   String message = "Your custom message here!";
   ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);

   producer.send(record, new Callback() {
   // Callback to handle message delivery status
   });
```

3. Close the Producer:

   The producer is automatically closed when the application finishes running. If you need to use the producer in a long-running application, make sure to close it explicitly when you're done:

   `producer.close();`


#### ReadFromKafkaTopicAndSplit

`ReadFromKafkaTopicAndSplit` is a Java application that demonstrates how to read messages from a Kafka topic, split them into different branches based on specific criteria, and send them to different output topics. This example uses the Apache Kafka Streams library to perform these operations.


#### Usage
`ReadFromKafkaTopicAndSplit` reads messages from a Kafka topic named `input-topic`. It splits these messages into different branches based on specific criteria and sends them to different output topics: `V, V2, and VALL`.

