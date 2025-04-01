package eu.brevissimus.payment.kafkaservice;

import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import static eu.brevissimus.payment.kafkaservice.TransactionTopics.GROUP_1;

@Slf4j
@RequiredArgsConstructor
@Service
public class ConsumeMessageService {

    private final TransactionService transactionService;
    private final PublishMessageService publishMessageService;
    // https://www.practicalsoftwarearchitecture.com/blog/spring-boot-kafka-config

    @KafkaListener(topics = TransactionTopics.TRANSACTION_START, groupId = GROUP_1,containerFactory = "kafkaListenerContainerFactory2")
    public void listenTransactionStartGroup1(@Payload AccountMoneyTransferDto payload) {
        log.info("Received Payload: {}", payload);
        Transaction transaction = transactionService.transferAccountMoney1(payload);
        publishMessageService.sendTransactionFinishMessage(transaction.getId());
    }

    @KafkaListener(topics = TransactionTopics.TRANSACTION_FINISH, groupId = GROUP_1,containerFactory = "kafkaListenerContainerFactory3")
    public void listenTransactionFinishGroup1(@Payload Long transactionId) {
        log.info("Received Message in group1: " + transactionId);
        transactionService.transferAccountMoney2(transactionId);
        log.info("Transaction finished");
    }
}
