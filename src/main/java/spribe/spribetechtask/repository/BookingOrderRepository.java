package spribe.spribetechtask.repository;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import spribe.spribetechtask.model.BookingOrder;

/**
 * @Author danynest @CreateAt 11.03.25
 */
public interface BookingOrderRepository extends JpaRepository<BookingOrder, Long> {
    @Query("SELECT b FROM BookingOrder b WHERE b.unit.id = :unitId")
    Page<BookingOrder> findByUnitId(@Param("unitId") long unitId, Pageable pageable);
}
