package com.reckue.post.services.realizations;

import com.reckue.post.PostServiceApplicationTests;
import com.reckue.post.exceptions.ReckueIllegalArgumentException;
import com.reckue.post.exceptions.models.comment.CommentAlreadyExistsException;
import com.reckue.post.exceptions.models.comment.CommentNotFoundException;
import com.reckue.post.models.Comment;
import com.reckue.post.repositories.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Class CommentServiceRealizationTest represents test for CommentService class.
 *
 * @author Artur Magomedov
 */
public class CommentServiceRealizationTest extends PostServiceApplicationTests {
    private Comment comment;
    private Comment comment2;
    private Comment comment3;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceRealization commentService;

    @BeforeEach
    private void createComments(){
        comment = Comment.builder()
                .id("javascript")
                .text("front")
                .userId("vlad")
                .postId("007")
                .published(404L)
                .build();

        comment2 = Comment.builder()
                .id("html")
                .text("test")
                .userId("ivery")
                .postId("911")
                .published(500L)
                .build();

        comment3 = Comment.builder().
                id("java").
                text("web").
                userId("sherzod").
                postId("666").
                published(200L).
                build();
    }

    @Test
    public void create() {
        when(commentRepository.save(comment)).thenReturn(comment);

        assertEquals(comment, commentService.create(comment));
    }

    @Test
    public void createIfCommentAlreadyExists() {
        doReturn(true).when(commentRepository).existsById(Mockito.anyString());

        Exception exception = assertThrows(CommentAlreadyExistsException.class, () -> commentService.create(comment));
        assertEquals("Comment by id '" + comment.getId() + "' already exists", exception.getMessage());
    }

    @Test
    public void update() {
        when(commentRepository.existsById(comment.getId())).thenReturn(true);
        when(commentRepository.save(comment)).thenReturn(comment);

        Assertions.assertEquals(comment, commentService.update(comment));
    }

    @Test
    public void updateCommentWithNullId() {
        Comment nullableComm = Comment.builder().build();

        Exception exception = assertThrows(ReckueIllegalArgumentException.class,
                () -> commentService.update(nullableComm));
        assertEquals("The parameter is null", exception.getMessage());
    }

    @Test
    public void updateCommentIfNotExistId() {
        when(commentRepository.existsById(comment.getId())).thenReturn(false);

        Exception exception = assertThrows(CommentNotFoundException.class, () -> commentService.update(comment));
        assertEquals("Comment by id '" + comment.getId() + "' is not found", exception.getMessage());
    }

    @Test
    public void findById() {
        doReturn(Optional.of(comment)).when(commentRepository).findById(Mockito.anyString());

        assertEquals(comment, commentService.findById(comment.getId()));
    }

    @Test
    public void findByIdIfNotExist() {
        Exception exception = assertThrows(CommentNotFoundException.class,
                () -> commentService.findById(comment.getId()));
        assertEquals("Comment by id '" + comment.getId() + "' is not found", exception.getMessage());
    }

    @Test
    public void findAll() {
        List<Comment> comments = Stream.of(comment, comment2).collect(Collectors.toList());

        when(commentRepository.findAll()).thenReturn(comments);
        assertEquals(comments, commentService.findAll());
    }

    @Test
    public void findAllAndSortById() {
        List<Comment> comments = Stream.of(comment, comment2, comment3).collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> expected = comments.stream()
                .sorted(Comparator.comparing(Comment::getId))
                .collect(Collectors.toList());

        assertNotEquals(comments, commentService.findAllAndSortById());
        assertEquals(expected, commentService.findAllAndSortById());
    }

    @Test
    public void findAllAndSortByText() {
        List<Comment> comments = Stream.of(comment, comment2, comment3).collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> expected = comments.stream()
                .sorted(Comparator.comparing(Comment::getText))
                .collect(Collectors.toList());

        assertEquals(expected, commentService.findAllAndSortByText());
    }

    @Test
    public void findAllAndSortByUserId() {
        List<Comment> comments = Stream.of(comment, comment2, comment3).collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> expected = comments.stream()
                .sorted(Comparator.comparing(Comment::getUserId))
                .collect(Collectors.toList());

        assertEquals(expected, commentService.findAllAndSortByUserId());
    }

    @Test
    public void findAllAndSortByPostId() {
        List<Comment> comments = Stream.of(comment, comment2, comment3).collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> expected = comments.stream()
                .sorted(Comparator.comparing(Comment::getPostId))
                .collect(Collectors.toList());

        assertEquals(expected, commentService.findAllAndSortByPostId());
    }

