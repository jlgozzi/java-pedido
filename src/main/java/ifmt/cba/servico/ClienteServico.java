package ifmt.cba.servico;

import java.util.List;

import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.negocio.ClienteNegocio;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.PersistenciaException;
import ifmt.cba.servico.util.MensagemErro;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;

@Path("/cliente")
public class ClienteServico {

  private static ClienteNegocio clienteNegocio;
  private static ClienteDAO clienteDAO;

  static {
    try {
      clienteDAO = new ClienteDAO(FabricaEntityManager.getEntityManagerProducao());
      clienteNegocio = new ClienteNegocio(clienteDAO);
    } catch (PersistenciaException e) {
      e.printStackTrace();
    }
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response adicionar(ClienteDTO clienteDTO) {
    ResponseBuilder resposta;
    try {
      clienteNegocio.inserir(clienteDTO);
      ClienteDTO clienteDTOTemp = clienteNegocio.pesquisaParteNome(clienteDTO.getNome()).get(0);
      resposta = Response.ok(clienteDTOTemp);
    } catch (Exception ex) {
      resposta = Response.status(400).entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response alterar(ClienteDTO clienteDTO) {
    ResponseBuilder resposta;
    try {
      clienteNegocio.alterar(clienteDTO);
      ClienteDTO clienteDTOTemp = clienteNegocio.pesquisaCodigo(clienteDTO.getCodigo());
      resposta = Response.ok(clienteDTOTemp);
    } catch (Exception ex) {
      resposta = Response.status(400).entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  @DELETE
  @Path("/{codigo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response excluir(@PathParam("codigo") int codigo) {
    ResponseBuilder resposta;
    try {
      clienteNegocio.excluir(codigo);
      resposta = Response.noContent();
    } catch (Exception ex) {
      resposta = Response.status(400).entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  @GET
  @Path("/codigo/{codigo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response buscarClientePorCodigo(@PathParam("codigo") int codigo) {
    ResponseBuilder resposta;
    try {
      ClienteDTO clienteDTO = clienteNegocio.pesquisaCodigo(codigo);
      resposta = Response.ok(clienteDTO);
    } catch (Exception ex) {
      resposta = Response.status(400).entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  @GET
  @Path("/nome/{nome}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response buscarClientePorNome(@PathParam("nome") String nome) {
    ResponseBuilder resposta;
    try {
      List<ClienteDTO> listaClienteDTO = clienteNegocio.pesquisaParteNome(nome);
      resposta = Response.ok(listaClienteDTO);
    } catch (Exception ex) {
      resposta = Response.status(400).entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }
}
