package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.Form;
import hu.cubix.hr.zpalvolgyi.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FormService {
    @Autowired
    private FormRepository formRepository;

    public Form create(Form form){
        return formRepository.save(form);
    }

    public void delete(Long id){
        formRepository.deleteById(id);
    }

    public List<Form> findAll(){
        return formRepository.findAll();
    }
}
