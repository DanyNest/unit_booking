package spribe.spribetechtask.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "booking_order")
@Data
public class BookingOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @CreationTimestamp
  @Column(name = "order_creation_date")
  private LocalDateTime creationDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus status;

  @Column(name = "booking_start_date")
  private LocalDateTime bookedFrom;

  @Column(name = "booking_end_date")
  private LocalDateTime bookedTo;

  @ManyToOne
  @JoinColumn(name = "unit_id")
  private Unit unit;
}
