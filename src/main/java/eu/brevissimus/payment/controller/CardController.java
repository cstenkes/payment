package eu.brevissimus.payment.controller;

import eu.brevissimus.payment.model.dto.AccountBalanceDto;
import eu.brevissimus.payment.model.dto.CardDto;
import eu.brevissimus.payment.model.dto.CardMoneyTransferDto;
import eu.brevissimus.payment.model.entity.Card;
import eu.brevissimus.payment.model.entity.Transaction;
import eu.brevissimus.payment.service.AccountService;
import eu.brevissimus.payment.service.CardService;
import eu.brevissimus.payment.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("/payment/api/v${application.version}/cards")
@RestController
@RequiredArgsConstructor
@Tag(name = "Payment by card", description = "all card related endpoints")
public class CardController {

    private final TransactionService transactionService;
    private final AccountService accountService;
    private final CardService cardService;

    @Operation(
            summary = "Get all transaction of card by card number",
            description = "Retrieves all transaction of card by card number")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Retrieving all transaction of card by card number was successful")
            })
    @GetMapping("/{cardNumber}/transactions")
    public List<Transaction> getAllTransactionsOfCard(
            @Parameter(description = "Card number", example = "12345678-12345678-12345678")
            @PathVariable String cardNumber) {
        List<Transaction> transactions = transactionService.getAllTransactionsByCardNumber(cardNumber);
        log.info("all transactions of card {} : {}", cardNumber, transactions );
        return transactions;
    }

    @Operation(
            summary = "Money transfer by card to account with the same currency",
            description = "Money transfer by card to account with the same currency")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Card to Account money transfer was successful")
            })
    @PostMapping("/transfer")
    public Transaction transferMoneyByCardToAccount(@RequestBody CardMoneyTransferDto transfer) {
        Transaction transaction = transactionService.transferCardMoney(transfer);
        log.info("money transfer transaction was done via card to account {} : {}", transfer, transaction );
        return transaction;
    }

    @Operation(
            summary = "Get balance of card by card number",
            description = "Retrieves balance of card by card number")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Balance of card by card number was successful")
            })
    @GetMapping("/{cardNumber}/balance")
    public AccountBalanceDto getBalanceOfCard(
            @Parameter(description = "Card number", example = "12345678-12345678-12345678")
            @PathVariable String cardNumber) {
        AccountBalanceDto accountBalanceDto = accountService.getBalanceOfCard(cardNumber);
        log.info("balance of card {} : {}", cardNumber, accountBalanceDto );
        return accountBalanceDto;
    }

    // trivial controller methods:

    @Operation(
            summary = "Card creation for an existing customer for a given account",
            description = "Card creation for an existing customer for a given account")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Card creation for an existing customer for a given account was successful")
            })
    @PostMapping("/")
    public Card createCard(@RequestBody CardDto cardDto) {
        Card card = cardService.createCard(cardDto);
        log.info("card created: {}", card );
        return card;
    }

    @Operation(
            summary = "Existing card modification",
            description = "Existing card modification")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Existing card modification was successful")
            })
    @PutMapping("/")
    public Card modifyCard(@RequestBody CardDto cardDto) {
        Card card = cardService.modifyCard(cardDto);
        log.info("card modified: {}", card );
        return card;
    }

    @Operation(
            summary = "Existing card deletion (only logical)",
            description = "Existing card deletion (only logical)")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Existing card deletion (only logical) was successful")
            })
    @DeleteMapping("/")
    public Card deleteCard(@RequestBody CardDto cardDto) {
        Card card = cardService.deleteCard(cardDto);
        log.info("card deleted: {}", card );
        return card;
    }
}
