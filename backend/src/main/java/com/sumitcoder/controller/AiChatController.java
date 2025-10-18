package com.sumitcoder.controller;

import com.sumitcoder.request.Prompt;
import com.sumitcoder.response.ChatResponse;
import com.sumitcoder.service.AiChatService;
import com.sumitcoder.service.OrderHistoryService;
import com.sumitcoder.dto.OrderHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiChatController {

    private final AiChatService aiChatService;
    private final OrderHistoryService orderHistoryService;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chatWithAi(
            @RequestBody Prompt prompt,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long productId
    ) {
        String lowerPrompt = prompt.getPrompt().toLowerCase();

        // ✅ If user is asking about orders
        if (userId != null && (
                lowerPrompt.contains("order") || 
                lowerPrompt.contains("history") || 
                lowerPrompt.contains("purchase") || 
                lowerPrompt.contains("status") ||
                lowerPrompt.contains("cancelled") ||
                lowerPrompt.contains("pending") ||
                lowerPrompt.contains("delivered") ||
                lowerPrompt.contains("how many")
        )) {
            OrderHistory history = orderHistoryService.getOrderHistory(userId);
            StringBuilder responseBuilder = new StringBuilder();

            if (lowerPrompt.contains("how many")) {
                // 👉 Example: "How many orders have I placed?"
                responseBuilder.append("📝 You have placed a total of ")
                        .append(history.getTotalOrders())
                        .append(" orders.");
            } 
            else if (lowerPrompt.contains("cancelled")) {
                // 👉 Example: "Show my cancelled orders"
                responseBuilder.append("❌ You have ")
                        .append(history.getCancelledOrders())
                        .append(" cancelled orders.");
            }
            else if (lowerPrompt.contains("pending")) {
                // 👉 Example: "Show my pending orders"
                responseBuilder.append("⏳ You have ")
                        .append(history.getPendingOrders())
                        .append(" pending orders.");
            }
            else if (lowerPrompt.contains("delivered")) {
                // 👉 Example: "Show my delivered orders"
                responseBuilder.append("✅ You have ")
                        .append(history.getCompletedOrders())
                        .append(" delivered orders.");
            }
            else {
                // 👉 Default: Show last 5 orders
                responseBuilder.append("📦 Your last 5 orders:\n");
                if (history.getCurrentOrders() != null && !history.getCurrentOrders().isEmpty()) {
                    history.getCurrentOrders().stream()
                            .sorted((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()))
                            .limit(5)
                            .forEach(order -> responseBuilder.append("- Order ID: ")
                                    .append(order.getOrderId())
                                    .append(" | Status: ")
                                    .append(order.getOrderStatus())
                                    .append(" | Payment: ")
                                    .append(order.getPaymentStatus())
                                    .append("\n"));
                } else {
                    responseBuilder.append("No orders found for your account.\n");
                }
            }

            return ResponseEntity.ok(new ChatResponse("assistant", responseBuilder.toString()));
        }

        // ✅ Otherwise product query handle kare
        ChatResponse response = aiChatService.getResponse(prompt.getPrompt(), productId, userId);
        return ResponseEntity.ok(response);
    }
}
