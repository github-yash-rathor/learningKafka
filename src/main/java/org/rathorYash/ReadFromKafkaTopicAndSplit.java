package org.rathorYash;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Branched;

public class ReadFromKafkaTopicAndSplit {
    public Topology buildTopology(Properties allProps) {
        final StreamsBuilder builder = new StreamsBuilder();
        final String inputTopic = "input-topic";

        builder.<String, String>stream(inputTopic)
                .split()
                .branch(
                        (key, appearance) -> appearance.startsWith("V"),
                        Branched.withConsumer(ks -> ks.to("V")))
                .branch(
                        (key, appearance) -> appearance.endsWith("V"),
                        Branched.withConsumer(ks -> ks.to("V2")))
                .branch(
                        (key, appearance) -> true,
                        Branched.withConsumer(ks -> ks.to("VALL")));

        return builder.build();
    }

    public static void main(String[] args) throws Exception {

        ReadFromKafkaTopicAndSplit ss = new ReadFromKafkaTopicAndSplit();
        Properties allProps = new Properties();
        allProps.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        allProps.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        allProps.put(StreamsConfig.APPLICATION_ID_CONFIG, "kafka-stream-example");
        allProps.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");


        Topology topology = ss.buildTopology(allProps);
//        ss.createTopics(allProps);

        final KafkaStreams streams = new KafkaStreams(topology, allProps);
        final CountDownLatch latch = new CountDownLatch(1);

        // Attach shutdown handler to catch Control-C.
        Runtime.getRuntime().addShutdownHook(new Thread("streams-shutdown-hook") {
            @Override
            public void run() {
                streams.close(Duration.ofSeconds(5));
                latch.countDown();
            }
        });

        try {
            streams.start();
            latch.await();
        } catch (Throwable e) {
            System.exit(1);
        }
        System.exit(0);
    }
}

