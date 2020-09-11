package io.jexxa.KafkaApp.TemperatureService.applicationservice;

import io.jexxa.KafkaApp.TemperatureService.domainservice.IMessageDisplay;
import io.jexxa.KafkaApp.TemperatureService.domainservice.ITemperatureListener;

public class TemperatureService
{


    private final ITemperatureListener iTemperatureListener;
    private final IMessageDisplay iMessageDisplay;

    public TemperatureService(ITemperatureListener iTemperatureListener, IMessageDisplay iMessageDisplay)    {

        this.iTemperatureListener = iTemperatureListener;
        this.iMessageDisplay = iMessageDisplay;
    }


    public void displayPublishedTemp(String Temp)
    {
        iMessageDisplay.show("Hier muss die Temp stehen");
    }

}
