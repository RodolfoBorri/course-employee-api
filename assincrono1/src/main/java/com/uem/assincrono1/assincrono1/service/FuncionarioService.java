package com.uem.assincrono1.assincrono1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.uem.assincrono1.assincrono1.Exception.ServiceException;
import com.uem.assincrono1.assincrono1.dto.request.FuncionarioRequestDTO;
import com.uem.assincrono1.assincrono1.dto.response.FuncionarioResponseDTO;
import com.uem.assincrono1.assincrono1.entity.Funcionario;
import com.uem.assincrono1.assincrono1.repository.FuncionarioRepository;

@Service
public class FuncionarioService { 

	@Autowired
	FuncionarioRepository funcionarioRepository;
	
	public FuncionarioResponseDTO cadastra(FuncionarioRequestDTO funcionarioRequestDTO) {
		validaCadastro(funcionarioRequestDTO);
		
		Funcionario funcionario = funcionarioRequestDTOParaEntidade(funcionarioRequestDTO, new Funcionario());
		
		funcionarioRepository.save(funcionario);
		
		return entidadeParaFuncionarioResponseDTO(funcionario);
	}

	private FuncionarioResponseDTO entidadeParaFuncionarioResponseDTO(Funcionario funcionario) {
		return FuncionarioResponseDTO.builder().id(funcionario.getId())
											   .matriculaFuncionario(funcionario.getMatriculaFuncionario())
											   .nomeFuncionario(funcionario.getNomeFuncionario())
											   .dataNascimento(funcionario.getDataNascimento())
											   .build();
	}

	private Funcionario funcionarioRequestDTOParaEntidade(FuncionarioRequestDTO funcionarioRequestDTO, Funcionario funcionario) {
		funcionario.setMatriculaFuncionario(funcionarioRequestDTO.getMatriculaFuncionario());
		funcionario.setNomeFuncionario(funcionarioRequestDTO.getNomeFuncionario());
		funcionario.setDataNascimento(funcionarioRequestDTO.getDataNascimento());
		
		return funcionario;
	}

	private void validaCadastro(FuncionarioRequestDTO funcionarioRequestDTO) {
		if(funcionarioRepository.findByMatriculaFuncionario(funcionarioRequestDTO.getMatriculaFuncionario()).isPresent()) {
			throw new ServiceException("DB-2", funcionarioRequestDTO.getMatriculaFuncionario());
		}		
	}

	public List<FuncionarioResponseDTO> consultaTodos() {
		List<FuncionarioResponseDTO> retorno = new ArrayList<FuncionarioResponseDTO>();
		
		List<Funcionario> funcionarios = funcionarioRepository.findAll();
		
		for(Funcionario funcionario : funcionarios) {
			retorno.add(entidadeParaFuncionarioResponseDTO(funcionario));
		}
		
		return retorno;
	}

	public FuncionarioResponseDTO consultaFuncionarioPorId(Long id) {
		Funcionario funcionario = buscaPorId(id);
		
		return entidadeParaFuncionarioResponseDTO(funcionario);
	}

	public Funcionario buscaPorId(Long id) {
		return funcionarioRepository.findById(id).orElseThrow(() -> new ServiceException("DB-1", id));
	}

	public void atualiza(Long id, FuncionarioRequestDTO funcionarioRequestDTO) {
		validaAtualizacao(id, funcionarioRequestDTO);
		
		Funcionario funcionarioAtualizado = funcionarioRequestDTOParaEntidade(funcionarioRequestDTO, buscaPorId(id));
		
		funcionarioRepository.save(funcionarioAtualizado);		
	}

	private void validaAtualizacao(Long id, FuncionarioRequestDTO funcionarioRequestDTO) {
		Optional<Funcionario> funcionarioExistente = funcionarioRepository.findByMatriculaFuncionario(funcionarioRequestDTO.getMatriculaFuncionario());

		if (funcionarioExistente.isPresent() && !funcionarioExistente.get().getId().equals(id)) {
			throw new ServiceException("DB-2", funcionarioRequestDTO.getMatriculaFuncionario());
		}
	}

	public void deleta(Long id) {
		
		Funcionario funcionarioExistente = buscaPorId(id);
		
		try {
			funcionarioRepository.delete(funcionarioExistente);	
		}
		catch (DataIntegrityViolationException e) {
			throw new ServiceException("DB-10", funcionarioExistente.getNomeFuncionario());			
		}
	}

}
