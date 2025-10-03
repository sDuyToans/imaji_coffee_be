package com.duytoan.imajicoffee.imaji_coffee_be.services.email;

import com.duytoan.imajicoffee.imaji_coffee_be.entities.order.Order;

public interface IMailService {
    public void sendOrderInfoToEmail(Order order);
}
