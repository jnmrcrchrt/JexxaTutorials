package io.jexxa.infrastructure.drivingadapter.kafka;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import io.jexxa.infrastructure.drivingadapter.IDrivingAdapter;

public class KafkaAdapter implements  IDrivingAdapter
{

    public static final String BOOTSTRAP_SERVER_KEY = "io.jexxa.kafka.broker";
    //public static final String AUTO_COMMIT = "false";   //Publizieren des Offsets
    public static final String GROUP_ID_KEY ="io.jexxa.kafka.group";


    public static final String KEY_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String VALUE_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";

    private final List<Object> registeredConsumer = new ArrayList<>();

    private final Properties properties;
    private KafkaConsumer<String,String> consumer;



    public KafkaAdapter(final Properties properties)
    {

        //properties.put("enable.auto.commit", AUTO_COMMIT);

        properties.put("bootstrap.server","localhost:9092");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("group.id", "group");       //Wert für Client?

        this.properties = properties;

        createConsumer();


    }

    private void createConsumer()
    {
        consumer = new KafkaConsumer<>(properties);
    }


    @Override
    public void register(Object object)
    {

        //Wie dynamisch Properties zuordnen

        IKafkaPublishRecord kafkaPort = (IKafkaPublishRecord) object;
        KafkaConfiguration kafkaConfiguration  = getConfiguration(object);

        //properties.put("auto.offset.reset",kafkaConfiguration.receiveFrom());

        consumer.subscribe(Arrays.asList(kafkaConfiguration.topic()));

        Polling(kafkaPort);

        registeredConsumer.add(consumer);

    }




    public void Polling(IKafkaPublishRecord kafkaPort)
    {

        synchronized (IDrivingAdapter.acquireLock().getSynchronizationObject())
        {

            while (true)
            {

                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, String> record : records)
                {
                    kafkaPort.onRecord(record);
                }

            }
        }
    }


    @Override
    public void start()
    {
        //Starte Verbindung


    }

    @Override
    public void stop(){

        //close(); Schließe alle Consumer
        registeredConsumer.clear();
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
