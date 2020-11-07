package com.reckue.post.model.node;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Class PollNode is responsible for the voting system.
 *
 * @author Viktor Grigoriev
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class PollNode implements Parent {

    @Id
    private String id;
    private String title;
    private List<String> items;
}
