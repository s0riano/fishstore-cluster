package com.seafood.seller.service;


import com.seafood.seller.model.dto.BuyerToSellerRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;
    @Value("${seller.exchange}")
    private String sellerExchange;

    @Autowired
    public RabbitMQService(RabbitTemplate rabbitTemplate, MessageConverter messageConverter) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
    }

    public void sendBuyerCheckMessage(Object message) {
        rabbitTemplate.convertAndSend(sellerExchange, "check.buyer", message);
    }

    public void notifyBuyerServiceAboutTransferCompletion(String buyerId) {
        BuyerToSellerRequestDTO requestDTO = new BuyerToSellerRequestDTO();
        requestDTO.setSellerId(UUID.fromString(buyerId));
        log.info("Notifying Buyer Service about the transfer of buyerId: {}", requestDTO.getSellerId());
        //rabbitTemplate.setMessageConverter(messageConverter); // Convert the DTO to a message (e.g., JSON) using the injected messageConverter
        rabbitTemplate.setMessageConverter(messageConverter);
        rabbitTemplate.convertAndSend(sellerExchange, "check.buyer", requestDTO);
    }

   /* @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${seller.response.queue}", durable = "true"), // Use a property for the queue name
                    exchange = @Exchange(value = "${seller.exchange}", type = ExchangeTypes.DIRECT), // Use the same property for the exchange
                    key = "buyer.response") // Consistent routing key
    )
    public void processBuyerServiceResponseRegardingUserTransfer(BuyerToSellerResponsePayload responsePayload) {
        String sellerId = responsePayload.getSellerId();
        boolean isDeactivated = responsePayload.isBuyerIsDeactivated();
        log.info("Received buyerService response: SellerId ID = {}, Status = {}", sellerId, isDeactivated);
        //call function to update the newly made seller account to activate it
    }*/

}
