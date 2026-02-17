package it.start2impact.MyHood;

import it.start2impact.MyHood.dto.FilterDto;
import it.start2impact.MyHood.dto.PostDto;
import it.start2impact.MyHood.entities.LocationEntity;
import it.start2impact.MyHood.entities.PostEntity;
import it.start2impact.MyHood.entities.UserEntity;
import it.start2impact.MyHood.exceptions.MyHoodException;
import it.start2impact.MyHood.exceptions.NotFoundException;
import it.start2impact.MyHood.exceptions.UnauthorizedException;
import it.start2impact.MyHood.repositories.LocationRepository;
import it.start2impact.MyHood.repositories.PostRepository;
import it.start2impact.MyHood.service.PostService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private PostService postService;

    @Test
    void createPost_returnsPostDto() {
        // Arrange
        PostDto postDto = new PostDto();
        UserEntity user = new UserEntity(); user.setId(1L);
        PostEntity savedEntity = new PostEntity();
        when(postRepository.saveAndFlush(any(PostEntity.class))).thenReturn(savedEntity);

        // Act
        PostDto result = postService.createPost(postDto, user);

        // Assert
        assertNotNull(result);
        verify(postRepository).saveAndFlush(any(PostEntity.class));
    }

    @Test
    void updatePost_authorized_returnsUpdatedPost() throws MyHoodException {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setId(1);
        UserEntity user = new UserEntity();
        user.setId(1L);
        PostEntity existingPost = new PostEntity();
        existingPost.setId(1L);
        existingPost.setUserEntity(user);
        when(postRepository.findById(1)).thenReturn(Optional.of(existingPost));
        when(postRepository.save(any(PostEntity.class))).thenReturn(existingPost);

        // Act
        PostDto result = postService.updatePost(postDto, user);

        // Assert
        assertNotNull(result);
        verify(postRepository).save(any(PostEntity.class));
        verify(postRepository).findById(any(Integer.class));
    }

    @Test
    void updatePost_unauthorized_throwsUnauthorizedException() {
        // Arrange
        PostDto postDto = new PostDto();
        postDto.setId(1);
        UserEntity user = new UserEntity();
        user.setId(2L);
        PostEntity existingPost = new PostEntity();
        existingPost.setId(1L);
        UserEntity otherUser = new UserEntity();
        otherUser.setId(1L);
        existingPost.setUserEntity(otherUser);
        when(postRepository.findById(1)).thenReturn(Optional.of(existingPost));

        // Act & Assert
        MyHoodException exception = assertThrows(MyHoodException.class,
                () -> postService.updatePost(postDto, user));
        assertNotNull(exception);
        assertInstanceOf(UnauthorizedException.class,exception);
    }

    @Test
    void deletePost_authorized_deletesPost() throws MyHoodException {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(1L);
        PostEntity existingPost = new PostEntity();
        existingPost.setId(1L);
        existingPost.setUserEntity(user);
        when(postRepository.findById(1)).thenReturn(Optional.of(existingPost));

        // Act
        postService.deletePost(1, user);

        // Assert
        verify(postRepository).deleteById(1);
    }

    @Test
    void deletePost_notAuthor_throwsNotFound() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setId(2L);
        PostEntity existingPost = new PostEntity();
        existingPost.setId(1L);
        UserEntity otherUser = new UserEntity();
        otherUser.setId(1L);
        existingPost.setUserEntity(otherUser);
        when(postRepository.findById(1)).thenReturn(Optional.of(existingPost));

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> postService.deletePost(1, user));
    }

    @Test
    void findAll_returnsAllPosts() {
        // Arrange
        PostEntity post1 = new PostEntity();
        when(postRepository.findAll()).thenReturn(List.of(post1));

        // Act
        List<PostDto> result = postService.findAll();

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findByDate_returnsPostsInRange() {
        // Arrange
        LocalDate from = LocalDate.now().minusDays(1);
        LocalDate to = LocalDate.now();
        PostEntity post = new PostEntity();
        when(postRepository.findByDate(from, to)).thenReturn(List.of(post));

        // Act
        List<PostDto> result = postService.findByDate(from, to);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void search_validFilter_returnsPosts() throws Exception {
        // Arrange
        FilterDto filter = new FilterDto();
        filter.setLocation("Roma");
        LocationEntity location = new LocationEntity();
        location.setLocation("Roma");
        when(locationRepository.findByLocationIgnoreCase("Roma")).thenReturn(Optional.of(location));
        PostEntity post = new PostEntity();
        when(postRepository.search(any(), any(), any(), eq(location))).thenReturn(List.of(post));

        // Act
        List<PostDto> result = postService.search(filter);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void search_invalidLocation_throwsNotFound() {
        // Arrange
        FilterDto filter = new FilterDto();
        filter.setLocation("CittaFinta");
        when(locationRepository.findByLocationIgnoreCase("CittaFinta")).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> postService.search(filter));
    }

    @Test
    void findAllOwnPosts_returnsUserPosts() {
        // Arrange
        UserEntity user = new UserEntity();
        PostEntity post = new PostEntity();
        when(postRepository.findByUserEntity(user)).thenReturn(List.of(post));

        // Act
        List<PostDto> result = postService.findAllOwnPosts(user);

        // Assert
        assertEquals(1, result.size());
    }
}
