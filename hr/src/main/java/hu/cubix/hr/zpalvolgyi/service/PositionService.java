package hu.cubix.hr.zpalvolgyi.service;

import hu.cubix.hr.zpalvolgyi.model.Form;
import hu.cubix.hr.zpalvolgyi.model.Position;
import hu.cubix.hr.zpalvolgyi.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionService {

    @Autowired
    private PositionRepository positionRepository;

    public Position create(Position position){
        return positionRepository.save(position);
    }

    public void delete(Long id){
        positionRepository.deleteById(id);
    }
    public List<Position> findAll(){
        return positionRepository.findAll();
    }
}
