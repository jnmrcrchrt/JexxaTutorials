package io.jexxa.infrastructure.drivingadapter.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IKafkaPublishRecord
{
    void onRecord(ConsumerRecord record);
}
