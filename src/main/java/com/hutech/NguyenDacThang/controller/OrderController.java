package com.hutech.NguyenDacThang.controller;

import com.hutech.NguyenDacThang.model.CartItem;
import com.hutech.NguyenDacThang.model.Order;
import com.hutech.NguyenDacThang.model.User;
import com.hutech.NguyenDacThang.service.CartService;
import com.hutech.NguyenDacThang.service.UserService;
import com.hutech.NguyenDacThang.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private UserService userService;

    @GetMapping("/checkout")
    public String checkout(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);

        if (user != null) {
            model.addAttribute("username", user.getUsername());
            model.addAttribute("email", user.getEmail());
            model.addAttribute("phone", user.getPhone());
        } else {
            model.addAttribute("username", "");
            model.addAttribute("email", "");
            model.addAttribute("phone", "");
        }
        return "cart/checkout"; // Assuming you have a checkout page
    }

    @PostMapping("/submit")
    public String submitOrder(@ModelAttribute("order") Order order, Model model, Principal principal) {
        String username = principal.getName();
        List<CartItem> cartItems = cartService.getCartItems();
        if (cartItems.isEmpty()) {
            return "redirect:/cart"; // Redirect if cart is empty
        }

        String customerName = order.getCustomerName();
        String shippingAddress = order.getShippingAddress();
        String phone = order.getPhone();
        String email = order.getEmail();
        String notes = order.getNotes();
        String paymentMethod = order.getPaymentMethod();

        Order createdOrder = orderService.createOrder(customerName, shippingAddress, phone, email, notes, paymentMethod, cartItems, username);
        model.addAttribute("order", createdOrder);
        return "redirect:/order/confirmation";
    }

    @GetMapping("/confirmation")
    public String orderConfirmation(@ModelAttribute("order") Order order, Model model) {
        model.addAttribute("message", "Your order has been successfully placed.");
        return "cart/order-confirmation";
    }

    @GetMapping("/listOrder")
    public String getlistOrders(Model model) {
        Map<User, List<Order>> ordersByUser = orderService.getOrdersGroupedByUser();
        model.addAttribute("ordersByUser", ordersByUser);
        return "cart/listOrder";
    }

    @GetMapping("/myOrder")
    public String getMyOrders(Model model, Principal principal) {
        String username = principal.getName();
        List<Order> orders = orderService.getUserOrders(username);
        model.addAttribute("orders", orders);
        return "cart/myOrder";
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
