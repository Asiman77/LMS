package com.example.library.Service;

import com.example.library.Model.Order;
import com.example.library.Model.Book;
import com.example.library.Model.Student;
import com.example.library.repository.OrderRepository;
import com.example.library.repository.BookRepository;
import com.example.library.repository.StudentRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    public OrderService(OrderRepository orderRepository, BookRepository bookRepository, StudentRepository studentRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
    }

    // ✅ Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // ✅ Get order by ID
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + id));
    }

    // ✅ Create a new order (Ensures book stock & student existence)
    public Order createOrder(Order order) {
        Book book = bookRepository.findById(order.getBook().getId())
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));

        Student student = studentRepository.findById(order.getStudent().getId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if (book.getStock() <= 0) {
            throw new IllegalStateException("Stock is insufficient!");
        }

        book.setStock(book.getStock() - 1);
        bookRepository.save(book);

        order.setStudent(student);
        order.setOrderTimestamp(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // ✅ Return a book (Restores book stock)
    public Order returnOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found!"));

        if (order.getReturnTimestamp() != null) {
            throw new IllegalStateException("Book has already been returned!");
        }

        order.setReturnTimestamp(LocalDateTime.now());

        Book book = order.getBook();
        book.setStock(book.getStock() + 1);
        bookRepository.save(book);

        return orderRepository.save(order);
    }

    // ✅ Delete an order
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Order not found!"));

        orderRepository.delete(order);
    }
}
