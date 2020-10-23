package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.model.Contact;
import pl.lukaszsowa.CRM.model.Idea;
import pl.lukaszsowa.CRM.repository.IdeaRepository;

@Service
public class IdeaService {

    @Autowired
    IdeaRepository ideaRepository;

    public Idea addIdea(Idea idea){
        return ideaRepository.save(idea);
    }
}
