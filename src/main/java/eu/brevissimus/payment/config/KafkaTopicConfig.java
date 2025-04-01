package eu.brevissimus.payment.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static eu.brevissimus.payment.kafkaservice.TransactionTopics.ACCOUNT_TRANSACTION_START;
import static eu.brevissimus.payment.kafkaservice.TransactionTopics.ACCOUNT_TRANSACTION_FINISH;
import static eu.brevissimus.payment.kafkaservice.TransactionTopics.CARD_TRANSACTION_START;
import static eu.brevissimus.payment.kafkaservice.TransactionTopics.CARD_TRANSACTION_FINISH;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topicStart1() { // start - create record on transaction
        return new NewTopic(ACCOUNT_TRANSACTION_START, 1, (short) 1);
    }

    @Bean
    public NewTopic topicStart2() { // start - create record on transaction
        return new NewTopic(ACCOUNT_TRANSACTION_FINISH, 1, (short) 1);
    }

    @Bean
    public NewTopic topicFinish1() { // operation on transaction (update balances of accounts and update transaction table)
        return new NewTopic(CARD_TRANSACTION_START, 1, (short) 1);
    }

    @Bean
    public NewTopic topicFinish2() { // operation on transaction (update balances of accounts and update transaction table)
        return new NewTopic(CARD_TRANSACTION_FINISH, 1, (short) 1);
    }

}