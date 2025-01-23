package ifmt.cba.servico;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import ifmt.cba.entity.Pedido;
import ifmt.cba.repository.PedidoRepository;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoServico {

  @Autowired
  private PedidoRepository pedidoRepository;

  // Listar todos os pedidos
  @GetMapping
  public List<Pedido> listarTodos() {
    return pedidoRepository.findAll();
  }

  // Buscar pedido por código
  @GetMapping("/{codigo}")
  public ResponseEntity<Pedido> buscarPorCodigo(@PathVariable int codigo) {
    Optional<Pedido> pedido = pedidoRepository.findById(codigo);
    if (pedido.isPresent()) {
      return ResponseEntity.ok(pedido.get());
    }
    return ResponseEntity.notFound().build();
  }

  // Criar um novo pedido
  @PostMapping
  public ResponseEntity<Pedido> criar(@RequestBody Pedido pedido) {
    String validacao = pedido.validar();
    if (!validacao.isEmpty()) {
      return ResponseEntity.badRequest().body(null);
    }
    Pedido novoPedido = pedidoRepository.save(pedido);
    return ResponseEntity.ok(novoPedido);
  }

  // Atualizar um pedido existente
  @PutMapping("/{codigo}")
  public ResponseEntity<Pedido> atualizar(@PathVariable int codigo, @RequestBody Pedido pedidoAtualizado) {
    Optional<Pedido> pedidoExistente = pedidoRepository.findById(codigo);
    if (pedidoExistente.isPresent()) {
      Pedido pedido = pedidoExistente.get();
      pedido.setCliente(pedidoAtualizado.getCliente());
      pedido.setDataPedido(pedidoAtualizado.getDataPedido());
      pedido.setHoraPedido(pedidoAtualizado.getHoraPedido());
      pedido.setHoraProducao(pedidoAtualizado.getHoraProducao());
      pedido.setHoraPronto(pedidoAtualizado.getHoraPronto());
      pedido.setHoraEntrega(pedidoAtualizado.getHoraEntrega());
      pedido.setHoraFinalizado(pedidoAtualizado.getHoraFinalizado());
      pedido.setEstado(pedidoAtualizado.getEstado());
      pedido.setListaItens(pedidoAtualizado.getListaItens());

      String validacao = pedido.validar();
      if (!validacao.isEmpty()) {
        return ResponseEntity.badRequest().body(null);
      }

      Pedido pedidoAtualizadoEntity = pedidoRepository.save(pedido);
      return ResponseEntity.ok(pedidoAtualizadoEntity);
    }
    return ResponseEntity.notFound().build();
  }

  // Excluir um pedido
  @DeleteMapping("/{codigo}")
  public ResponseEntity<Void> excluir(@PathVariable int codigo) {
    if (pedidoRepository.existsById(codigo)) {
      pedidoRepository.deleteById(codigo);
      return ResponseEntity.noContent().build();
    }
    return ResponseEntity.notFound().build();
  }
}
