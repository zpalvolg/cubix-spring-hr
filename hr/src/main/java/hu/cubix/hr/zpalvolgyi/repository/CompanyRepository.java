package hu.cubix.hr.zpalvolgyi.repository;

import hu.cubix.hr.zpalvolgyi.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
