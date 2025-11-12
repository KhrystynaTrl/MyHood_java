package it.start2impact.MyHood.service;

import it.start2impact.MyHood.dto.PostDto;
import it.start2impact.MyHood.entities.PostEntity;
import it.start2impact.MyHood.entities.UserEntity;
import it.start2impact.MyHood.exceptions.MyHoodException;
import it.start2impact.MyHood.exceptions.NotFoundException;
import it.start2impact.MyHood.exceptions.UnauthorizedException;
import it.start2impact.MyHood.mappers.PostMapper;
import it.start2impact.MyHood.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;

    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;

    }

    public PostDto createPost(PostDto post, UserEntity user){
        logger.info("PostService.createPost - {} {}",post, user);
        PostEntity entity = PostMapper.fromDto(post);
        entity.setUserEntity(user);
        return PostMapper.fromEntity(postRepository.saveAndFlush(entity));
    }
    public PostDto updatePost(PostDto post, UserEntity user) throws MyHoodException {
        logger.info("PostService.updatePost - {} {}",post, user);
        PostEntity entity = postRepository.findById(post.getId()).orElseThrow(()->new NotFoundException("Post not found"));
        if(entity.getUserEntity().getId().equals(user.getId())){
            PostEntity postEntityMapped = PostMapper.fromDto(post);
            postEntityMapped.setId(entity.getId());
            postEntityMapped.setUserEntity(entity.getUserEntity());
            return PostMapper.fromEntity(postRepository.save(postEntityMapped));
        }
        throw new UnauthorizedException("Only the author of this post can update it");
    }

    public void deletePost(int id, UserEntity user) throws MyHoodException{
        logger.info("PostService.deletePost - {} {}",id, user);
        PostEntity entity = postRepository.findById(id).orElseThrow(()-> new NotFoundException("Post not found"));
        if(entity.getUserEntity().getId().equals(user.getId())){
            postRepository.deleteById(id);
            return;
        }
        throw new NotFoundException("Post not found");
    }

    public List<PostDto> findAll(){
        logger.info("PostService.findAll");
        List<PostEntity> allPosts = postRepository.findAll();
        return PostMapper.fromEntityList(allPosts);
    }

    public List<PostDto> findByDate(LocalDate fromDate, LocalDate toDate){
        logger.info("PostService.findByDate - {} {}",fromDate, toDate);
        List<PostEntity> entities = postRepository.findByDate(fromDate,toDate);
        return PostMapper.fromEntityList(entities);

    }

}
