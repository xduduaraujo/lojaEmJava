package iteris.minishop.repository;

import iteris.minishop.domain.entity.ImportRegistry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRegistryRepository extends JpaRepository<ImportRegistry, Integer> {
}
