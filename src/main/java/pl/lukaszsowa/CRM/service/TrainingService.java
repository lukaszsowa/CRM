package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Training;
import pl.lukaszsowa.CRM.repository.TrainingRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingService {

    @Autowired
    TrainingRepository trainingRepository;

    public Training addTraining(Training training){
        return trainingRepository.save(training);
    }

    public List<Training> getAllTrainings(){
        return trainingRepository.findAll();
    }

    public void deleteTraining(long id){
        trainingRepository.deleteById(id);
    }

    public Optional<Training> getTrainingById(long id){
        return trainingRepository.findById(id);
    }

    public Long getTrainingsCount(){
        return trainingRepository.count();
    }

    public List<Training> getTrainings(long id){
        return trainingRepository.getTrainings(id);
    }
}
