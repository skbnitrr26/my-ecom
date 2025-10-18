package com.sumitcoder.service;

import com.sumitcoder.model.Order;
import com.sumitcoder.model.Product;
import com.sumitcoder.repository.OrderRepository;
import com.sumitcoder.repository.ProductRepository;
import com.sumitcoder.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiChatService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public ChatResponse getResponse(String prompt, Long productId, Long userId) {
        String lowerPrompt = prompt.toLowerCase().trim();

        // --- Normalization for plurals/synonyms ---
        String normalizedPrompt = lowerPrompt
                .replace("orders", "order")
                .replace("offers", "discount")
                .replace("canceled", "cancelled")
                .replace("deliver", "delivered")
                .replace("pending", "pending");

        String response = "❓ Sorry, I could not understand your query.";

        // --- 1. Greetings ---
        if (normalizedPrompt.matches("hi|hii|hello|hey|hola")) {
            return new ChatResponse("assistant", "👋 Hello! How can I help you today?");
        }

        // --- 2. Product-specific queries ---
        if (productId != null) {
            Product product = productRepository.findById(productId).orElse(null);
            if (product != null) {
                if (normalizedPrompt.contains("color")) {
                    response = "🎨 The color of this product is: " + product.getColor();
                } else if (normalizedPrompt.contains("price")) {
                    response = "💰 Price: ₹" + product.getMrpPrice() +
                               " (Discounted: ₹" + product.getSellingPrice() + ")";
                } else if (normalizedPrompt.contains("available") || normalizedPrompt.contains("stock")) {
                    response = "📦 Available stock: " + product.getQuantity();
                } else if (normalizedPrompt.contains("discount")) {
                    response = "🏷️ Discount: " + product.getDiscountPercent() + "%";
                } else {
                    response = "❓ I could not find product details for your query.";
                }
            } else {
                response = "⚠️ No product found with ID: " + productId;
            }
            return new ChatResponse("assistant", response);
        }

        // --- 3. User-specific queries ---
        if (userId != null) {
            List<Order> orders = orderRepository.findByUserId(userId);

            if (orders.isEmpty()) {
                return new ChatResponse("assistant", "📭 You have no orders yet.");
            }

            // Keywords for intent detection
            List<String> cancelKeywords = List.of("cancel", "cancelled", "remove");
            List<String> deliverKeywords = List.of("deliver", "delivered", "completed");
            List<String> pendingKeywords = List.of("pending", "waiting", "processing");

            // Last 5 orders
            if (normalizedPrompt.contains("last order") || normalizedPrompt.contains("order status")) {
                List<Order> lastFiveOrders = orders.stream()
                        .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                        .limit(5)
                        .collect(Collectors.toList());

                StringBuilder sb = new StringBuilder("📦 Your last 5 orders:\n");
                lastFiveOrders.forEach(order -> sb.append("- Order ID: ")
                        .append(order.getOrderId())
                        .append(" | Status: ")
                        .append(order.getOrderStatus())
                        .append(" | Payment: ")
                        .append(order.getPaymentStatus())
                        .append("\n"));

                response = sb.toString();
            }
            // Total orders
            else if (normalizedPrompt.contains("how many order") || normalizedPrompt.contains("placed")) {
                response = "📝 You have placed a total of " + orders.size() + " orders.";
            }
            // Cancelled orders
            else if (cancelKeywords.stream().anyMatch(normalizedPrompt::contains)) {
                List<Order> cancelledOrders = orders.stream()
                        .filter(o -> o.getOrderStatus().name().equalsIgnoreCase("CANCELLED"))
                        .collect(Collectors.toList());
                response = cancelledOrders.isEmpty()
                        ? "❌ You have no cancelled orders."
                        : "❌ You have cancelled " + cancelledOrders.size() + " orders.";
            }
            // Delivered orders
            else if (deliverKeywords.stream().anyMatch(normalizedPrompt::contains)) {
                List<Order> deliveredOrders = orders.stream()
                        .filter(o -> o.getOrderStatus().name().equalsIgnoreCase("DELIVERED"))
                        .collect(Collectors.toList());
                response = deliveredOrders.isEmpty()
                        ? "🚚 You have no delivered orders."
                        : "🚚 You have " + deliveredOrders.size() + " delivered orders.";
            }
            // Pending orders
            else if (pendingKeywords.stream().anyMatch(normalizedPrompt::contains)) {
                List<Order> pendingOrders = orders.stream()
                        .filter(o -> o.getOrderStatus().name().equalsIgnoreCase("PENDING"))
                        .collect(Collectors.toList());
                response = pendingOrders.isEmpty()
                        ? "⏳ You have no pending orders."
                        : "⏳ You have " + pendingOrders.size() + " pending orders.";
            }
            // Fallback inside user context
            else {
                response = "❓ Sorry, I could not understand your query. Try asking about cancelled, delivered, or pending orders.";
            }

            return new ChatResponse("assistant", response);
        }

        // --- 4. Help command ---
        if (normalizedPrompt.contains("help") || normalizedPrompt.contains("what can you do")) {
            return new ChatResponse("assistant",
                    "ℹ️ I can help you with:\n" +
                    "- Product info (color, price, stock, discount)\n" +
                    "- Your orders (last 5, placed, cancelled, delivered, pending)\n");
        }

        // --- 5. Default fallback ---
        return new ChatResponse("assistant", response);
    }
}
