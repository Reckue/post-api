package com.reckue.post.controllers;

import com.reckue.post.controllers.apis.NodeApi;
import com.reckue.post.exceptions.ReckueUnauthorizedException;
import com.reckue.post.models.Node;
import com.reckue.post.services.NodeService;
import com.reckue.post.transfers.NodeRequest;
import com.reckue.post.transfers.NodeResponse;
import com.reckue.post.utils.converters.NodeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.reckue.post.utils.converters.NodeConverter.convert;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

/**
 * Class NodeController is responsible for processing incoming requests.
 *
 * @author Viktor Grigoriev
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/nodes")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class NodeController implements NodeApi {

    private final NodeService nodeService;

    /**
     * This type of request allows to create and process it using the converter.
     * Throws {@link ReckueUnauthorizedException} in case if token is absent.
     *
     * @param nodeRequest the object of class NodeRequest
     * @return the object of class NodeResponse
     */
    @PostMapping
    // TODO: add parentId as PathVariable and delete it from request
    //  (you don't need to enter a parentId in update method)
    public NodeResponse create(@RequestBody @Valid NodeRequest nodeRequest, HttpServletRequest request) {
        log.info("{}", nodeRequest);
        String token;
        try {
            token = request.getHeader(AUTHORIZATION).substring(7);
        } catch (NullPointerException e) {
            throw new ReckueUnauthorizedException("Token missing");
        }
        return convert(nodeService.create(convert(nodeRequest), token));
    }

    /**
     * This type of request allows to update by id the object and process it using the converter.
     * Throws {@link ReckueUnauthorizedException} in case if token is absent.
     *
     * @param id          the object identifier
     * @param nodeRequest the object of class NodeRequest
     * @return the object of class NodeResponse
     */
    @PutMapping("/{id}")
    public NodeResponse update(@PathVariable String id,
                               @RequestBody @Valid NodeRequest nodeRequest,
                               HttpServletRequest request) {
        String token;
        try {
            token = request.getHeader(AUTHORIZATION).substring(7);
        } catch (NullPointerException e) {
            throw new ReckueUnauthorizedException("Token missing");
        }
        Node node = convert(nodeRequest);
        node.setId(id);
        return convert(nodeService.update(node, token));
    }

    /**
     * This type of request allows to find all the objects
     * that meet the requirements, process their using the converter.
     *
     * @param limit  quantity of objects
     * @param offset quantity to skip
     * @param sort   parameter for sorting
     * @param desc   sorting descending
     * @return list of given quantity of objects of class NodeResponse with a given offset
     * sorted by the selected parameter for sorting in descending order
     */
    @GetMapping
    public List<NodeResponse> findAll(@RequestParam(required = false) Integer limit,
                                      @RequestParam(required = false) Integer offset,
                                      @RequestParam(required = false) String sort,
                                      @RequestParam(required = false) Boolean desc) {

        return nodeService.findAll(limit, offset, sort, desc).stream()
                .map(NodeConverter::convert)
                .collect(Collectors.toList());
    }

    /**
     * This type of request allows to get all the objects by user id, process it using the converter.
     *
     * @param userId user identifier
     * @param limit  quantity of objects
     * @param offset quantity to skip
     * @return list of objects of class NodeResponse
     */
    @GetMapping("/userId/{userId}")
    public List<NodeResponse> findAllByUserId(@PathVariable String userId,
                                              @RequestParam(required = false) Integer limit,
                                              @RequestParam(required = false) Integer offset) {
        return nodeService.findAllByUserId(userId, limit, offset).stream()
                .map(NodeConverter::convert)
                .collect(Collectors.toList());
    }

    /**
     * This type of request allows to get the object by id, process it using the converter.
     *
     * @param id the object identifier
     * @return the object of class NodeResponse
     */
    @GetMapping("/{id}")
    public NodeResponse findById(@PathVariable String id) {
        return convert(nodeService.findById(id));
    }

    /**
     * This type of request allows to delete the object by id.
     * Throws {@link ReckueUnauthorizedException} in case if token is absent.
     *
     * @param id the object identifier
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id, HttpServletRequest request) {
        String token;
        try {
            token = request.getHeader(AUTHORIZATION).substring(7);
        } catch (NullPointerException e) {
            throw new ReckueUnauthorizedException("Token missing");
        }
        nodeService.deleteById(id, token);
    }
}
