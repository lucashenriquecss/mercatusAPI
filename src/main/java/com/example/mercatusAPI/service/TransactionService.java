package com.example.mercatusAPI.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.mercatusAPI.entitty.auction.Auction;
import com.example.mercatusAPI.entitty.inventory.ItemInventory;
import com.example.mercatusAPI.entitty.item.Item;
import com.example.mercatusAPI.entitty.transaction.Transaction;
import com.example.mercatusAPI.entitty.transaction.TransactionType;
import com.example.mercatusAPI.entitty.user.User;
import com.example.mercatusAPI.exception.ForbiddenException;
import com.example.mercatusAPI.exception.NotFoundException;
import com.example.mercatusAPI.repository.ItemInventoryRepository;
import com.example.mercatusAPI.repository.ItemRepository;
import com.example.mercatusAPI.repository.TransactionRepository;
import com.example.mercatusAPI.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ItemInventoryRepository itemInventoryRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository,ItemInventoryRepository itemInventoryRepository,ItemRepository itemRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.itemInventoryRepository = itemInventoryRepository;
    }


    @Transactional
    public void itemPurchase(User user, String itemId, int quantity){

        Optional<Item> item = itemRepository.findById(itemId);
        
        if (user.getBalance().compareTo(item.get().getAmount()) < 0) {
            throw new ForbiddenException("Saldo insuficiente para comprar o Item.");
        }

        
        Transaction transaction = transactionRepository.save(
            Transaction.builder()
            .type(TransactionType.ITEM_PURCHASE)
            .description("Buy Item")
            .amount(item.get().getAmount())
            .oldBalanceUser(user.getBalance())
            .newBalanceUser(user.getBalance().subtract(item.get().getAmount()))
            .user(user)
            .build()
        );

        user.setBalance(user.getBalance().subtract(item.get().getAmount()));

        userRepository.save(user);

        Optional<ItemInventory> existingItem =  itemInventoryRepository.findByInventoryAndItem(item.get(), user.getInventory());

        if(existingItem.isPresent()){
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
            itemInventoryRepository.save(existingItem.get());
            return;
        }

        itemInventoryRepository.save(
            ItemInventory.builder()
            .item(item.get())
            .inventory(user.getInventory())
            .quantity(quantity)
            .build()
        );

    }

    @Transactional
    public void itemTransfer(User senderUser, User receiverUser, String itemId, int quantity, Auction auction) {
        Item item = itemRepository.findById(itemId)
            .orElseThrow(() -> new NotFoundException("Item não encontrado."));

        BigDecimal totalAmount = item.getAmount().multiply(BigDecimal.valueOf(quantity));
        if (receiverUser.getBalance().compareTo(totalAmount) < 0) {
            throw new ForbiddenException("Saldo insuficiente para comprar o item.");
        }

        createTransaction(receiverUser, item, auction, totalAmount.negate(), "Item transfer - debit");

        createTransaction(senderUser, item, auction, totalAmount, "Item transfer - credit");

        receiverUser.setBalance(receiverUser.getBalance().subtract(totalAmount));
        senderUser.setBalance(senderUser.getBalance().add(totalAmount));

        userRepository.save(receiverUser);
        userRepository.save(senderUser);

        adjustInventory(senderUser, receiverUser, item, quantity);
    }

    private void createTransaction(User user, Item item, Auction auction, BigDecimal amount, String description) {
        transactionRepository.save(
            Transaction.builder()
                .type(TransactionType.ITEM_TRANSFER)
                .description(description)
                .amount(amount)
                .oldBalanceUser(user.getBalance())
                .newBalanceUser(user.getBalance().add(amount))
                .user(user)
                .item(item)
                .auction(auction)
                .build()
        );
    }

    private void adjustInventory(User senderUser, User receiverUser, Item item, int quantity) {
        ItemInventory senderInventory = itemInventoryRepository
            .findByInventoryAndItem(item, senderUser.getInventory())
            .orElseThrow(() -> new NotFoundException("Item não encontrado no inventário do remetente."));
        
        if (senderInventory.getQuantity() < quantity) {
            throw new ForbiddenException("Quantidade insuficiente no inventário do remetente.");
        }
        senderInventory.setQuantity(senderInventory.getQuantity() - quantity);
        itemInventoryRepository.save(senderInventory);

        ItemInventory receiverInventory = itemInventoryRepository
            .findByInventoryAndItem(item, receiverUser.getInventory())
            .orElseGet(() -> ItemInventory.builder()
                .item(item)
                .inventory(receiverUser.getInventory())
                .quantity(0)
                .build());

        receiverInventory.setQuantity(receiverInventory.getQuantity() + quantity);
        itemInventoryRepository.save(receiverInventory);
    }

}
