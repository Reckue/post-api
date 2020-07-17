package com.reckue.post.transfers;

import com.reckue.post.models.types.NodeType;
import com.reckue.post.models.types.StatusType;
import com.reckue.post.transfers.nodes.NodeParentResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Class NodeResponse represents an outgoing DTO for receiving a node.
 *
 * @author Kamila Meshcheryakova
 */
@Data
@Builder
public class NodeResponse {

    @ApiModelProperty(notes = "The database generated node ID")
    private String id;

    @ApiModelProperty(notes = "Node type")
    private NodeType type;

    @ApiModelProperty(notes = "Sub node")
    private NodeParentResponse node;

    @ApiModelProperty(notes = "Identifier of the post")
    @NotNull
    private String postId;

    @Size(max = 128)
    @ApiModelProperty(notes = "The source used to write the node")
    private String source;

    @ApiModelProperty(notes = "Node author identifier")
    private String userId;

    @ApiModelProperty(notes = "Created date")
    private long createdDate;

    @ApiModelProperty(notes = "Modification date")
    private long modificationDate;

    @ApiModelProperty(notes = "Node activity status")
    private StatusType status;
}
