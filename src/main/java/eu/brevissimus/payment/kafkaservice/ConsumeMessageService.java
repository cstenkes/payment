package eu.brevissimus.payment.kafkaservice;

import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.dto.CardMoneyTransferDto;
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

    @KafkaListener(topics = TransactionTopics.ACCOUNT_TRANSACTION_START, groupId = GROUP_1,containerFactory = "kafkaListenerContainerFactory2")
    public void listenTransactionStartGroup1(@Payload AccountMoneyTransferDto payload) {
        log.info("Received Message in group1: {}", payload);
        Transaction transaction = transactionService.transferAccountMoney1(payload);
        publishMessageService.sendAccountMoneyTransferFinishMessage(transaction.getId());
    }

    @KafkaListener(topics = TransactionTopics.ACCOUNT_TRANSACTION_FINISH, groupId = GROUP_1, containerFactory = "kafkaListenerContainerFactory3")
    public void listenTransactionFinishGroup1(@Payload Long transactionId) {
        log.info("Received Message in group1: {} ", transactionId);
        transactionService.transferMoney2(transactionId);
        log.info("Transaction finished");
    }


    @KafkaListener(topics = TransactionTopics.CARD_TRANSACTION_START, groupId = GROUP_1, containerFactory = "kafkaListenerContainerFactory4")
    public void listenTransactionStartGroup2(@Payload CardMoneyTransferDto payload) {
        log.info("Received Message in group2: {}", payload);
        Transaction transaction = transactionService.transferCardMoney1(payload);
        publishMessageService.sendCardMoneyTransferFinishMessage(transaction.getId());
    }

    @KafkaListener(topics = TransactionTopics.CARD_TRANSACTION_FINISH, groupId = GROUP_1, containerFactory = "kafkaListenerContainerFactory3")
    public void listenTransactionFinishGroup2(@Payload Long transactionId) {
        log.info("Received Message in group2: " + transactionId);
        transactionService.transferMoney2(transactionId);
        log.info("Transaction finished");
    }
}
