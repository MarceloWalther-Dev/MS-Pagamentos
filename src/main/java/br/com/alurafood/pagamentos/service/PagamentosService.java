package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PagamentosService {
    Page<PagamentoDTO> obterTodos(Pageable paginacao);

    PagamentoDTO obterPorId(Long id);

    PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO);

    PagamentoDTO atualizarPagamento(Long id, PagamentoDTO pagamentoDTO);

    void excluirPagamento(Long id);
}
