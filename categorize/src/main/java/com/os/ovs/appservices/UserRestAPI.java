package com.os.ovs.appservices;


import java.util.List;

public interface UserRestAPI {



    @Api(value = "user", description = "the findUserDetails API")


    @ApiOperation(value = "Find user by ID", nickname = "getUserDetails", notes = "Returns a single Person", response = User.class, tags={ "user", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation - retrieves all users in a location", response = User.class),
            @ApiResponse(code = 400, message = "Invalid User ID supplied"),
            @ApiResponse(code = 404, message = "Location not found") })
    @RequestMapping(value = "/user/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)

    List<User> getUserDetails(@ApiParam(value = "ID of User to return", required = true) @PathVariable("id") Long id);

}
