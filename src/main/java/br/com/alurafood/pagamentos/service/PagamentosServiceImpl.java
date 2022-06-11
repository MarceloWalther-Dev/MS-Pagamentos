package br.com.alurafood.pagamentos.service;

import br.com.alurafood.pagamentos.dto.PagamentoDTO;
import br.com.alurafood.pagamentos.model.Pagamento;
import br.com.alurafood.pagamentos.model.Status;
import br.com.alurafood.pagamentos.repository.PagamentoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;


@Service
public class PagamentosServiceImpl implements PagamentosService {

    @Autowired
    private PagamentoRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public Page<PagamentoDTO> obterTodos(Pageable paginacao){
        return repository.findAll(paginacao) // tras uma lista de pagamentos ou um Page<Pagamentos>
                .map(pagamento -> modelMapper.map(pagamento, PagamentoDTO.class));// converta esse Page<Pagamentos> para Page<PagamentosDTO>
    }

    @Override
    public PagamentoDTO obterPorId(Long id){
        Pagamento pagamento = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException());
        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Override
    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO){
        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setStatus(Status.CRIADO);
        repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Override
    public PagamentoDTO atualizarPagamento(Long id, PagamentoDTO pagamentoDTO){
        Pagamento pagamento = modelMapper.map(pagamentoDTO, Pagamento.class);
        pagamento.setId(id);
        pagamento = repository.save(pagamento);

        return modelMapper.map(pagamento, PagamentoDTO.class);
    }

    @Override
    public void excluirPagamento(Long id){
        repository.deleteById(id);
    }

}
