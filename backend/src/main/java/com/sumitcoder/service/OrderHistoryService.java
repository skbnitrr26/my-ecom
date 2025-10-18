package com.sumitcoder.service;

import com.sumitcoder.domain.OrderStatus;
import com.sumitcoder.dto.OrderDto;
import com.sumitcoder.dto.OrderHistory;
import com.sumitcoder.model.Order;
import com.sumitcoder.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderHistoryService {

    private final OrderRepository orderRepository;

    public OrderHistory getOrderHistory(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);

        // Convert to OrderDto (agar mapper service hai to use karo, warna simple map karo)
        List<OrderDto> orderDtos = orders.stream().map(order -> {
            OrderDto dto = new OrderDto();
            dto.setId(order.getId());

            // ✅ Agar orderId null hai to fallback use karo
            dto.setOrderId(order.getOrderId() != null ? order.getOrderId() : "ORD-" + order.getId());

            dto.setSellerId(order.getSellerId());
            dto.setOrderStatus(order.getOrderStatus());
            dto.setTotalItem(order.getTotalItem());
            dto.setTotalSellingPrice(order.getTotalSellingPrice());
            dto.setDiscount(order.getDiscount());
            dto.setOrderDate(order.getOrderDate());
            dto.setDeliverDate(order.getDeliverDate());

            // ✅ Payment status bhi set karo
            dto.setPaymentStatus(order.getPaymentStatus());

            return dto;
        }).collect(Collectors.toList());


        OrderHistory history = new OrderHistory();
        history.setId(userId);
        history.setCurrentOrders(orderDtos);
        history.setTotalOrders(orderDtos.size());
        history.setCompletedOrders((int) orderDtos.stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.DELIVERED).count());
        history.setCancelledOrders((int) orderDtos.stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.CANCELLED).count());
        history.setPendingOrders((int) orderDtos.stream()
                .filter(o -> o.getOrderStatus() == OrderStatus.PENDING).count());

        return history;
    }
}

