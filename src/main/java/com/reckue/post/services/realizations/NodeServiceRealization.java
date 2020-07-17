package com.reckue.post.services.realizations;

import com.reckue.post.exceptions.ReckueIllegalArgumentException;
import com.reckue.post.exceptions.models.nodes.NodeNotFoundException;
import com.reckue.post.models.Node;
import com.reckue.post.repositories.NodeRepository;
import com.reckue.post.services.NodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class NodeServiceRealization represents realization of NodeService.
 *
 * @author Kamila Meshcheryakova
 */
@Service
@RequiredArgsConstructor
public class NodeServiceRealization implements NodeService {

    private final NodeRepository nodeRepository;

    /**
     * This method is used to create an object of class Node.
     *
     * @param node object of class Node
     * @return node object of class Node
     */
    @Override
    public Node create(Node node) {
        return nodeRepository.save(node);
    }

    /**
     * This method is used to update data in an object of class Node.
     * Throws {@link NodeNotFoundException} in case
     * if such object isn't contained in database.
     * Throws {@link ReckueIllegalArgumentException} in case
     * if parameter equals null.
     *
     * @param node object of class Node
     * @return node object of class Node
     */
    @Override
    public Node update(Node node) {
        if (node.getId() == null) {
            throw new ReckueIllegalArgumentException("The parameter is null");
        }
        Node savedNode = nodeRepository
                .findById(node.getId())
                .orElseThrow(() -> new NodeNotFoundException(node.getId()));
        savedNode.setUserId(node.getUserId());
        savedNode.setType(node.getType());
        savedNode.setSource(node.getSource());
        savedNode.setStatus(node.getStatus());

        return nodeRepository.save(savedNode);
    }

    /**
     * This method is used to get all objects of class Node.
     *
     * @return list of objects of class Node
     */
    @Override
    public List<Node> findAll() {
        return nodeRepository.findAll();
    }

    /**
     * This method is used to get all objects of class Node by parameters.
     *
     * @param limit  quantity of objects
     * @param offset quantity to skip
     * @param sort   parameter for sorting
     * @param desc   sorting descending
     * @return list of given quantity of objects of class Node with a given offset
     * sorted by the selected parameter for sorting in descending order
     */
    @Override
    public List<Node> findAll(Integer limit, Integer offset, String sort, Boolean desc) {
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
     * @return list of objects of class Node sorted by the selected parameter for sorting
     * in descending order
     */
    public List<Node> findAllByTypeAndDesc(String sort, boolean desc) {
        if (desc) {
            List<Node> nodes = findAllBySortType(sort);
            Collections.reverse(nodes);
            return nodes;
        }
        return findAllBySortType(sort);
    }

    /**
     * This method is used to sort objects by type.
     *
     * @param sort type of sorting: type, status, source, createdDate, modificationDate, userId default - id
     * @return list of objects of class Node sorted by the selected parameter for sorting
     */
    public List<Node> findAllBySortType(String sort) {
        switch (sort) {
            case "type":
                return findAllAndSortByType();
            case "status":
                return findAllAndSortByStatus();
            case "source":
                return findAllAndSortBySource();
            case "createdDate":
                return findAllAndSortByCreatedDate();
            case "modificationDate":
                return findAllAndSortByModificationDate();
            case "userId":
                return findAllAndSortByUserId();
            case "id":
                return findAllAndSortById();
        }
        throw new ReckueIllegalArgumentException("Such field as " + sort + " doesn't exist");
    }

    /**
     * This method is used to sort objects by modificationDate.
     *
     * @return list of objects of class Node sorted by modificationDate
     */
    private List<Node> findAllAndSortByModificationDate() {
        return findAll().stream()
                .sorted(Comparator.comparing(Node::getModificationDate))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by id.
     *
     * @return list of objects of class Node sorted by id
     */
    public List<Node> findAllAndSortById() {
        return findAll().stream()
                .sorted(Comparator.comparing(Node::getId))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by status.
     *
     * @return list of objects of class Node sorted by status
     */
    public List<Node> findAllAndSortByStatus() {
        return findAll().stream()
                .sorted(Comparator.comparing(Node::getStatus))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by type.
     *
     * @return list of objects of class Node sorted by type
     */
    public List<Node> findAllAndSortByType() {
        return findAll().stream()
                .sorted(Comparator.comparing(Node::getType))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by userId.
     *
     * @return list of objects of class Node sorted by userId
     */
    public List<Node> findAllAndSortByUserId() {
        return findAll().stream()
                .sorted(Comparator.comparing(Node::getUserId))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by source.
     *
     * @return list of objects of class Node sorted by source
     */
    public List<Node> findAllAndSortBySource() {
        return findAll().stream()
                .sorted(Comparator.comparing(Node::getSource))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to sort objects by createdDate.
     *
     * @return list of objects of class Node sorted by createdDate
     */
    public List<Node> findAllAndSortByCreatedDate() {
        return findAll().stream()
                .sorted(Comparator.comparing(Node::getCreatedDate))
                .collect(Collectors.toList());
    }

    /**
     * This method is used to get an object by id.
     * Throws {@link NodeNotFoundException} in case if such object isn't contained in database.
     *
     * @param id object
     * @return post object of class Node
     */
    @Override
    public Node findById(String id) {
        return nodeRepository.findById(id).orElseThrow(
                () -> new NodeNotFoundException(id));
    }

    /**
     * This method is used to delete an object by id.
     * Throws {@link NodeNotFoundException} in case if such object isn't contained in database.
     *
     * @param id object
     */
    @Override
    public void deleteById(String id) {
        if (nodeRepository.existsById(id)) {
            nodeRepository.deleteById(id);
        } else {
            throw new NodeNotFoundException(id);
        }
    }

    /**
     * This method is used to delete all nodes.
     */
    @Deprecated
    @Override
    public void deleteAll() {
        nodeRepository.deleteAll();
    }
}
