package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Idea;
import pl.lukaszsowa.CRM.repository.IdeaRepository;

import java.util.List;

@Service
public class IdeaService {

    @Autowired
    IdeaRepository ideaRepository;

    public Idea addIdea(Idea idea){
        return ideaRepository.save(idea);
    }

    public void deleteIdea(long id){
        ideaRepository.deleteById(id);
    }

    public List<Idea> getIdeas(){
        return ideaRepository.findAll();
    }

    public List<Idea> getAllIdeasByUserId(long id){
        return ideaRepository.getAllIdeasByUserId(id);
    }

    public List<Idea> getIdeasByUserId(long id){
        return ideaRepository.getIdeasByUserId(id, PageRequest.of(0,4));
    }
}
