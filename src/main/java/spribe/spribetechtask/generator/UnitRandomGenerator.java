package spribe.spribetechtask.generator;

import static spribe.spribetechtask.model.AccommodationType.APARTMENTS;
import static spribe.spribetechtask.model.AccommodationType.FLAT;
import static spribe.spribetechtask.model.AccommodationType.HOME;
import static spribe.spribetechtask.model.AccommodationType.STUDIO;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spribe.spribetechtask.model.Unit;
import spribe.spribetechtask.repository.UnitRepository;

/**
 * @Author danynest @CreateAt 13.03.25
 */
@Service
public class UnitRandomGenerator {
  @Autowired private UnitRepository unitRepository;

  @PostConstruct
  @Transactional
  public void generateRandomUnits() {
    Random random = new Random();
    List<Unit> generatedUnits =
        IntStream.range(0, 90)
            .parallel()
            .mapToObj(
                i -> {
                  Unit unit = new Unit();
                  unit.setName("UnitName: " + (i + 1));
                  unit.setAccommodationType(
                      i % 2 == 0 ? FLAT : i % 3 == 0 ? HOME : i % 5 == 0 ? APARTMENTS : STUDIO);
                  unit.setFloorNumber(random.nextInt(10) + 1);
                  unit.setRoomsCount(random.nextInt(5) + 1);
                  unit.setPrice(new BigDecimal(random.nextInt(1000) + 100));
                  unit.setDescription("Unit: " + (i + 1) + "description");
                  unit.setCreatedAt(LocalDateTime.now());
                  return unit;
                })
            .toList();
    unitRepository.saveAll(generatedUnits);
  }
}
