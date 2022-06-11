package br.com.alurafood.pagamentos.controller;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.service.PagamentosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;

@RestController
@RequestMapping(value = "/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentosService service;

    @GetMapping
    public ResponseEntity<Page<PagamentoDTO>> listar(@PageableDefault(size = 10)Pageable paginacao){
        return ResponseEntity.ok(service.obterTodos(paginacao));
    }

    @GetMapping(value = "/{pagamentoId}")
    public ResponseEntity<PagamentoDTO> detalhar(@PathVariable("pagamentoId") @NotNull Long pagamentoId){
        PagamentoDTO pagamentoDTO = service.obterPorId(pagamentoId);
        return ResponseEntity.ok(pagamentoDTO);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> cadastrar(@RequestBody @Valid PagamentoDTO pagamentoDTO, UriComponentsBuilder uriBuilder){
        PagamentoDTO pagamento = service.criarPagamento(pagamentoDTO);
        URI link = uriBuilder.path("/pagamentos/{pagamentoId}").buildAndExpand(pagamento.getId()).toUri();
        return ResponseEntity.created(link).body(pagamento);
    }

    @PutMapping(value = "/{pagamentoId}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable("pagamentoId") @NotNull Long pagamentoId, @RequestBody @Valid PagamentoDTO pagamentoDTO){
        PagamentoDTO pagamento = service.atualizarPagamento(pagamentoId, pagamentoDTO);
        return ResponseEntity.ok(pagamento);
    }

    @DeleteMapping(value = "/{pagamentoId}")
    public ResponseEntity<Void> remover(@PathVariable(value = "pagamentoId") @NotNull Long pagamentoId){
        service.excluirPagamento(pagamentoId);
        return ResponseEntity.noContent().build();
    }
}
