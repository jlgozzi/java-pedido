package ifmt.cba.execucao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import ifmt.cba.dto.BairroDTO;
import ifmt.cba.dto.ClienteDTO;
import ifmt.cba.dto.EstadoPedidoDTO;
import ifmt.cba.dto.GrupoAlimentarDTO;
import ifmt.cba.dto.ItemPedidoDTO;
import ifmt.cba.dto.PedidoDTO;
import ifmt.cba.dto.ProdutoDTO;
import ifmt.cba.negocio.BairroNegocio;
import ifmt.cba.negocio.ClienteNegocio;
import ifmt.cba.negocio.GrupoAlimentarNegocio;
import ifmt.cba.negocio.NegocioException;
import ifmt.cba.negocio.PedidoNegocio;
import ifmt.cba.negocio.ProdutoNegocio;
import ifmt.cba.persistencia.BairroDAO;
import ifmt.cba.persistencia.ClienteDAO;
import ifmt.cba.persistencia.FabricaEntityManager;
import ifmt.cba.persistencia.GrupoAlimentarDAO;
import ifmt.cba.persistencia.ItemPedidoDAO;
import ifmt.cba.persistencia.PedidoDAO;
import ifmt.cba.persistencia.PersistenciaException;
import ifmt.cba.persistencia.ProdutoDAO;

