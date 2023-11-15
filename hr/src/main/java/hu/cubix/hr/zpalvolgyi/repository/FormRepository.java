package hu.cubix.hr.zpalvolgyi.repository;

import hu.cubix.hr.zpalvolgyi.model.Form;
import hu.cubix.hr.zpalvolgyi.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {

    public List<Form> findByName(String name);

}
