package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.model.Training;
import pl.lukaszsowa.CRM.repository.TrainingRepository;

@Service
public class TrainingService {

    @Autowired
    TrainingRepository trainingRepository;

    public Training addTraining(Training training){
        return trainingRepository.save(training);
    }
}
