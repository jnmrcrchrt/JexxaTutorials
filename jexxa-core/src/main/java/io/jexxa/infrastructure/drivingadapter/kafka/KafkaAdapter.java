package io.jexxa.infrastructure.drivingadapter.kafka;


import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


import io.jexxa.infrastructure.drivingadapter.messaging.JMSConfiguration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import io.jexxa.infrastructure.drivingadapter.IDrivingAdapter;

public class KafkaAdapter implements  IDrivingAdapter
{

    public static final String BOOSTRAP_SERVER_KEY = "io.jexxa.kafka.broker";
    //public static final String AUTO_COMMIT = "false";   //Publizieren des Offsets
    public static final String GROUP_ID_KEY ="io.jexxa.kafka.group";


    public static final String KEY_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";
    public static final String VALUE_DESERIALIZER = "org.apache.kafka.common.serialization.StringDeserializer";

    private final List<Object> registeredConsumer = new ArrayList<>();

    private final Properties properties;
    private KafkaConsumer consumer;

    public KafkaAdapter(final Properties properties)
    {

        //In Properties?

        //properties.put("enable.auto.commit", AUTO_COMMIT);

        properties.put("bootstrap.server", BOOSTRAP_SERVER_KEY);
        properties.put("key.deserializer", KEY_DESERIALIZER);
        properties.put("value.deserializer", VALUE_DESERIALIZER);
        properties.put("group.id", GROUP_ID_KEY);       //Wert für Client?

        this.properties = properties;

        createConsumer();

    }

    private void createConsumer()
    {
        consumer = new KafkaConsumer(properties);
    }


    @Override
    public void register(Object object)
    {

        //Wie dynmaisch Properties zuordnen

        consumer = (KafkaConsumer) object;
        KafkaConfiguration kafkaConfiguration  = getConfiguration(object);

        properties.put("auto.offset.reset",kafkaConfiguration.receiveFrom());

        //kafkaConsumer.subscribe(Arrays.asList(kafkaConfiguration.topic()));
        consumer.subscribe(Arrays.asList("H5_000T3001T_AAL_PV"));


        //Polling
        //onRecord(consumer);

        registeredConsumer.add(consumer);

    }



    public void onRecord(KafkaConsumer kafkaConsumer)
    {
        while(true){
            ConsumerRecords<String,String> records = kafkaConsumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String,String> record : records){
      
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
