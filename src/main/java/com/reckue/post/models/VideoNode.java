package com.reckue.post.models;

import com.reckue.post.utils.NodeContent;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class VideoNode is responsible for displaying video.
 *
 * @author Iveri Narozashvili
 */
@Data
@Document
public class VideoNode implements NodeContent {

    private String videoUrl;
}
