package com.example.expressfood.service.impl;

import com.example.expressfood.dao.CartItemsRepos;
import com.example.expressfood.dao.CartRepos;
import com.example.expressfood.dao.ProductRepos;
import com.example.expressfood.dto.response.CartResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.entities.Cart;
import com.example.expressfood.entities.CartItems;
import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.Product;
import com.example.expressfood.exception.CartException;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.ProductException;
import com.example.expressfood.service.ICartService;
import com.example.expressfood.service.IClientService;
import com.example.expressfood.shared.MessagesEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService {
    
    private final CartRepos cartRepos;

    private final CartItemsRepos cartItemsRepos;

    private final ProductRepos productRepos;

    private final IClientService clientService;

    @Override
    public CartResponse getCartOfClient() {
        Client client = clientService.getAuthenticatedClient();
        Cart cart = cartRepos.findCardByClient(client)
                .orElseThrow(() -> new CartException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        return CartResponse.fromEntity(cart);
    }

    @Override
    public MessageResponse addItemToCart(Long productId, int qty) {
        Client client = clientService.getAuthenticatedClient();
        Cart cart = cartRepos.findCardByClient(client)
                .orElseThrow(() -> new CartException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Product product = productRepos.findById(productId)
                .orElseThrow(() -> new ProductException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        Optional<CartItems> optionalCartItems = cartItemsRepos.findByCartAndProduct(cart, product);
        CartItems cartItems = new CartItems();
        if (optionalCartItems.isPresent()) {
            cartItems.setCardItemId(optionalCartItems.get().getCardItemId());
            cartItems.setQte(qty+optionalCartItems.get().getQte());
        }else{
            cartItems.setQte(qty);
        }
        cartItems.setCart(cart);
        cartItems.setProduct(product);
        CartItems savedItem = cartItemsRepos.save(cartItems);
        if(savedItem.getCardItemId() != null)
            return new MessageResponse(MessagesEnum.PRODUCT_ADDED_CART_SUCCESSFULLY.getMessage());
        else
            throw new CartException(ErrorMessages.INTERNAL_SERVER_ERROR.getErrorMessage());
    }

    @Override
    public MessageResponse removeItemFromCart(Long itemId) {
        CartItems cartItem = cartItemsRepos.findById(itemId)
                .orElseThrow(() -> new CartException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        cartItemsRepos.delete(cartItem);
        return new MessageResponse(MessagesEnum.PRODUCT_DELETED_SUCCESSFULLY.getMessage());
    }

    @Override
    public MessageResponse changeItemQuantity(Long itemId, int qty) {
        CartItems cartItem = cartItemsRepos.findById(itemId)
                .orElseThrow(() -> new CartException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        cartItem.setQte(qty);
        cartItemsRepos.save(cartItem);
        return new MessageResponse(MessagesEnum.PRODUCT_QTY_CHANGED_SUCCESSFULLY.getMessage());
    }

    @Override
    public void cleanCart(Client client) {
        Cart cart = cartRepos.findCardByClient(client)
                .orElseThrow(() -> new CartException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        cart.getCartItems().forEach(
                cartItems ->
                    cartItemsRepos.deleteById(cartItems.getCardItemId())
        );
    }

    @Override
    public Collection<CartItems> getCartItemByClient(Client client) {
        Cart cart = cartRepos.findCardByClient(client)
                .orElseThrow(() -> new CartException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage()));
        return cartItemsRepos.findByCart(cart);
    }

    @Override
    public int getCartItemCount() {
        Client client = clientService.getAuthenticatedClient();
        return cartRepos.countCartItemsByClient(client);
    }
}
