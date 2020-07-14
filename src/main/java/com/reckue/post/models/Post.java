package com.reckue.post.models;

import com.reckue.post.models.nodes.Parent;
import com.reckue.post.models.types.PostStatusType;
import com.reckue.post.models.types.StatusType;
import com.reckue.post.utils.NodeContent;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Class Post is responsible for model that works with posts.
 *
 * @author Iveri Narozashvili
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Post {

    @Id
    private String id;

    private String title;
    private List<Node> nodes;
    private String source;
    private String userId;
    private List<Tag> tags;
    private long published;
    private long changed;
    private PostStatusType status;
}
