package com.reckue.post.services.realizations;

import com.reckue.post.exceptions.ModelAlreadyExistsException;
import com.reckue.post.exceptions.ModelNotFoundException;
import com.reckue.post.models.Post;
import com.reckue.post.repositories.PostRepository;
import com.reckue.post.services.PostService;
import com.reckue.post.utils.Generator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Class PostServiceRealization represents realization of PostService.
 *
 * @author Kamila Meshcheryakova
 */
@Service
@RequiredArgsConstructor
public class PostServiceRealization implements PostService {

    private final PostRepository postRepository;

    /**
     * This method is used to create an object of class Post.
     * Throws {@link ModelAlreadyExistsException} in case if such object already exists.
     *
     * @param post object of class Post
     * @return post object of class Post
     */
    @Override
    public Post create(Post post) {
        post.setId(Generator.id());
        if (!postRepository.existsById(post.getId())) {
            return postRepository.save(post);
        } else {
            throw new ModelAlreadyExistsException("Post already exists");
        }
    }

    /**
     * This method is used to update data in an object of class Post.
     * Throws {@link ModelNotFoundException} in case
     * if such object isn't contained in database.
     * Throws {@link IllegalArgumentException} in case
     * if parameter equals null.
     *
     * @param post object of class Post
     * @return post object of class Post
     */
    @Override
    public Post update(Post post) {
        if (post.getId() == null) {
            throw new IllegalArgumentException("The parameter is null");
        }
        if (!postRepository.existsById(post.getId())) {
            throw new ModelNotFoundException("Post by id " + post.getId() + " is not found");
        }
        Post savedPost = Post.builder()
                .id(post.getId())
                .username(post.getUsername())
                .title(post.getTitle())
                .nodes(post.getNodes())
                .source(post.getSource())
                .tags(post.getTags())
                .published(post.getPublished())
                .changed(post.getChanged())
                .status(post.getStatus())
                .build();
        return postRepository.save(savedPost);
    }

    /**
     * This method is used to get all objects of class Post.
     *
     * @return list of objects of class Post
     */
    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    /**
     * This method is used to get all objects of class Post by parameters.
     *
     * @param limit  quantity of objects
     * @param offset quantity to skip
     * @param sort   parameter for sorting
     * @param desc   sorting descending
     * @return list of given quantity of objects of class Post with a given offset
     * sorted by the selected parameter for sorting in descending order
     */
    @Override
    public List<Post> findAll(int limit, int offset, String sort, boolean desc) {
        if (limit < 0 || offset < 0) {
            throw new IllegalArgumentException("Limit or offset is incorrect");
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
     * @return list of objects of class Post sorted by the selected parameter for sorting
     * in descending order
     */
    public List<Post> findAllByTypeAndDesc(String sort, boolean desc) {
        if (desc) {
            List<Post> posts = findAllBySortType(sort);
            Collections.reverse(posts);
            return posts;
        }
        return findAllBySortType(sort);
    }

    /**
     * This method is used to sort objects by type.
     *
     * @param sort type of sorting: title, source, published, changed, status, default - id
     * @return list of objects of class Post sorted by the selected parameter for sorting
     */
    public List<Post> findAllBySortType(String sort) {
        switch (sort) {
            case "title":
                return findAllAndSortByTitle();
            case "source":
                return findAllAndSortBySource();
            case "published":
                return findAllAndSortByPublished();
            case "changed":
                return findAllAndSortByChanged();
            case "status":
                return findAllAndSortByStatus();
            case "id":
                return findAllAndSortById();
            case "username":
                return findAllAndSortByUsername();
        }
        throw new IllegalArgumentException("Such field as" + sort + " doesn't exist");
    }

    /**
     * This method is used to sort objects by id.
     *
     * @return list of objects of class Post sorted by id
     */
    public List<Post> findAllAndSortById() {
        return findAll().stream()
                .sorted(Comparator.comparing(Post::getId))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by type.
     *
     * @return list of objects of class Post sorted by type
     */
    public List<Post> findAllAndSortByTitle() {
        return findAll().stream()
                .sorted(Comparator.comparing(Post::getTitle))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by username.
     *
     * @return list of objects of class Post sorted by username
     */
    public List<Post> findAllAndSortByUsername() {
        return findAll().stream()
                .sorted(Comparator.comparing(Post::getUsername))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by source.
     *
     * @return list of objects of class Post sorted by source
     */
    public List<Post> findAllAndSortBySource() {
        return findAll().stream()
                .sorted(Comparator.comparing(Post::getSource))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by publication date.
     *
     * @return list of objects of class Post sorted by publication date
     */
    public List<Post> findAllAndSortByPublished() {
        return findAll().stream()
                .sorted(Comparator.comparing(Post::getPublished))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by date modified.
     *
     * @return list of objects of class Post sorted by date modified
     */
    public List<Post> findAllAndSortByChanged() {
        return findAll().stream()
                .sorted(Comparator.comparing(Post::getChanged))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by status.
     *
     * @return list of objects of class Post sorted by status
     */
    public List<Post> findAllAndSortByStatus() {
        return findAll().stream()
                .sorted(Comparator.comparing(Post::getStatus))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to get an object by id.
     * Throws {@link ModelNotFoundException} in case if such object isn't contained in database.
     *
     * @param id object
     * @return post object of class Post
     */
    @Override
    public Post findById(String id) {
        return postRepository.findById(id).orElseThrow(
                () -> new ModelNotFoundException("Post by id " + id + " is not found"));
    }

    /**
     * This method is used to delete an object by id.
     * Throws {@link ModelNotFoundException} in case if such object isn't contained in database.
     *
     * @param id object
     */
    @Override
    public void deleteById(String id) {
        if (postRepository.existsById(id)) {
            postRepository.deleteById(id);
        } else {
            throw new ModelNotFoundException("Post by id " + id + " is not found");
        }
    }
}
