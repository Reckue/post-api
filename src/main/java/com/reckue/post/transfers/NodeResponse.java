package com.reckue.post.transfers;

import com.reckue.post.models.NodeType;
import com.reckue.post.models.StatusType;
import com.reckue.post.utils.NodeContent;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

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

    @ApiModelProperty(notes = "Type of node content")
    private NodeContent content;

    @ApiModelProperty(notes = "The source used to write the node")
    private String source;

    @ApiModelProperty(notes = "Node author")
    private String username;

    @ApiModelProperty(notes = "Publication date")
    private long published;

    @ApiModelProperty(notes = "Node activity status")
    private StatusType status;
}
