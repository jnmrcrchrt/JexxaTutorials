package io.jexxa.KafkaApp.TemperatureService.applicationservice;

import io.jexxa.KafkaApp.TemperatureService.domainservice.IMessageDisplay;
import io.jexxa.KafkaApp.TemperatureService.domainservice.ITemperatureListener;

@SuppressWarnings("unused")
public class TemperatureService
{

    private final IMessageDisplay iMessageDisplay;

    public TemperatureService(IMessageDisplay iMessageDisplay)    {

        this.iMessageDisplay = iMessageDisplay;
    }


    public void displayPublishedTemp(String Temp)
    {
        iMessageDisplay.show(Temp);
    }

}
