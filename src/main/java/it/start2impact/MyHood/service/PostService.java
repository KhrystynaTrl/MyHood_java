package it.start2impact.MyHood.service;

import it.start2impact.MyHood.repositories.PostReopository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostReopository postReopository;

    @Autowired
    public PostService(PostReopository postReopository) {
        this.postReopository = postReopository;
    }
}
