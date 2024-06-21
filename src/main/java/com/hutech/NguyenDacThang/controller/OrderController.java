package com.hutech.NguyenDacThang.controller;

import com.hutech.NguyenDacThang.model.CartItem;
import com.hutech.NguyenDacThang.model.Order;
import com.hutech.NguyenDacThang.service.CartService;
import com.hutech.NguyenDacThang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @GetMapping("/checkout")
    public String checkout() {
        return "/cart/checkout"; // Assuming you have a checkout page
    }

    @PostMapping("/submit")
    public String submitOrder(@RequestParam String customerName,
                              @RequestParam String shippingAddress,
                              @RequestParam String phone,
                              @RequestParam String email,
                              @RequestParam String notes,
                              @RequestParam String paymentMethod,
                              Model model) {
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }
        Order order = orderService.createOrder(customerName, shippingAddress, phone, email, notes, paymentMethod, cartItems);
        cartService.clearCart();
        model.addAttribute("order", order);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(@ModelAttribute("order") Order order, Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation";
    }

    @GetMapping("/listOrder")
    public String getlistOrders(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "cart/listOrder";
    }

    @GetMapping("/listOrderAdmin")
    public String getlistOrderAdmin(Model model) {
        List<Order> orders = orderService.getAllOrders();
        model.addAttribute("orders", orders);
        return "cart/listOrderAdmin";
    }

    @GetMapping("/orderDetail/{id}")
    public String getOrderDetail(@PathVariable("id") Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return "redirect:/order/listOrder";
        }
        model.addAttribute("order", order);
        return "cart/orderDetail";
    }
}
