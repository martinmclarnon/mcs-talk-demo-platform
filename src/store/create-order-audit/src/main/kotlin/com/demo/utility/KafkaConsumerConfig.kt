package com.demo.utility

import com.demo.dto.CreateOrderRequest
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer
import java.util.*


@EnableKafka
@Configuration("NotificationConfiguration")
class KafkaConsumerConfig (
    @Value("\${kafka.order.bootstrap-servers}") servers: String
    , @Value("\${kafka.order.consumer.group-id.notification}") groupIdNotification: String
)
{
    private var servers: String = ""
    private var groupId: String = ""

    init {
        this.servers = servers
        this.groupId = groupIdNotification
    }

    @Bean("NotificationConsumerFactory")
    fun createOrderConsumerFactory(): ConsumerFactory<String, CreateOrderRequest> {
        val props: MutableMap<String, Any?> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = servers
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[ConsumerConfig.CLIENT_ID_CONFIG] = UUID.randomUUID().toString()
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        props[ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG] = false
        return DefaultKafkaConsumerFactory(
            props, StringDeserializer(),
            JsonDeserializer(CreateOrderRequest::class.java)
        )
    }

    @Bean("NotificationContainerFactory")
    fun createOrderKafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, CreateOrderRequest> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, CreateOrderRequest>()
        factory.consumerFactory = createOrderConsumerFactory()
        factory.containerProperties.ackMode = ContainerProperties.AckMode.MANUAL_IMMEDIATE
        return factory
    }
}

