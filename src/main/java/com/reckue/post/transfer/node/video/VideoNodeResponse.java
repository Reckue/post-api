package com.reckue.post.transfer.node.video;

import com.reckue.post.transfer.node.NodeParentResponse;
import lombok.*;

/**
 * Class VideoNodeResponse represents an outgoing DTO for receiving type of node such as a video.
 *
 * @author Kamila Meshcheryakova
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VideoNodeResponse implements NodeParentResponse {

    private String videoUrl;
}
