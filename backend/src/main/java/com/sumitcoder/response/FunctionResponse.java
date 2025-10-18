package com.sumitcoder.response;

import com.sumitcoder.dto.OrderHistory;
import com.sumitcoder.model.Cart;
import com.sumitcoder.model.Order;
import com.sumitcoder.model.Product;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionResponse {
    private String functionName;
    private Cart userCart;
    private OrderHistory orderHistory;
    private Product product;
}
