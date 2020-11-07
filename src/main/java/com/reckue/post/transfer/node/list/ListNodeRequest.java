package com.reckue.post.transfer.node.list;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.reckue.post.model.type.NodeType;
import com.reckue.post.transfer.node.NodeParentRequest;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Class ListNodeRequest represents an incoming DTO for adding type of node such as a list of contents.
 *
 * @author Kamila Meshcheryakova
 */
@Builder
@Data
public class ListNodeRequest implements NodeParentRequest {

    @NotNull
    private List<String> content;

    @NotNull
    private NodeType type;

    @JsonCreator
    public ListNodeRequest(@NotNull List<String> content,
                           @NotNull NodeType type) {
        this.content = content;
        this.type = type;
    }
}
