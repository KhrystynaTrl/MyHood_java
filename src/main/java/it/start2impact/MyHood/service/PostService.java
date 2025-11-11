package it.start2impact.MyHood.service;

import it.start2impact.MyHood.dto.PostDto;
import it.start2impact.MyHood.entities.PostEntity;
import it.start2impact.MyHood.entities.UserEntity;
import it.start2impact.MyHood.exceptions.NotFoundException;
import it.start2impact.MyHood.exceptions.UnauthorizedException;
import it.start2impact.MyHood.mappers.PostMapper;
import it.start2impact.MyHood.repositories.PostRepository;
import it.start2impact.MyHood.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;

    }

    public PostDto createPost(PostDto post, UserEntity user){
        PostEntity entity = PostMapper.fromDto(post);
        entity.setUserEntity(user);
        return PostMapper.fromEntity(postRepository.saveAndFlush(entity));
    }
    public PostDto updatePost(PostDto post, UserEntity user) throws UnauthorizedException, NotFoundException {
        PostEntity entity = postRepository.findById(post.getId()).orElseThrow(()->new NotFoundException("Post not found"));
        if(entity.getId().equals(user.getId())){
            PostEntity postEntityMapped = PostMapper.fromDto(post);
            postEntityMapped.setId(entity.getId());
            postEntityMapped.setUserEntity(entity.getUserEntity());
            return PostMapper.fromEntity(postRepository.save(postEntityMapped));
        }
        throw new UnauthorizedException("Only the author of this post can update it");





    }


}