public class AppGerarDadosBD {
    public static void gerarBaseDados() {
        try {
            GrupoAlimentarDAO grupoAlimentarDAO = new GrupoAlimentarDAO(
                    FabricaEntityManager.getEntityManagerProducao());
            ProdutoDAO produtoDAO = new ProdutoDAO(FabricaEntityManager.getEntityManagerProducao());
            GrupoAlimentarNegocio grupoAlimentarNegocio = new GrupoAlimentarNegocio(grupoAlimentarDAO, produtoDAO);
    
            GrupoAlimentarDTO grupoDTO = new GrupoAlimentarDTO();
            grupoDTO.setNome("Carboidrato");
            grupoAlimentarNegocio.inserir(grupoDTO);

            grupoDTO = new GrupoAlimentarDTO();
            grupoDTO.setNome("Legumes");
            grupoAlimentarNegocio.inserir(grupoDTO);

            grupoDTO = new GrupoAlimentarDTO();
            grupoDTO.setNome("Proteinas");
            grupoAlimentarNegocio.inserir(grupoDTO);
            
            grupoDTO = new GrupoAlimentarDTO();
            grupoDTO.setNome("Verduras");
            grupoAlimentarNegocio.inserir(grupoDTO);

            List<GrupoAlimentarDTO> listaGrupo = grupoAlimentarNegocio.pesquisaParteNome("");
            System.out.println();
            System.out.println("Total de Grupos Alimentares: "+listaGrupo.size());
            System.out.println();            
            //------------------Produtos-----------------------------------
            ProdutoNegocio produtoNegocio = new ProdutoNegocio(produtoDAO);
            grupoDTO = grupoAlimentarNegocio.pesquisaCodigo(3); // proteina

            ProdutoDTO produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Alcatra bovina");
            produtoDTO.setEstoque(1000);
            produtoDTO.setEstoqueMinimo(100);
            produtoDTO.setCustoUnidade(2.0f);
            produtoDTO.setValorEnergetico(50);
            produtoDTO.setGrupoAlimentar(grupoDTO);
            produtoNegocio.inserir(produtoDTO);

            produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Costela suina");
            produtoDTO.setEstoque(30);
            produtoDTO.setEstoqueMinimo(50);
            produtoDTO.setCustoUnidade(1.5f);
            produtoDTO.setValorEnergetico(60);
            produtoDTO.setGrupoAlimentar(grupoDTO);
            produtoNegocio.inserir(produtoDTO);

            grupoDTO = grupoAlimentarNegocio.pesquisaCodigo(2); // Legumes

            produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Batata Inglesa");
            produtoDTO.setEstoque(400);
            produtoDTO.setEstoqueMinimo(300);
            produtoDTO.setCustoUnidade(1.0f);
            produtoDTO.setValorEnergetico(80);
            produtoDTO.setGrupoAlimentar(grupoDTO);
            produtoNegocio.inserir(produtoDTO);

            produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Batata Doce");
            produtoDTO.setEstoque(100);
            produtoDTO.setEstoqueMinimo(200);
            produtoDTO.setCustoUnidade(1.3f);
            produtoDTO.setValorEnergetico(70);
            produtoDTO.setGrupoAlimentar(grupoDTO);
            produtoNegocio.inserir(produtoDTO);

            grupoDTO = grupoAlimentarNegocio.pesquisaCodigo(1); // Carboidrato

            produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Arroz Branco");
            produtoDTO.setEstoque(1000);
            produtoDTO.setEstoqueMinimo(500);
            produtoDTO.setCustoUnidade(1.7f);
            produtoDTO.setValorEnergetico(100);
            produtoDTO.setGrupoAlimentar(grupoDTO);
            produtoNegocio.inserir(produtoDTO);

            produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Arroz Integral");
            produtoDTO.setEstoque(1000);
            produtoDTO.setEstoqueMinimo(500);
            produtoDTO.setCustoUnidade(1.9f);
            produtoDTO.setValorEnergetico(90);
            produtoDTO.setGrupoAlimentar(grupoDTO);
            produtoNegocio.inserir(produtoDTO);
            
            produtoDTO = new ProdutoDTO();
            produtoDTO.setNome("Fubá de Milho");
            produtoDTO.setEstoque(500);
            produtoDTO.setEstoqueMinimo(200);
            produtoDTO.setCustoUnidade(1.4f);
            produtoDTO.setValorEnergetico(75);
            produtoDTO.setGrupoAlimentar(grupoDTO);
            produtoNegocio.inserir(produtoDTO);

            List<ProdutoDTO> listaProdutos = produtoNegocio.pesquisaParteNome("");
            System.out.println("Total de Produtos Alimentares: "+listaProdutos.size());
            System.out.println(); 

            //------------------Bairro-------------------------
            BairroDAO bairroDAO = new BairroDAO(FabricaEntityManager.getEntityManagerProducao());
            BairroNegocio bairroNegocio = new BairroNegocio(bairroDAO);
    
            BairroDTO bairroDTO = new BairroDTO();
            bairroDTO.setNome("Centro");
            bairroDTO.setCustoEntrega(7.00f);
            bairroNegocio.inserir(bairroDTO);

            bairroDTO = new BairroDTO();
            bairroDTO.setNome("Coxipo");
            bairroDTO.setCustoEntrega(8.00f);
            bairroNegocio.inserir(bairroDTO);

            List<BairroDTO> listaBairros = bairroNegocio.pesquisaParteNome("");
            System.out.println("Total de Bairros: "+listaBairros.size());
            System.out.println(); 

            //------------------------Clientes------------------------------------------
            ClienteDAO clienteDAO = new ClienteDAO(FabricaEntityManager.getEntityManagerProducao());
            ClienteNegocio clienteNegocio = new ClienteNegocio(clienteDAO);
                
            bairroDTO = bairroNegocio.pesquisaParteNome("Centro").get(0);
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.setNome("Cliente 01");
            clienteDTO.setCPF("234.345.656-55");
            clienteDTO.setRG("234567-9");
            clienteDTO.setTelefone("65 99999-7070");
            clienteDTO.setLogradouro("Rua das flores");
            clienteDTO.setNumero("123");
            clienteDTO.setPontoReferencia("Proximo a nada");
            clienteDTO.setBairro(bairroDTO);
            clienteNegocio.inserir(clienteDTO);

            bairroDTO = bairroNegocio.pesquisaParteNome("Coxipo").get(0);
            clienteDTO = new ClienteDTO();
            clienteDTO.setNome("Cliente 02");
            clienteDTO.setCPF("123.432.678-99");
            clienteDTO.setRG("123456-8");
            clienteDTO.setTelefone("65 98888-3030");
            clienteDTO.setLogradouro("Rua das pedras");
            clienteDTO.setNumero("456");
            clienteDTO.setPontoReferencia("Final da rua");
            clienteDTO.setBairro(bairroDTO);
            clienteNegocio.inserir(clienteDTO);

            List<ClienteDTO> listaClientes = clienteNegocio.pesquisaParteNome("");
            System.out.println("Total de Clientes: "+listaClientes.size());
            System.out.println(); 

            //------------------------Pedidos-------------------------------------
            PedidoDAO pedidoDAO = new PedidoDAO(FabricaEntityManager.getEntityManagerProducao());
            ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO(FabricaEntityManager.getEntityManagerProducao());
            PedidoNegocio pedidoNegocio = new PedidoNegocio(pedidoDAO, itemPedidoDAO, clienteDAO);

            clienteDTO = clienteNegocio.pesquisaParteNome("Cliente 01").get(0);
            produtoDTO = produtoNegocio.pesquisaCodigo(1); //arroz branco
            List<ItemPedidoDTO> listaItens = new ArrayList<ItemPedidoDTO>();
            ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setProduto(produtoDTO);
            itemPedidoDTO.setQuantidadePorcao(2);
            listaItens.add(itemPedidoDTO);

            produtoDTO = produtoNegocio.pesquisaCodigo(2); //costela suina
            itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setProduto(produtoDTO);
            itemPedidoDTO.setQuantidadePorcao(1);
            listaItens.add(itemPedidoDTO);

            PedidoDTO pedidoDTO = new PedidoDTO();
            pedidoDTO.setDataPedido(LocalDate.now());
            pedidoDTO.setHoraPedido(LocalTime.now());
            pedidoDTO.setCliente(clienteDTO);
            pedidoDTO.setEstado(EstadoPedidoDTO.REGISTRADO);
            pedidoDTO.setListaItens(listaItens);
            pedidoNegocio.inserir(pedidoDTO);

            //--------------------------------------------------------------------
            
            clienteDTO = clienteNegocio.pesquisaParteNome("Cliente 02").get(0);
            produtoDTO = produtoNegocio.pesquisaCodigo(1); //arroz branco
            listaItens = new ArrayList<ItemPedidoDTO>();
            itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setProduto(produtoDTO);
            itemPedidoDTO.setQuantidadePorcao(3);
            listaItens.add(itemPedidoDTO);

            produtoDTO = produtoNegocio.pesquisaCodigo(3); //alcatra bovina
            itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setProduto(produtoDTO);
            itemPedidoDTO.setQuantidadePorcao(2);
            listaItens.add(itemPedidoDTO);

            pedidoDTO = new PedidoDTO();
            pedidoDTO.setDataPedido(LocalDate.now());
            pedidoDTO.setHoraPedido(LocalTime.now());
            pedidoDTO.setCliente(clienteDTO);
            pedidoDTO.setEstado(EstadoPedidoDTO.REGISTRADO);
            pedidoDTO.setListaItens(listaItens);
            pedidoNegocio.inserir(pedidoDTO);

            //----------------------------------------------------------------
            //--------------------------------------------------------------------
            
            clienteDTO = clienteNegocio.pesquisaParteNome("Cliente 01").get(0);
            produtoDTO = produtoNegocio.pesquisaCodigo(6); //arroz integral
            listaItens = new ArrayList<ItemPedidoDTO>();
            itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setProduto(produtoDTO);
            itemPedidoDTO.setQuantidadePorcao(4);
            listaItens.add(itemPedidoDTO);

            produtoDTO = produtoNegocio.pesquisaCodigo(3); //alcatra bovina
            itemPedidoDTO = new ItemPedidoDTO();
            itemPedidoDTO.setProduto(produtoDTO);
            itemPedidoDTO.setQuantidadePorcao(3);
            listaItens.add(itemPedidoDTO);

            pedidoDTO = new PedidoDTO();
            pedidoDTO.setDataPedido(LocalDate.now());
            pedidoDTO.setHoraPedido(LocalTime.now());
            pedidoDTO.setCliente(clienteDTO);
            pedidoDTO.setEstado(EstadoPedidoDTO.PRODUCAO);
            pedidoDTO.setListaItens(listaItens);
            pedidoNegocio.inserir(pedidoDTO);

            List<PedidoDTO> listaPedidos = pedidoNegocio.pesquisaPorEstado(EstadoPedidoDTO.REGISTRADO);
            System.out.println("Total de Pedidos: "+listaPedidos.size());
            System.out.println(); 
        } catch (PersistenciaException | NegocioException e) {
            e.printStackTrace();
        }
    }

}
