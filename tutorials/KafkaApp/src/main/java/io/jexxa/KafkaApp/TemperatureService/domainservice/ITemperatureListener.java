package io.jexxa.KafkaApp.TemperatureService.domainservice;

public interface ITemperatureListener
{
    void publish(String Messwert);
}
