package ru.itis.kpfu.kafkagrpcintegrationserver.config;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.CollectionUtils;
import ru.itis.kpfu.kafkagrpcintegrationserver.model.KafkaTopicInfoDTO;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zagir Dingizbaev
 */

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaConfig<K, V> {

    private final KafkaProperties kafkaProperties;
    private final ConfigurableListableBeanFactory beanFactory;

    @Bean
    public KafkaListenerContainerFactory<?> defaultSingleFactory() {
        ConcurrentKafkaListenerContainerFactory<?, ?> factory = getContainerFactory();
        factory.setBatchListener(false);
        factory.setMessageConverter(new StringJsonMessageConverter());
        return factory;
    }

    private ConcurrentKafkaListenerContainerFactory<?, ?> getContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<Long, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(new DefaultKafkaConsumerFactory<>(getConsumerConfigs(
                kafkaProperties.getKafkaServer())));
        factory.setConcurrency(kafkaProperties.getCountOfListeners());
        return factory;
    }

    private static Map<String, Object> getConsumerConfigs(String kafkaServer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 100 * 1024 * 1024);
        props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
        return props;
    }

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        // Depending on you Kafka Cluster setup you need to configure
        // additional properties!
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getKafkaServer());
        return new KafkaAdmin(configs);
    }

    @Bean
    public KafkaTemplate<K, V> kafkaTemplate() {
        return getKafkaTemplate(
                kafkaProperties.getKafkaProducerName(),
                kafkaProperties.getKafkaServer());
    }

    private static <K, V> KafkaTemplate<K, V> getKafkaTemplate(String hostName, String kafkaServer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, hostName);
        KafkaTemplate<K, V> template = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(props));
        template.setMessageConverter(new StringJsonMessageConverter());
        return template;
    }

    @PostConstruct
    public void postConstruct() {
        if (CollectionUtils.isEmpty(kafkaProperties.getTopics()))
            return;
        kafkaProperties.getTopics()
                .stream()
                .map(KafkaTopicInfoDTO::new)
                .map(x -> TopicBuilder.name(x.getName())
                        .partitions(x.getPartitions())
                        .replicas(x.getReplicas())
                        .build())
                .forEach(t -> beanFactory.registerSingleton("kafka-topic-" + t.name(), t));
    }
}
