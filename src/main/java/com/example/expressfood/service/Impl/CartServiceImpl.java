package com.example.expressfood.service.Impl;

import com.example.expressfood.dao.CartItemsRepos;
import com.example.expressfood.dao.CartRepos;
import com.example.expressfood.dao.ClientRepos;
import com.example.expressfood.dao.ProductRepos;
import com.example.expressfood.dto.request.CartItemRequest;
import com.example.expressfood.dto.response.CartResponse;
import com.example.expressfood.dto.response.MessageResponse;
import com.example.expressfood.entities.Cart;
import com.example.expressfood.entities.CartItems;
import com.example.expressfood.entities.Client;
import com.example.expressfood.entities.Product;
import com.example.expressfood.exception.CartException;
import com.example.expressfood.exception.ErrorMessages;
import com.example.expressfood.exception.ProductException;
import com.example.expressfood.exception.UserException;
import com.example.expressfood.service.ICartService;
import com.example.expressfood.service.IClientService;
import com.example.expressfood.shared.MessagesEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    CartRepos cartRepos;
    @Autowired
    CartItemsRepos cartItemsRepos;
    @Autowired
    ProductRepos productRepos;

    @Autowired
    IClientService clientService;

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
                cartItems -> {
                    System.out.println("Cart item Id : "+cartItems.getCardItemId());
                    cartItemsRepos.deleteById(cartItems.getCardItemId());
                }
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
        int cartItemCount = 0;
        cartItemCount = cartRepos.countCartItemsByClient(client);
        return cartItemCount;
    }
}
