package io.jexxa.KafkaApp.TemperatureService.infrastructure.drivingadapter.kafka;

import java.time.Duration;
import java.time.LocalTime;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import com.google.gson.Gson;
import io.jexxa.KafkaApp.TemperatureService.applicationservice.TemperatureService;
import io.jexxa.infrastructure.drivingadapter.kafka.KafkaConfiguration;
import io.jexxa.infrastructure.drivingadapter.messaging.JMSConfiguration;
import io.jexxa.utils.JexxaLogger;
import org.apache.kafka.clients.KafkaClient;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class TemperatureListener
{

    private final static String TOPIC = "H5_000T3001T_AAL_PV";

    private final TemperatureService temperatureService;

    public TemperatureListener(TemperatureService temperatureService)
    {
        this.temperatureService = temperatureService;
    }

    @KafkaConfiguration(topic = TOPIC, receiveFrom = "earliest")
    public void onRecord()
    {

            //Hier muss nur noch der Wert ankommen der aus der Topic gelesen wird

            // Forward this information to corresponding application service.
            temperatureService.displayPublishedTemp("Hier muss die Temp stehen");
    }
}
