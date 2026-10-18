package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;
import utilities.Services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/etudiants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EtudiantRessource {

    private final EtudiantBusiness etudiantBusiness = Services.ETUDIANTS;
    private final OptionBusiness optionBusiness = Services.OPTIONS;

    // 1) POST /etudiants  -> 200 | 404 (si l'option n'existe pas)
    @POST
    public Response createEtudiant(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null
                || !etudiantBusiness.addEtudiant(etudiant)) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // 2) GET /etudiants  -> 200
    @GET
    public Response getEtudiants() {
        return Response.ok(etudiantBusiness.getAllEtudiants()).build();
    }

    // 6) GET /etudiants/option?codeOption=1  -> 200 (XML) | 404
    @GET
    @Path("/option")
    @Produces(MediaType.APPLICATION_XML)
    public Response getEtudiantsByOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionBusiness.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        EtudiantList liste = new EtudiantList(etudiantBusiness.getEtudiantsByOption(option));
        return Response.ok(liste).build();
    }

    // 3) GET /etudiants/{id}  -> 200 | 404
    @GET
    @Path("/{id}")
    public Response getEtudiant(@PathParam("id") String id) {
        Etudiant etudiant = etudiantBusiness.getEtudiantByIdentifiant(id);
        if (etudiant == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }

    // 4) DELETE /etudiants/{id}  -> 204 | 404
    @DELETE
    @Path("/{id}")
    public Response deleteEtudiant(@PathParam("id") String id) {
        if (!etudiantBusiness.deleteEtudiant(id)) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.noContent().build();
    }

    // 5) PUT /etudiants/{id}  -> 200 | 404
    @PUT
    @Path("/{id}")
    public Response updateEtudiant(@PathParam("id") String id, Etudiant etudiant) {
        if (etudiant == null) {
            return Response.status(Status.NOT_FOUND).build();
        }
        // on rattache l'étudiant à l'option réellement stockée
        if (etudiant.getOption() != null) {
            Option option = optionBusiness.getOptionByCode(etudiant.getOption().getCodeOption());
            if (option == null) {
                return Response.status(Status.NOT_FOUND).build();
            }
            etudiant.setOption(option);
        }
        if (!etudiantBusiness.updateEtudiant(id, etudiant)) {
            return Response.status(Status.NOT_FOUND).build();
        }
        return Response.ok(etudiant).build();
    }
}