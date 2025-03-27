package com.example.expressfood.shared;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessagesEnum {

    PRODUCT_ADDED_CART_SUCCESSFULLY("The product has been added successfully to your cart"),
    PRODUCT_DELETED_SUCCESSFULLY("The product has been deleted successfully"),
    PRODUCT_QTY_CHANGED_SUCCESSFULLY("The product quantity has been changed successfully"),
    PRODUCT_AVAILABILITY_CHANGED_SUCCESSFULLY("The product availability has been changed successfully"),
    CART_CLEANED_SUCCESSFULLY("The cart has been cleaned successfully"),
    CATEGORY_DELETED_SUCCESSFULLY("The category has been deleted successfully"),
    ORDER_STATUS_CHANGED_SUCCESSFULLY("The order status has been updated successfully"),
    ORDER_AFFECTED_COOK_SUCCESSFULLY("The order has been affected to cook successfully"),
    ORDER_AFFECTED_DELIVERY_SUCCESSFULLY("The order has been affected to the delivery person successfully"),
    UNITE_DELETED_SUCCESSFULLY("The unit has been deleted successfully"),
    USER_PSW_CHANGED_SUCCESSFULLY("The user password has been changed successfully"),
    USER_ENABLED_SUCCESSFULLY("The user has been enabled successfully"),
    USER_DISABLED_SUCCESSFULLY("The user has been disabled successfully");


    private final String message;

    public String getMessage() {
        return message;
    }
}
