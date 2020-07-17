package com.reckue.post.services.realizations;


import com.reckue.post.exceptions.ReckueIllegalArgumentException;
import com.reckue.post.exceptions.models.comment.CommentAlreadyExistsException;
import com.reckue.post.exceptions.models.comment.CommentNotFoundException;
import com.reckue.post.models.Comment;
import com.reckue.post.repositories.CommentRepository;
import com.reckue.post.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class CommentServiceRealization represents realization of CommentService.
 *
 * @author Artur Magomedov
 */
@Service
@RequiredArgsConstructor
public class CommentServiceRealization implements CommentService {

    private final CommentRepository commentRepository;

    /**
     * This method is used to create an object of class Comment.
     *
     * @param comment object of class Comment
     * @return comment object of class Comment
     */
    @Override
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * This method is used to update data in an object of class Comment.
     * Throws {@link CommentNotFoundException} in case
     * if such object isn't contained in database.
     * Throws {@link ReckueIllegalArgumentException} in case
     * if such parameter is null.
     *
     * @param comment object of class Comment
     * @return comment object of class Comment
     */
    @Override
    public Comment update(Comment comment) {
        if (comment.getId() == null) {
            throw new ReckueIllegalArgumentException("The parameter is null");
        }
        Comment savedComment = commentRepository
                .findById(comment.getId())
                .orElseThrow(() -> new CommentNotFoundException(comment.getId()));
        savedComment.setText(comment.getText());
        savedComment.setPostId(comment.getPostId());
        savedComment.setUserId(comment.getUserId());
        savedComment.setComments(comment.getComments());

        return commentRepository.save(savedComment);
    }

    /**
     * This method is used to get all objects of class Comment.
     *
     * @return list of objects of class Comment
     */
    @Override
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    /**
     * This method is used to get all objects of class Comment by parameters.
     *
     * @param limit  quantity of objects
     * @param offset quantity to skip
     * @param sort   parameter for sorting
     * @param desc   sorting descending
     * @return list of objects of class Comment
     */
    @Override
    public List<Comment> findAll(Integer limit, Integer offset, String sort, Boolean desc) {
        if (limit == null) limit = 10;
        if (offset == null) offset = 0;
        if (StringUtils.isEmpty(sort)) sort = "id";
        if (desc == null) desc = false;

        if (limit < 0 || offset < 0) {
            throw new ReckueIllegalArgumentException("Limit or offset is incorrect");
        }
        return findAllByTypeAndDesc(sort, desc).stream()
                .limit(limit)
                .skip(offset)
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects in descending order by type.
     *
     * @param sort parameter for sorting
     * @param desc sorting descending
     * @return list of objects of class Comment sorted by the selected parameter for sorting
     * in descending order
     */
    public List<Comment> findAllByTypeAndDesc(String sort, boolean desc) {
        if (desc) {
            List<Comment> comments = findAllBySortType(sort);
            Collections.reverse(comments);
            return comments;
        }
        return findAllBySortType(sort);
    }

    /**
     * This method is used to sort objects by type.
     *
     * @param sort type of sorting: id, text, userId, postId, createdDate or modificationDate
     * @return list of objects of class Comment sorted by the selected parameter for sorting
     */
    public List<Comment> findAllBySortType(String sort) {
        switch (sort) {
            case "id":
                return findAllAndSortById();
            case "text":
                return findAllAndSortByText();
            case "userId":
                return findAllAndSortByUserId();
            case "postId":
                return findAllAndSortByPostId();
            case "createdDate":
                return findAllAndSortByCreatedDate();
            case "modificationDate":
                return findAllAndSortByModificationDate();
        }
        throw new ReckueIllegalArgumentException("Such field as " + sort + " doesn't exist");
    }

    /**
     * This method is used to sort objects by modificationDate.
     *
     * @return list of objects of class Comment sorted by modificationDate
     */
    private List<Comment> findAllAndSortByModificationDate() {
        return findAll().stream()
                .sorted(Comparator.comparing(Comment::getModificationDate))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by id.
     *
     * @return list of objects of class Comment sorted by id
     */
    public List<Comment> findAllAndSortById() {
        return findAll().stream()
                .sorted(Comparator.comparing(Comment::getId))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by text.
     *
     * @return list of objects of class Comment sorted by text
     */
    public List<Comment> findAllAndSortByText() {
        return findAll().stream()
                .sorted(Comparator.comparing(Comment::getText))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by user id.
     *
     * @return list of objects of class Comment sorted by user id
     */
    public List<Comment> findAllAndSortByUserId() {
        return findAll().stream()
                .sorted(Comparator.comparing(Comment::getUserId))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by post id.
     *
     * @return list of objects of class Comment sorted by post id
     */
    public List<Comment> findAllAndSortByPostId() {
        return findAll().stream()
                .sorted(Comparator.comparing(Comment::getPostId))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by createdDate.
     *
     * @return list of objects of class Comment sorted by createdDate
     */
    public List<Comment> findAllAndSortByCreatedDate() {
        return findAll().stream()
                .sorted(Comparator.comparing(Comment::getCreatedDate))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to get an object by id.
     * Throws {@link CommentNotFoundException} in case if such object isn't contained in database.
     *
     * @param id object
     * @return object of class Comment
     */
    @Override
    public Comment findById(String id) {
        return commentRepository.findById(id).orElseThrow(
//                () -> new ModelNotFoundException("Comment by id " + id + " is not found"));
                () -> new CommentNotFoundException(id));
    }

    /**
     * This method is used to delete an object by id.
     * Throws {@link CommentNotFoundException} in case
     * if such object isn't contained in database.
     *
     * @param id object
     */
    @Override
    public void deleteById(String id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new CommentNotFoundException(id);
        }
    }
}
