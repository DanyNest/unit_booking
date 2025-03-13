package spribe.spribetechtask.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

/**
 * @Author danynest @CreateAt 06.03.25
 */
@Entity
@Table(name = "unit")
@Data
public class Unit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "unit_name")
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "accommodation_type")
  private AccommodationType accommodationType;

  @Column(name = "floor_number")
  private int floorNumber;

  @Column(name = "rooms_count")
  private int roomsCount;

  private BigDecimal price;
  private String description;

  @CreationTimestamp
  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @OneToMany(mappedBy = "unit", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BookingOrder> bookings;
}
