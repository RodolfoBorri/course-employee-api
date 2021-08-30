package com.uem.assincrono1.assincrono1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uem.assincrono1.assincrono1.Exception.ServiceException;
import com.uem.assincrono1.assincrono1.dto.request.DependenteRequestDTO;
import com.uem.assincrono1.assincrono1.dto.response.DependenteResponseDTO;
import com.uem.assincrono1.assincrono1.entity.Dependente;
import com.uem.assincrono1.assincrono1.entity.Funcionario;
import com.uem.assincrono1.assincrono1.repository.DependenteRepository;

@Service
public class DependenteService {

	@Autowired
	DependenteRepository dependenteRepository;
	
	@Autowired
	FuncionarioService funcionarioService;
	
	public DependenteResponseDTO cadastra(DependenteRequestDTO dependenteRequestDTO) {
		validaCadastro(dependenteRequestDTO);
		
		Dependente dependente = dependenteRequestDTOParaEntidade(dependenteRequestDTO, new Dependente());
		dependente.setFuncionario(funcionarioService.buscaPorId(dependenteRequestDTO.getIdFuncionario()));
		
		dependenteRepository.save(dependente);
		
		return entidadeParaDependenteResponseDTO(dependente);
	}

	private DependenteResponseDTO entidadeParaDependenteResponseDTO(Dependente dependente) {
		return DependenteResponseDTO.builder().id(dependente.getId())
										 	  .nomeDependente(dependente.getNomeDependente())
										 	  .idFuncionario(dependente.getFuncionario().getId())
										 	  .build();
	}

	private Dependente dependenteRequestDTOParaEntidade(DependenteRequestDTO dependenteRequestDTO, Dependente dependente) {
		dependente.setNomeDependente(dependenteRequestDTO.getNomeDependente());
		
		return dependente;
	}

	private void validaCadastro(DependenteRequestDTO dependenteRequestDTO) {
		Optional<Dependente> dependente = dependenteRepository.findByNomeDependente(dependenteRequestDTO.getNomeDependente());
		Funcionario funcionario = funcionarioService.buscaPorId(dependenteRequestDTO.getIdFuncionario());
		
		if(dependente.isPresent() && dependente.get().getFuncionario().equals(funcionario)) {
			throw new ServiceException("DB-4", dependenteRequestDTO.getNomeDependente(), funcionario.getNomeFuncionario());
		}
	}
	
	public List<DependenteResponseDTO> consultaTodos() {
		List<DependenteResponseDTO> retorno = new ArrayList<DependenteResponseDTO>();
		
		List<Dependente> dependentes = dependenteRepository.findAll();
		
		for(Dependente dependente : dependentes) {
			retorno.add(entidadeParaDependenteResponseDTO(dependente));
		}
		
		return retorno;
	}

	public DependenteResponseDTO consultaDependentePorId(Long id) {
		Dependente dependente = buscaPorId(id);
		
		return entidadeParaDependenteResponseDTO(dependente);
	}

	private Dependente buscaPorId(Long id) {
		return dependenteRepository.findById(id).orElseThrow(() -> new ServiceException("DB-6", id));
	}

	public void atualiza(Long id, DependenteRequestDTO dependenteRequestDTO) {
		validaAtualizacao(id, dependenteRequestDTO);
		
		Dependente dependenteAtualizado = dependenteRequestDTOParaEntidade(dependenteRequestDTO, buscaPorId(id));
		
		dependenteRepository.save(dependenteAtualizado);
		
	}

	private void validaAtualizacao(Long id, DependenteRequestDTO dependenteRequestDTO) {
		Optional<Dependente> dependenteExistente = dependenteRepository.findByNomeDependente(dependenteRequestDTO.getNomeDependente());

		if (dependenteExistente.isPresent() && !dependenteExistente.get().getId().equals(id)) {
			throw new ServiceException("DB-4", dependenteRequestDTO.getNomeDependente());
		}		
	}
}
