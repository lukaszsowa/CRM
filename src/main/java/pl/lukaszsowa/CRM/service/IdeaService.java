package pl.lukaszsowa.CRM.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszsowa.CRM.repository.IdeaRepository;

@Service
public class IdeaService {

    @Autowired
    IdeaRepository ideaRepository;
}
