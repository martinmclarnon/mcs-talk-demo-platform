package com.demo.utility

import org.apache.kafka.clients.producer.ProducerConfig
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate


@Configuration
class KafkaConsumerConfig (
    @Value("\${kafka.order.bootstrap-servers}") servers: String
){

    private var servers: String = ""

    init {
        this.servers = servers
    }

    @Bean
    fun <K, V> createOrderProducerFactory(): DefaultKafkaProducerFactory<K, V> {
        val config: MutableMap<String, Any?> = HashMap()
        config[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = servers
        config[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = "org.apache.kafka.common.serialization.StringSerializer"
        config[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = "org.springframework.kafka.support.serializer.JsonSerializer"
        return DefaultKafkaProducerFactory<K, V>(config)
    }

    @Bean
    fun <K, V> createOrderKafkaTemplate(): KafkaTemplate<K?, V?> {
        return KafkaTemplate(createOrderProducerFactory<K, V>())
    }
}