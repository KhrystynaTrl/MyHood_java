package it.start2impact.MyHood.controller;

import it.start2impact.MyHood.dto.PostDto;
import it.start2impact.MyHood.entities.UserEntity;
import it.start2impact.MyHood.exceptions.MyHoodException;
import it.start2impact.MyHood.exceptions.NotFoundException;
import it.start2impact.MyHood.exceptions.UnauthorizedException;
import it.start2impact.MyHood.service.PostService;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto post, @AuthenticationPrincipal UserEntity userEntity) throws MyHoodException {
        logger.info("PostController.createPost - {}", post);
        if(userEntity != null ){
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post, userEntity));
        }
        throw new UnauthorizedException("Please login");
    }

    @PutMapping("/update")
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto post, @AuthenticationPrincipal UserEntity userEntity) throws MyHoodException {
        logger.info("PostController.updatePost - {}", post);
        if(post.getId() == null){
            throw new MyHoodException("Id mandatory", HttpStatus.BAD_REQUEST);
        }
        if(userEntity != null ){
            return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(post, userEntity));
        }
        throw new UnauthorizedException("Please login");
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deletePost(@Valid @RequestParam(name = "id") int id, @AuthenticationPrincipal UserEntity userEntity) throws MyHoodException {
        logger.info("PostController.deletePost - {}", id);
        if(userEntity != null ){
            postService.deletePost(id,userEntity);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        throw new UnauthorizedException("Please login");
    }

    @GetMapping("/find-all")
    public ResponseEntity<List<PostDto>> findAllPosts()throws MyHoodException{
        logger.info("PostController.findAllPosts");
        List<PostDto> findAllPost = postService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(findAllPost);
    }

    @GetMapping("/find-by-date")
    public ResponseEntity<List<PostDto>> findByDate(LocalDate fromDate, LocalDate toDate)throws NotFoundException{
        logger.info("PostController.findByDate- {} {}", fromDate, toDate);
        List<PostDto> findByDate = postService.findByDate(fromDate,toDate);
        if(!findByDate.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(findByDate);
        }
        throw new NotFoundException("There are no events available within the selected date range");
    }


}
