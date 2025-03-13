package spribe.spribetechtask.model.dto.mapper;

import org.mapstruct.Mapper;
import spribe.spribetechtask.model.Unit;
import spribe.spribetechtask.model.dto.UnitDto;

/**
 * @Author danynest @CreateAt 10.03.25
 */
@Mapper(componentModel = "spring")
public interface UnitMapper {
  UnitDto unitToUnitDTO(Unit unit);
}
