package spribe.spribetechtask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import spribe.spribetechtask.model.Unit;

/**
 * @Author danynest @CreateAt 06.03.25
 */
public interface UnitRepository extends JpaRepository<Unit, Long>, JpaSpecificationExecutor<Unit> {}
