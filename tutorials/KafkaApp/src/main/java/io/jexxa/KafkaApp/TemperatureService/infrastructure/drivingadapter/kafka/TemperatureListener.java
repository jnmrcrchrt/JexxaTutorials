package io.jexxa.KafkaApp.TemperatureService.infrastructure.drivingadapter.kafka;

import io.jexxa.KafkaApp.TemperatureService.applicationservice.TemperatureService;

public class TemperatureListener
{

    private final TemperatureService temperatureService;

    public TemperatureListener(TemperatureService temperatureService)
    {
        this.temperatureService = temperatureService;
    }
}
