package io.jexxa.KafkaApp.TemperatureService.infrastructure.drivingadapter.kafka;

import io.jexxa.KafkaApp.TemperatureService.applicationservice.TemperatureService;
import io.jexxa.KafkaApp.TemperatureService.domainservice.ITemperatureListener;
import io.jexxa.infrastructure.drivingadapter.kafka.IKafkaPublishRecord;
import io.jexxa.infrastructure.drivingadapter.kafka.KafkaConfiguration;
import org.apache.kafka.clients.consumer.ConsumerRecord;

public class TemperatureListener implements  IKafkaPublishRecord
{

    private final static String TOPIC = "H5_000T3001T_AAL_PV";

    private final TemperatureService temperatureService;

    public TemperatureListener(TemperatureService temperatureService)
    {
        this.temperatureService = temperatureService;
    }

    @Override
    @KafkaConfiguration(topic = TOPIC)
    public void onRecord(ConsumerRecord<String,String> record)
    {
            //Hier muss nur noch der Wert ankommen der aus der Topic gelesen wird
            // Forward this information to corresponding application service.
            temperatureService.displayPublishedTemp("Value: "+ record.value().toString() + ", Key: " + record.key().toString() + ", Partition: " + record.partition());
    }

}
