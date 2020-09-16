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

public class KafkaAdapter implements IDrivingAdapter
{
    private final List<KafkaConsumer> registeredConsumer = new ArrayList<>();
    private final List<Object> publisher = new ArrayList<>();

    private final Properties properties;
    private KafkaConsumer<String, String> consumer;


    public KafkaAdapter(final Properties properties)
    {
        this.properties = properties;
        createConsumer();

    }

    private void createConsumer()
    {
        consumer = new KafkaConsumer<String, String>(properties);
    }


    @Override
    public void register(Object object)
    {

        IKafkaPublishRecord kafkaPort = (IKafkaPublishRecord) (object);
        KafkaConfiguration kafkaConfiguration = getConfiguration(object);

        //subscribe to topic
        consumer.subscribe(Arrays.asList(kafkaConfiguration.topic()));

        //registration of consumer and publisher
        registeredConsumer.add(consumer);
        publisher.add(kafkaPort);

        //polling for new records
        Polling(kafkaPort);

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
    public void stop()
    {

        //close(); Schließe alle Consumer
        consumer.close();
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
