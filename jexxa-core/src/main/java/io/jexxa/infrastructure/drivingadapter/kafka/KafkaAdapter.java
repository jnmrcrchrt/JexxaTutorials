package io.jexxa.infrastructure.drivingadapter.kafka;


import java.util.Arrays;
import java.util.Properties;


import io.jexxa.infrastructure.drivingadapter.messaging.JMSConfiguration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import io.jexxa.infrastructure.drivingadapter.IDrivingAdapter;

public class KafkaAdapter implements  IDrivingAdapter
{

    public static final String BOOSTRAP_SERVER = "localhost:9092";
    public static final String AUTO_COMMIT = "false";
    public static final String KEY_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String VALUE_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";



    private final Properties properties;

    public KafkaAdapter(final Properties properties)
    {
        this.properties = properties;

    }


    @Override
    public void register(Object object)
    {

        KafkaConsumer kafkaConsumer = (KafkaConsumer) (object);
        KafkaConfiguration kafkaConfiguration  = getConfiguration(object);



    }

    @Override
    public void start()
    {
        

    }

    @Override
    public void stop()
    {

    }


    private KafkaConfiguration getConfiguration(Object object)
    {
        return Arrays.stream(object.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(KafkaConfiguration.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Given object does not provide a " + KafkaConfiguration.class.getSimpleName()))
                .getDeclaredAnnotation(KafkaConfiguration.class);
    }

}
