package eu.brevissimus.payment.kafkaservice;

import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import eu.brevissimus.payment.model.dto.CardMoneyTransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static eu.brevissimus.payment.kafkaservice.TransactionTopics.ACCOUNT_TRANSACTION_FINISH;
import static eu.brevissimus.payment.kafkaservice.TransactionTopics.ACCOUNT_TRANSACTION_START;
import static eu.brevissimus.payment.kafkaservice.TransactionTopics.CARD_TRANSACTION_FINISH;
import static eu.brevissimus.payment.kafkaservice.TransactionTopics.CARD_TRANSACTION_START;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublishMessageService {

    private final KafkaTemplate<String, AccountMoneyTransferDto> kafkaTemplateTransactionDto;
    private final KafkaTemplate<String, CardMoneyTransferDto> kafkaTemplateCardTransferDto;
    private final KafkaTemplate<String, Long> kafkaTemplateLong;
    private final KafkaTemplate<String, Long> kafkaTemplateLong2; //todo

    public void sendAccountMoneyTransferStartMessage(AccountMoneyTransferDto accountMoneyTransferDto) {
        log.info("sending accountMoneyTransferDto: {}",accountMoneyTransferDto);
        kafkaTemplateTransactionDto.send(ACCOUNT_TRANSACTION_START, accountMoneyTransferDto);
        log.info("sending finished");
    }

    public void sendAccountMoneyTransferFinishMessage(Long transactionId) {
        log.info("sending id for finish topic: {}",transactionId);
        kafkaTemplateLong.send(ACCOUNT_TRANSACTION_FINISH, transactionId);
    }

    public void sendCardMoneyTransferStartMessage(CardMoneyTransferDto cardMoneyTransferDto) {
        log.info("sending cardMoneyTransferDto: {}",cardMoneyTransferDto);
        kafkaTemplateCardTransferDto.send(CARD_TRANSACTION_START, cardMoneyTransferDto);
        log.info("sending finished");
    }

    public void sendCardMoneyTransferFinishMessage(Long transactionId) {
        log.info("sending id for finish topic: {}",transactionId);
        kafkaTemplateLong.send(CARD_TRANSACTION_FINISH, transactionId);
    }
}
