package io.jexxa.KafkaApp.TemperatureService;

import io.jexxa.KafkaApp.TemperatureService.applicationservice.TemperatureService;
import io.jexxa.KafkaApp.TemperatureService.infrastructure.drivingadapter.kafka.TemperatureListener;
import io.jexxa.core.JexxaMain;
import io.jexxa.infrastructure.drivingadapter.jmx.JMXAdapter;
import io.jexxa.infrastructure.drivingadapter.kafka.KafkaAdapter;
import io.jexxa.infrastructure.drivingadapter.messaging.JMSAdapter;
import io.jexxa.infrastructure.drivingadapter.rest.RESTfulRPCAdapter;

public final class TemperatureServiceApplication
{
    //Declare the packages that should be used by Jexxa
    private static final String DRIVEN_ADAPTER      = TemperatureServiceApplication.class.getPackageName() + ".infrastructure.drivenadapter";
    private static final String OUTBOUND_PORTS            = TemperatureServiceApplication.class.getPackageName() + ".domainservice";


    public static void main(String[] args)
    {
        System.out.println(DRIVEN_ADAPTER);
        System.out.println(OUTBOUND_PORTS);

        JexxaMain jexxaMain = new JexxaMain("TemperaturService");

        jexxaMain
                //Define which outbound ports should be managed by Jexxa
                .addToApplicationCore(OUTBOUND_PORTS)

                //Define the driving adapter that should which implementation of the outbound port should be used by Jexxa.
                //Note: We must only register a single driven adapter for the outbound port
                .addToInfrastructure(DRIVEN_ADAPTER);

        //Driving Adapter
        jexxaMain.bind(KafkaAdapter.class).to(TemperatureListener.class);

        jexxaMain
                .start()

                .waitForShutdown()

                .stop();
    }




    private TemperatureServiceApplication()
    {
        //Private constructor since we only offer main
    }
}