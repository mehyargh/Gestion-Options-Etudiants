package ressources;

import entities.Option;
import metiers.OptionBusiness;
import utilities.Services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/options")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OptionRessource {

    private final OptionBusiness business = Services.OPTIONS;

    // 1) POST /options  -> 200 | 404
    @POST
    public Response createOption(Option option) {
        if (option == null || !business.addOption(option)) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }


    // 2) GET /options  et  3) GET /options?domaine=Mathématiques
    @GET
    public Response getOptions(@QueryParam("domaine") String domaine) {
        List<Option> liste;
        if (domaine == null || domaine.isEmpty()) {
            liste = business.getListeOptions();
        } else {
            liste = business.getOptionsByDomaine(domaine);
        }
        return Response.ok(liste).build();
    }
    // 6) GET /options/{code}  -> 200 | 404
    @GET
    @Path("/{code}")
    public Response getOption(@PathParam("code") int code) {
        Option option = business.getOptionByCode(code);
        if (option == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }

    // 4) DELETE /options/{code}  -> 204 | 404
    @DELETE
    @Path("/{code}")
    public Response deleteOption(@PathParam("code") int code) {
        if (!business.deleteOption(code)) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    // 5) PUT /options/{code}  -> 200 | 404
    @PUT
    @Path("/{code}")
    public Response updateOption(@PathParam("code") int code, Option option) {
        if (option == null || !business.updateOption(code, option)) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(option).build();
    }
}
