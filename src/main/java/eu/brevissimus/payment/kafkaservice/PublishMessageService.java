package eu.brevissimus.payment.kafkaservice;

import eu.brevissimus.payment.model.dto.AccountMoneyTransferDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PublishMessageService {

    private final KafkaTemplate<String, AccountMoneyTransferDto> kafkaTemplateTransactionDto;
    private final KafkaTemplate<String, Long> kafkaTemplateLong;

    public void sendTransactionStartMessage(AccountMoneyTransferDto accountMoneyTransferDto) {
        log.info("sending accountMoneyTransferDto: {}",accountMoneyTransferDto);
        kafkaTemplateTransactionDto.send(TransactionTopics.TRANSACTION_START, accountMoneyTransferDto);
        log.info("sending finished");
    }

    public void sendTransactionFinishMessage(Long transactionId) {
        log.info("sending id for finish topic: {}",transactionId);
        kafkaTemplateLong.send(TransactionTopics.TRANSACTION_FINISH, transactionId);
    }
}
