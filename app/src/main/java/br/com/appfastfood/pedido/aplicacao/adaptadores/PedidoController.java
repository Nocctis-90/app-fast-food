package br.com.appfastfood.pedido.aplicacao.adaptadores;

import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.PedidoRequisicao;
import br.com.appfastfood.pedido.aplicacao.adaptadores.requisicao.RequisicaoExcecao;
import br.com.appfastfood.pedido.aplicacao.adaptadores.resposta.PedidoResposta;
import br.com.appfastfood.pedido.usecase.portas.PedidoServico;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Tudo sobre pedidos")
public class PedidoController {
        private PedidoServico pedidoServico;


        public PedidoController(PedidoServico pedidoServico) {
                this.pedidoServico = pedidoServico;
        }

        @PostMapping
        @Operation(summary = "Cadastrar Pedido", description = "Funcionalidade de criar um pedido")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "pedido cadastrado com sucesso", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResposta.class)) }),
                        @ApiResponse(responseCode = "400", description = "", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RequisicaoExcecao.class))) })
        public ResponseEntity<?> criar(@RequestBody PedidoRequisicao pedidoRequisicao) throws JsonProcessingException {
                /*TRANSFORMAR EM ASSINCRONO*/
                String id = this.pedidoServico.criar(pedidoRequisicao.sanitizarEntrada(pedidoRequisicao));
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(PedidoRequisicao.builder().idPedido(id).build());

        }

}
