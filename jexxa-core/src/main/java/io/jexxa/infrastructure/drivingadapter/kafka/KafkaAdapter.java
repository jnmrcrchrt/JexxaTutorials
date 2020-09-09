package io.jexxa.infrastructure.drivingadapter.kafka;


import java.util.Properties;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import io.jexxa.infrastructure.drivingadapter.IDrivingAdapter;

public class KafkaAdapter implements  IDrivingAdapter
{

    public static final String BOOSTRAP_SERVER = "localhost:9092";
    public static final String AUTO_COMMIT = "false";



    private final Properties properties;

    public KafkaAdapter(final Properties properties)
    {
        this.properties = properties;

    }


    @Override
    public void register(Object object)
    {

        KafkaConsumer kafkaConsumer = (KafkaConsumer) (object);

    }

    @Override
    public void start()
    {
        

    }

    @Override
    public void stop()
    {

    }

}
