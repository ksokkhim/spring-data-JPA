package co.istad.ecommerce.features.order;

import co.istad.ecommerce.features.order.dto.CreateOrderRequest;
import co.istad.ecommerce.features.order.dto.OrderResponse;
import co.istad.ecommerce.features.order.mapper.OrderMapper;
import co.istad.ecommerce.features.product.Product;
import co.istad.ecommerce.features.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;
    private OrderRepository orderRepository;
    private CreateOrderRequest createOrderRequest;
    private ProductRepository productRepository;


    @Override
    public OrderResponse createNew(CreateOrderRequest createOrderRequest) {
        final Order order = new Order();
        order.setAddress(createOrderRequest.address());
        order.setCustomerId("koko");
        order.setDiscount(createOrderRequest.discount());
        order.setRemark(createOrderRequest.remark());
        order.setIsDelete(false);
        order.setStatus(false);


        List<OrderLine> orderLines = new ArrayList<>();


        Boolean isValidOrder = createOrderRequest.orderLines().stream()
                .allMatch(orderLineDto -> {
                    Optional<Product> optionalProduct = productRepository.findByCode((orderLineDto.code()));


                    if (optionalProduct.isPresent()) {
                        OrderLine orderLine = new OrderLine();
                        orderLine.setProduct(optionalProduct.get());
                        orderLine.setQty(orderLineDto.qty());
                        orderLine.setUnitPrice(orderLineDto.unitPrice());
                        orderLine.setOrder(order);
                        orderLines.add(orderLine);
                        return true;
                    }

                    return false;


                });


        if (!isValidOrder) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid order line");
        }


        order.setOrderLines(orderLines);

        Order savedOrder = orderRepository.save(order);


        return orderMapper.mapOrdertoOrderResponse(savedOrder);
    }

    @Override
    public Page<OrderResponse> findAll(int pageNumber, int pageSize) {
        Sort sortById = Sort.by(Sort.Direction.DESC, "id");
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sortById);
        Page<Order> orders = orderRepository.findAll(pageRequest);
        return orders.map(orderMapper::mapOrdertoOrderResponse);
    }

    @Override
    public OrderResponse findById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order had been not found"));
        return orderMapper.mapOrdertoOrderResponse(order);
    }

    @Override
    public void softDelete(UUID id) {

        Order softDeleteOrder = orderRepository.findById(id)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order had been not found"));
        softDeleteOrder.setIsDelete(true);
        orderRepository.save(softDeleteOrder);

    }

    @Override
    public void hardDelete(UUID id) {
        Order hardDeleteOrder = orderRepository.findById(id)
                .orElseThrow(()
                        -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order had been not found"));
        orderRepository.delete(hardDeleteOrder);

    }

    @Override
    public OrderResponse paymentById(UUID id) {
        Order statuesPayment = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Statues had been not found"));
        statuesPayment.setStatus(true);
        orderRepository.save(statuesPayment);
        return orderMapper.mapOrdertoOrderResponse(statuesPayment);
    }


}
