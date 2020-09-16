package io.jexxa.infrastructure.drivingadapter.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IKafkaPublishRecord
{
    <K, V> void onRecord(ConsumerRecord<K,V> record);
}
