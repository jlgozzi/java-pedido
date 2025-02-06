package ifmt.cba.servico;

import java.time.LocalDate;
import java.util.List;

import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.negocio.PedidoNegocio;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.ItemPedidoDAO;
import ifmt.cba.persistencia.PedidoDAO;
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

@Path("/pedido")
public class PedidoServico {

  private static PedidoNegocio pedidoNegocio;

  static {
    try {
      PedidoDAO pedidoDAO = new PedidoDAO(FabricaEntityManager.getEntityManagerProducao());
      ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(FabricaEntityManager.getEntityManagerProducao());
      ClienteDAO clienteDAO = new ClienteDAO(FabricaEntityManager.getEntityManagerProducao());
      pedidoNegocio = new PedidoNegocio(pedidoDAO, itemPedidoDAO, clienteDAO);
    } catch (PersistenciaException e) {
      e.printStackTrace();
    }
  }

  // Adicionar Pedido
  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response adicionar(PedidoDTO pedidoDTO) {
    ResponseBuilder resposta;
    try {
      // Insere o pedido e obtém o PedidoDTO criado
      PedidoDTO pedidoCriado = pedidoNegocio.inserir(pedidoDTO);

      // Define o link do pedido
      pedidoCriado.setLink("/pedido/codigo/" + pedidoCriado.getCodigo());

      // Retorna o pedido criado
      resposta = Response.ok();
      resposta.entity(pedidoCriado);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Alterar Pedido
  @PUT
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response alterar(PedidoDTO pedidoDTO) {
    ResponseBuilder resposta;
    try {
      pedidoNegocio.alterar(pedidoDTO);
      PedidoDTO pedidoDTOTemp = pedidoNegocio.pesquisaCodigo(pedidoDTO.getCodigo());
      pedidoDTOTemp.setLink("/pedido/codigo/" + pedidoDTOTemp.getCodigo());
      resposta = Response.ok();
      resposta.entity(pedidoDTOTemp);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Excluir Pedido
  @DELETE
  @Path("/{codigo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response excluir(@PathParam("codigo") int codigo) {
    ResponseBuilder resposta;
    try {
      PedidoDTO pedidoDTO = pedidoNegocio.pesquisaCodigo(codigo);
      if (pedidoDTO == null) {
        throw new Exception("Pedido não encontrado");
      }
      pedidoNegocio.excluir(pedidoDTO);
      resposta = Response.noContent();
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Buscar Pedido por Código
  @GET
  @Path("/codigo/{codigo}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response buscarPedidoPorCodigo(@PathParam("codigo") int codigo) {
    ResponseBuilder resposta;
    try {
      PedidoDTO pedidoDTO = pedidoNegocio.pesquisaCodigo(codigo);
      if (pedidoDTO == null) {
        throw new Exception("Pedido não encontrado");
      }
      pedidoDTO.setLink("/pedido/codigo/" + pedidoDTO.getCodigo());
      resposta = Response.ok();
      resposta.entity(pedidoDTO);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Buscar Pedido por Data
  @GET
  @Path("/data/{dataInicial}/{dataFinal}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response buscarPedidoPorData(@PathParam("dataInicial") String dataInicialStr,
      @PathParam("dataFinal") String dataFinalStr) {
    ResponseBuilder resposta;
    try {
      LocalDate dataInicial = LocalDate.parse(dataInicialStr);
      LocalDate dataFinal = LocalDate.parse(dataFinalStr);
      List<PedidoDTO> listaPedidoDTO = pedidoNegocio.pesquisaPorDataProducao(dataInicial, dataFinal);
      for (PedidoDTO pedidoDTO : listaPedidoDTO) {
        pedidoDTO.setLink("/pedido/codigo/" + pedidoDTO.getCodigo());
      }
      resposta = Response.ok();
      resposta.entity(listaPedidoDTO);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Buscar Pedido por Estado
  @GET
  @Path("/estado/{estado}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response buscarPedidoPorEstado(@PathParam("estado") EstadoPedidoDTO estado) {
    ResponseBuilder resposta;
    try {
      List<PedidoDTO> listaPedidoDTO = pedidoNegocio.pesquisaPorEstado(estado);
      for (PedidoDTO pedidoDTO : listaPedidoDTO) {
        pedidoDTO.setLink("/pedido/codigo/" + pedidoDTO.getCodigo());
      }
      resposta = Response.ok();
      resposta.entity(listaPedidoDTO);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Mudar estado do pedido para PRODUCAO
  @POST
  @Path("/mudarParaProducao")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response mudarPedidoParaProducao(PedidoDTO pedidoDTO) {
    ResponseBuilder resposta;
    try {
      pedidoNegocio.mudarPedidoParaProducao(pedidoDTO);
      PedidoDTO pedidoDTOTemp = pedidoNegocio.pesquisaCodigo(pedidoDTO.getCodigo());
      pedidoDTOTemp.setLink("/pedido/codigo/" + pedidoDTOTemp.getCodigo());
      resposta = Response.ok();
      resposta.entity(pedidoDTOTemp);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Mudar estado do pedido para PRONTO
  @POST
  @Path("/mudarParaPronto")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response mudarPedidoParaPronto(PedidoDTO pedidoDTO) {
    ResponseBuilder resposta;
    try {
      pedidoNegocio.mudarPedidoParaPronto(pedidoDTO);
      PedidoDTO pedidoDTOTemp = pedidoNegocio.pesquisaCodigo(pedidoDTO.getCodigo());
      pedidoDTOTemp.setLink("/pedido/codigo/" + pedidoDTOTemp.getCodigo());
      resposta = Response.ok();
      resposta.entity(pedidoDTOTemp);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Mudar estado do pedido para ENTREGA
  @POST
  @Path("/mudarParaEntrega")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response mudarPedidoParaEntrega(PedidoDTO pedidoDTO) {
    ResponseBuilder resposta;
    try {
      pedidoNegocio.mudarPedidoParaEntrega(pedidoDTO);
      PedidoDTO pedidoDTOTemp = pedidoNegocio.pesquisaCodigo(pedidoDTO.getCodigo());
      pedidoDTOTemp.setLink("/pedido/codigo/" + pedidoDTOTemp.getCodigo());
      resposta = Response.ok();
      resposta.entity(pedidoDTOTemp);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }

  // Mudar estado do pedido para CONCLUIDO
  @POST
  @Path("/mudarParaConcluido")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response mudarPedidoParaConcluido(PedidoDTO pedidoDTO) {
    ResponseBuilder resposta;
    try {
      pedidoNegocio.mudarPedidoParaConcluido(pedidoDTO);
      PedidoDTO pedidoDTOTemp = pedidoNegocio.pesquisaCodigo(pedidoDTO.getCodigo());
      pedidoDTOTemp.setLink("/pedido/codigo/" + pedidoDTOTemp.getCodigo());
      resposta = Response.ok();
      resposta.entity(pedidoDTOTemp);
    } catch (Exception ex) {
      resposta = Response.status(400);
      resposta.entity(new MensagemErro(ex.getMessage()));
    }
    return resposta.build();
  }
}