    @Test
    public void findAllAndSortByPublished() {
        List<Comment> comments = Stream.of(comment, comment2, comment3).collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> expected = comments.stream()
                .sorted(Comparator.comparing(Comment::getPublished))
                .collect(Collectors.toList());

        assertEquals(expected, commentService.findAllAndSortByPublished());
    }

    @Test
    public void findAllBySortType() {
        List<Comment> comments = Stream.of(comment, comment2, comment3).collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> sortedByIdExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getId))
                .collect(Collectors.toList());

        List<Comment> sortedByTextExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getText))
                .collect(Collectors.toList());

        List<Comment> sortedByUserIdExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getUserId))
                .collect(Collectors.toList());

        List<Comment> sortedByPostIdExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getPostId))
                .collect(Collectors.toList());

        List<Comment> sortedByPublishedExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getPublished))
                .collect(Collectors.toList());

        assertEquals(sortedByIdExpected, commentService.findAllBySortType("id"));
        assertEquals(sortedByTextExpected, commentService.findAllBySortType("text"));
        assertEquals(sortedByUserIdExpected, commentService.findAllBySortType("userId"));
        assertEquals(sortedByPostIdExpected, commentService.findAllBySortType("postId"));
        assertEquals(sortedByPublishedExpected, commentService.findAllBySortType("published"));
    }

    @Test
    public void findAllBySortTypeAndDesc() {
        List<Comment> comments = Stream.of(comment, comment2, comment3).collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> sortByIdAndDescExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getId).reversed())
                .collect(Collectors.toList());

        List<Comment> sortByTextAndDescExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getText).reversed())
                .collect(Collectors.toList());

        List<Comment> sortByUserIdAndDescExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getUserId).reversed())
                .collect(Collectors.toList());

        List<Comment> sortByPostIdAndDescExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getPostId).reversed())
                .collect(Collectors.toList());

        List<Comment> sortByPublishedAndDescExpected = comments.stream()
                .sorted(Comparator.comparing(Comment::getPublished).reversed())
                .collect(Collectors.toList());

        assertEquals(sortByIdAndDescExpected, commentService.findAllByTypeAndDesc("id", true));
        assertEquals(sortByTextAndDescExpected, commentService.findAllByTypeAndDesc("text", true));
        assertEquals(sortByUserIdAndDescExpected, commentService.findAllByTypeAndDesc("userId", true));
        assertEquals(sortByPostIdAndDescExpected, commentService.findAllByTypeAndDesc("postId", true));
        assertEquals(sortByPublishedAndDescExpected, commentService.findAllByTypeAndDesc("published", true));
    }

    @Test
    public void findAllWithLimitOffsetSortAndDesc() {
        List<Comment> comments = Stream.of(comment, comment2, comment3).collect(Collectors.toList());
        when(commentRepository.findAll()).thenReturn(comments);

        List<Comment> test1 = comments.stream()
                .sorted(Comparator.comparing(Comment::getId))
                .limit(2)
                .skip(1)
                .collect(Collectors.toList());

        List<Comment> test2 = comments.stream()
                .sorted(Comparator.comparing(Comment::getText).reversed())
                .limit(3)
                .collect(Collectors.toList());

        List<Comment> test3 = comments.stream()
                .sorted(Comparator.comparing(Comment::getUserId))
                .limit(1)
                .skip(2)
                .collect(Collectors.toList());

        List<Comment> test4 = comments.stream()
                .sorted(Comparator.comparing(Comment::getPostId))
                .limit(2)
                .skip(1)
                .collect(Collectors.toList());

        List<Comment> test5 = comments.stream()
                .sorted(Comparator.comparing(Comment::getPublished).reversed())
                .limit(2)
                .skip(1)
                .collect(Collectors.toList());

        assertEquals(test1, commentService.findAll(2, 1, "id", false));
        assertEquals(test2, commentService.findAll(3, 0, "text", true));
        assertEquals(test3, commentService.findAll(1, 2, "userId", false));
        assertEquals(test4, commentService.findAll(2, 1, "postId", false));
        assertEquals(test5, commentService.findAll(2, 1, "published", true));
    }

    @Test
    public void deleteById() {
        List<Comment> comments = new ArrayList<>();
        comments.add(comment);

        when(commentRepository.existsById(comment.getId())).thenReturn(true);
        doAnswer(invocation -> {
            comments.remove(comment);
            return null;
        }).when(commentRepository).deleteById(comment.getId());
        commentRepository.deleteById(comment.getId());

        assertEquals(0, comments.size());
    }

    @Test
    public void deleteByIdWithException() {
        Exception exception = assertThrows(CommentNotFoundException.class,
                () -> commentService.deleteById(comment.getId()));
        assertEquals("Comment by id '" + comment.getId() + "' is not found", exception.getMessage());
    }
}
