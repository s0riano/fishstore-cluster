package com.seafood.buyer.model.service;

import com.seafood.buyer.model.components.BuyerCheckComponent;
import com.seafood.buyer.model.entity.BuyerToSellerRequestDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RabbitMQService {

    @Value("${seller.exchange}")
    private String buyerExchangeName;

    private final RabbitTemplate rabbitTemplate;
    private final BuyerCheckComponent buyerCheckComponent;
    //private final MessageConverter messageConverter;

    @Autowired
    public RabbitMQService(RabbitTemplate rabbitTemplate, BuyerCheckComponent buyerCheckComponent) {
        this.rabbitTemplate = rabbitTemplate;
        this.buyerCheckComponent = buyerCheckComponent;
    }

    @RabbitListener(queues = "${buyer.check.queue}") //sett opp messagge converter
    public void receiveMessage(BuyerToSellerRequestDTO buyerToSellerRequestDTO) {
        log.info("Received message from SellerService regarding id: {}", buyerToSellerRequestDTO.getSellerId());
        // do try with function to deactivate user
        buyerCheckComponent.modifyBuyerForTransitioningToSeller(buyerToSellerRequestDTO);
        // create responsDTO
        // send response dto back to seller service
        // BuyerToSellerResponsePayload is the object that mus be sent as a respons
    }

    /*@RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${buyer.check.queue}", durable = "true"), // Use a property for the queue name
                    exchange = @Exchange(value = "${seller.exchange}", type = ExchangeTypes.DIRECT), // Use the same property for the exchange
                    key = "check.buyer") // Consistent routing key
    )
    public void processRequestFromSellerServiceToTransferBuyer(BuyerToSellerRequestDTO responsePayload) {
        log.info("Received request on user to become Seller, ID = {}", responsePayload.getSellerId());
        //inventoryResponseComponent.updateStatusFromInventory(responsePayload);
    }*/
}

