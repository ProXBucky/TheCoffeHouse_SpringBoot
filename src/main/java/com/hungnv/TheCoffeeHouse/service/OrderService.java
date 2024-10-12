package com.hungnv.TheCoffeeHouse.service;

import com.hungnv.TheCoffeeHouse.dto.OrderDTO;
import com.hungnv.TheCoffeeHouse.dto.ProductDTO;
import com.hungnv.TheCoffeeHouse.dto.UserDTO;
import com.hungnv.TheCoffeeHouse.exception.OrderNotFoundException;
import com.hungnv.TheCoffeeHouse.exception.ProductNotFoundException;
import com.hungnv.TheCoffeeHouse.model.OrderDetail;
import com.hungnv.TheCoffeeHouse.model.Orders;
import com.hungnv.TheCoffeeHouse.model.Products;
import com.hungnv.TheCoffeeHouse.model.Users;
import com.hungnv.TheCoffeeHouse.repository.OrderDetailRepository;
import com.hungnv.TheCoffeeHouse.repository.OrderRepository;
import com.hungnv.TheCoffeeHouse.repository.ProductRepository;
import com.hungnv.TheCoffeeHouse.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final UsersRepository usersRepository;
    private final ProductRepository productRepository;

    public List<OrderDTO.OrderResponse> getLastest(int limit) {
        List<Orders> ordersList = orderRepository.findTopN(limit);
        List<OrderDTO.OrderResponse> orderResponsesList = new ArrayList<>();
        for (Orders orders : ordersList) {
            OrderDTO.OrderResponse x = new OrderDTO.OrderResponse();
            UserDTO.UserResponse y = getUserResponse(orders);

            OrderDTO.StatusOrder z = new OrderDTO.StatusOrder();
            z.setStatusEN(orders.getStatusData().getValueEn());
            z.setStatusVN(orders.getStatusData().getValueVn());
            x.setOrder(orders);
            x.setUser(y);
            x.setStatusOrder(z);
            orderResponsesList.add(x);
        }
        return orderResponsesList;
    }

    private static UserDTO.UserResponse getUserResponse(Orders orders) {
        UserDTO.UserResponse y = new UserDTO.UserResponse();
        y.setId(orders.getUser().getId());
        y.setPhone(orders.getUser().getPhone());
        y.setAddress(orders.getUser().getAddress());
        y.setEmail(orders.getUser().getEmail());
        y.setFirstName(orders.getUser().getFirstName());
        y.setLastName(orders.getUser().getLastName());
        y.setIsApproved(orders.getUser().getIsApproved());
        y.setRoleId(orders.getUser().getRoleId());
        return y;
    }

    public List<OrderDTO.OrderResponseFull> getAll() {
        List<Orders> orders = orderRepository.findAllOrdersByStatusId(Arrays.asList("SP1", "SP2"));
        return orders.stream().map(this::mapToOrderDTO).collect(Collectors.toList());
    }

    public List<OrderDTO.OrderResponseFull> getAllDelivered() {
        List<Orders> orders = orderRepository.findAllOrdersByStatusId(Arrays.asList("SP3"));
        return orders.stream().map(this::mapToOrderDTO).collect(Collectors.toList());
    }

    private OrderDTO.OrderResponseFull mapToOrderDTO(Orders order) {
        OrderDTO.OrderResponseFull orderResponseFull = new OrderDTO.OrderResponseFull();
        Orders orders = order;
        orderResponseFull.setOrder(orders);

        UserDTO.UserResponse userDTO = new UserDTO.UserResponse();
        userDTO.setId(order.getUser().getId());
        userDTO.setEmail(order.getUser().getEmail());
        userDTO.setFirstName(order.getUser().getFirstName());
        userDTO.setLastName(order.getUser().getLastName());
        userDTO.setAddress(order.getUser().getAddress());
        userDTO.setPhone(order.getUser().getPhone());
        orderResponseFull.setUser(userDTO);

        OrderDTO.StatusOrder statusDTO = new OrderDTO.StatusOrder();
        statusDTO.setStatusEN(order.getStatusData().getValueEn());
        statusDTO.setStatusVN(order.getStatusData().getValueVn());
        orderResponseFull.setStatusOrder(statusDTO);

        // Map OrderDetails
        List<OrderDTO.DetailOrderResponse> detailOrderResponseList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderId(order.getId());

        for (OrderDetail orderDetail1 : orderDetailList) {

            ProductDTO.ProductDescription productDTO = ProductDTO.ProductDescription.builder()
                    .id(orderDetail1.getProductData().getId())
                    .name(orderDetail1.getProductData().getName())
                    .image(orderDetail1.getProductData().getImage())
                    .originalPrice(orderDetail1.getProductData().getOriginalPrice())
                    .build();

            detailOrderResponseList.add(new OrderDTO.DetailOrderResponse(orderDetail1, productDTO));

        }
        orderResponseFull.setDetailOrderResponses(detailOrderResponseList);

        return orderResponseFull;
    }

    public void delete(Long orderId) {
        Orders order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " not found"));
        orderDetailRepository.deleteAllByOrderId(order.getId());
        orderRepository.delete(order);
    }

    public Orders updatePay(OrderDTO.UpdateOrderRequest body) {
        Orders order = orderRepository.findById(body.getOrderId()).orElseThrow(() -> new OrderNotFoundException("Order with id " + body.getOrderId() + " not found"));
        order.setStatusId("SP2");
        return orderRepository.save(order);
    }

    public Orders updateDelivery(OrderDTO.UpdateOrderRequest body) {
        Orders order = orderRepository.findById(body.getOrderId()).orElseThrow(() -> new OrderNotFoundException("Order with id " + body.getOrderId() + " not found"));
        order.setStatusId("SP3");
        return orderRepository.save(order);
    }

    @Transactional
    public OrderDTO.NewOrderResponse add(OrderDTO.OrderRequest body) {
        System.out.println("request" + body);
        Users existingUser = usersRepository.findByEmail(body.getEmail());
        Users user;
        if (existingUser != null) {
            user = existingUser;
        } else {
            user = Users.builder()
                    .email(body.getEmail())
                    .firstName(body.getFirstName())
                    .lastName(body.getLastName())
                    .phone(body.getPhone())
                    .address(body.getAddress())
                    .roleId("R3")
                    .isApproved(1)
                    .build();
            usersRepository.save(user);
        }

        // Tạo Order mới
        Orders order = Orders.builder()
                .userId(user.getId())
                .totalPrice(body.getCartTotalAmount())
                .timeOrder(body.getTimeOrder())
                .statusId("SP1")
                .build();
        orderRepository.save(order);

        // Tạo OrderDetail và cập nhật quantitySold của Product
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDTO.CartItem item : body.getCartItems()) {
            Products product = productRepository.findById(item.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product with id " + item.getProductId() + " not found"));

            OrderDetail orderDetail = OrderDetail.builder()
                    .orderId(order.getId())
                    .productId(product.getId())
                    .quantity(Math.toIntExact(item.getQuantity()))
                    .build();
            orderDetails.add(orderDetail);
            product.setQuantitySold((int) (product.getQuantitySold() + item.getQuantity()));
            productRepository.save(product);
        }
        orderDetailRepository.saveAll(orderDetails);

        return new OrderDTO.NewOrderResponse(
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getAddress(),
                order,
                orderDetails
        );
    }

}
