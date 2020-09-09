package io.jexxa.infrastructure.drivingadapter.kafka;

public @interface KafkaConfiguration
{
    String topic() default "";
}
