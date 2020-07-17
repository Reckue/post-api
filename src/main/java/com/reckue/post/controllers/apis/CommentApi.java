package com.reckue.post.controllers.apis;

import com.reckue.post.transfers.CommentRequest;
import com.reckue.post.transfers.CommentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.List;

/**
 * Interface CommentApi allows to post annotations for swagger.
 *
 * @author Artur Magomedov
 */
@Api(tags = {"/comments"})
public interface CommentApi {

    @ApiOperation(value = "Add a comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The comment successfully added"),
            @ApiResponse(code = 500, message = "Access to the resource you tried to obtain is not possible")})
    CommentResponse create(CommentRequest commentRequest);

    @ApiOperation(value = "Update a comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The comment successfully updated"),
            @ApiResponse(code = 400, message = "You need to fill in the fields of your request"),
            @ApiResponse(code = 404, message = "The resource you were trying to change is not found"),
            @ApiResponse(code = 500, message = "Access to the resource you tried to obtain is not possible")})
    CommentResponse update(String id, CommentRequest commentRequest);

    @ApiOperation(value = "View a list of available comments", response = CommentResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of comments successfully retrieved"),
            @ApiResponse(code = 400, message = "You need to change the parameters of your request"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Access to the resource you tried to obtain is not possible")})
    List<CommentResponse> findAll(Integer limit, Integer offset, String sort, Boolean desc);

    @ApiOperation(value = "Get a comment by id", response = CommentResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The comment successfully found"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Access to the resource you tried to obtain is not possible")})
    CommentResponse findById(String id);

    @ApiOperation(value = "Delete a comment")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The comment successfully deleted"),
            @ApiResponse(code = 404, message = "The resource you were trying to delete is not found"),
            @ApiResponse(code = 500, message = "Access to the resource you tried to obtain is not possible")})
    void deleteById(String id);
}
