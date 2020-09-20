package com.varkashy.apple.encrypt.resources;

import com.varkashy.apple.encrypt.api.SimpleMathsApi;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The entry context for the API is defined as AverageDeviation
 */
@Path("/AverageDeviation")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SimpleMathsResource {

    public SimpleMathsResource(SimpleMathsApi simpleMathsApi){
        this._simpleMathsApi = simpleMathsApi;
    }
    private final SimpleMathsApi _simpleMathsApi;
    /*
    This could simply be a post call with context being Average Deviation
    However, considering the endpoints are more "Rest-Like" than "Rest-ful"
    having the verb pushAndRecalculate might be ok
     */

    /**
     * Endpoint for returning running average and standard deviation
     * @param number - the new number in the stream
     * @return String representation of AverageResponseDto Object
     */
    @POST
    @Path("/pushAndRecalculate")
    public String pushAndRecalculate(Integer number){
        return _simpleMathsApi.pushAndRecalculate(number).toString();
    }

    /**
     * Endpoint for getting the running mean and standard deviation in encrypted format
     * @param number - the new number in the stream
     * @return Encrypted String representation of AverageResponseDTO object
     */
    @POST
    @Path("/pushRecalculateEncrypt")
    public Response pushRecalculateEncrypt(Integer number){
        try {
            return Response.ok(_simpleMathsApi.pushRecalculateEncrypt(number).toString()).build();
        }
        catch (Exception exception){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Decrypts the provided encrypted value
     * @param encryptedValue - encrypted value that needs to be decrypted
     * @return Plain text for the decrypted value
     */
    @GET
    @Path("/decrypt/{encryptedValue}")
    public Response decrypt(@PathParam("encryptedValue") String encryptedValue){
        try {
            return Response.ok(_simpleMathsApi.decrypt(encryptedValue)).build();
        } catch (Exception exception) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
