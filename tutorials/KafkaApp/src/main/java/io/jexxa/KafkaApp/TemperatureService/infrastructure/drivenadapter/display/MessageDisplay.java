package io.jexxa.KafkaApp.TemperatureService.infrastructure.drivenadapter.display;

import io.jexxa.KafkaApp.TemperatureService.domainservice.IMessageDisplay;
import io.jexxa.utils.JexxaLogger;

public class MessageDisplay implements IMessageDisplay
{
    @Override
    public void show(String message)
    {
        JexxaLogger.getLogger(MessageDisplay.class).info(message);
    }
}
