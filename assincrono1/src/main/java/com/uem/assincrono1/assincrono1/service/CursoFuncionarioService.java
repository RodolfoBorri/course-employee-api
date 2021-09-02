package com.uem.assincrono1.assincrono1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uem.assincrono1.assincrono1.Exception.ServiceException;
import com.uem.assincrono1.assincrono1.dto.request.CursoFuncionarioRequestDTO;
import com.uem.assincrono1.assincrono1.dto.response.CursoFuncionarioResponseDTO;
import com.uem.assincrono1.assincrono1.entity.Curso;
import com.uem.assincrono1.assincrono1.entity.CursoFuncionario;
import com.uem.assincrono1.assincrono1.entity.Funcionario;
import com.uem.assincrono1.assincrono1.repository.CursoFuncionarioRepository;

@Service
public class CursoFuncionarioService {

	@Autowired
	CursoFuncionarioRepository cursoFuncionarioRepository;
	
	@Autowired
	CursoService cursoService;
	
	@Autowired
	FuncionarioService funcionarioService;
			
	public CursoFuncionarioResponseDTO cadastra(CursoFuncionarioRequestDTO cursoFuncionarioRequestDTO) {
		validaCadastro(cursoFuncionarioRequestDTO);
		CursoFuncionario cursoFuncionario = cursoFuncionarioRequestDTOParaEntidade(cursoFuncionarioRequestDTO, new CursoFuncionario());
		
		cursoFuncionarioRepository.save(cursoFuncionario);
		
		return entidadeParaCursoFuncionarioResponseDTO(cursoFuncionario);
	}

	public CursoFuncionarioResponseDTO entidadeParaCursoFuncionarioResponseDTO(CursoFuncionario cursoFuncionario) {
		return CursoFuncionarioResponseDTO.builder().id(cursoFuncionario.getId())
										  .nomeCurso(cursoFuncionario.getCurso().getNomeCurso())
										  .nomeFuncionario(cursoFuncionario.getFuncionario().getNomeFuncionario())
										  .anoFormacao(cursoFuncionario.getAnoFormacao())
										  .build();
	}

	private CursoFuncionario cursoFuncionarioRequestDTOParaEntidade(CursoFuncionarioRequestDTO cursoFuncionarioRequestDTO, CursoFuncionario cursoFuncionario) {
		Curso curso = cursoService.buscaPorId(cursoFuncionarioRequestDTO.getIdCurso());
		Funcionario funcionario = funcionarioService.buscaPorId(cursoFuncionarioRequestDTO.getIdFuncionario());
		cursoFuncionario.setAnoFormacao(cursoFuncionarioRequestDTO.getAnoFormacao());
		cursoFuncionario.setCurso(curso);
		cursoFuncionario.setFuncionario(funcionario);
		
		return cursoFuncionario;
	}

	private void validaCadastro(CursoFuncionarioRequestDTO cursoFuncionarioRequestDTO) {
		Curso curso = cursoService.buscaPorId(cursoFuncionarioRequestDTO.getIdCurso());
		Funcionario funcionario = funcionarioService.buscaPorId(cursoFuncionarioRequestDTO.getIdFuncionario());
		
		if(cursoFuncionarioRepository.findByCursoAndFuncionarioAndAnoFormacao(curso, 
				funcionario, 
				cursoFuncionarioRequestDTO.getAnoFormacao()).isPresent()) {
			throw new ServiceException("DB-7", cursoFuncionarioRequestDTO.getIdFuncionario(), 
					cursoFuncionarioRequestDTO.getIdCurso(), 
					cursoFuncionarioRequestDTO.getAnoFormacao());
		}
	}
	
	public List<CursoFuncionarioResponseDTO> consultaTodos() {
		List<CursoFuncionarioResponseDTO> retorno = new ArrayList<CursoFuncionarioResponseDTO>();
		
		List<CursoFuncionario> cursoFuncionarios = cursoFuncionarioRepository.findAll();
		
		for(CursoFuncionario cursoFuncionario : cursoFuncionarios) {
			retorno.add(entidadeParaCursoFuncionarioResponseDTO(cursoFuncionario));
		}
		
		return retorno;
	}

	public List<CursoFuncionarioResponseDTO> consultaCursosFuncionarioPorIdFuncionario(Long idFuncionario) {
		List<CursoFuncionarioResponseDTO> cursoResponse = new ArrayList<CursoFuncionarioResponseDTO>();
		
		List<CursoFuncionario> cursosFuncionario = buscaPorIdFuncionario(idFuncionario);
		
		for(CursoFuncionario cursoFuncionario : cursosFuncionario)
			cursoResponse.add(entidadeParaCursoFuncionarioResponseDTO(cursoFuncionario));
		
		return cursoResponse;
	}
	
	public List<CursoFuncionarioResponseDTO> consultaCursosFuncionarioPorIdCurso(Long idCurso) {
		List<CursoFuncionarioResponseDTO> cursoResponse = new ArrayList<CursoFuncionarioResponseDTO>();
		
		List<CursoFuncionario> cursosFuncionario = buscaPorIdCurso(idCurso);
		
		for(CursoFuncionario cursoFuncionario : cursosFuncionario)
			cursoResponse.add(entidadeParaCursoFuncionarioResponseDTO(cursoFuncionario));
		
		return cursoResponse;
	}

	public List<CursoFuncionario> buscaPorIdFuncionario(Long idFuncionario) {
		Funcionario funcionario = funcionarioService.buscaPorId(idFuncionario);

		List<CursoFuncionario> cursoFuncionarios = cursoFuncionarioRepository.findByFuncionario(funcionario);
		
		if(cursoFuncionarios.isEmpty())
			throw new ServiceException("DB-8", idFuncionario);
		
		return cursoFuncionarios;
	}
	
	public List<CursoFuncionario> buscaPorIdCurso(Long idCurso) {
		Curso curso = cursoService.buscaPorId(idCurso);
		
		List<CursoFuncionario> cursoFuncionarios = cursoFuncionarioRepository.findByCurso(curso);
		
		if(cursoFuncionarios.isEmpty())
			throw new ServiceException("DB-9", idCurso);
		
		return cursoFuncionarios;
	}

	public void atualiza(Long id, CursoFuncionarioRequestDTO cursoFuncionarioRequestDTO) {
		CursoFuncionario cursoFuncionarioExistente = validaAtualizacao(id, cursoFuncionarioRequestDTO);
		
		CursoFuncionario cursoFuncionarioAtualizado = cursoFuncionarioRequestDTOParaEntidade(cursoFuncionarioRequestDTO, cursoFuncionarioExistente);
		
		cursoFuncionarioRepository.save(cursoFuncionarioAtualizado);		
	}

	private CursoFuncionario validaAtualizacao(Long id, CursoFuncionarioRequestDTO cursoFuncionarioRequestDTO) {
		Curso curso = cursoService.buscaPorId(cursoFuncionarioRequestDTO.getIdCurso());
		Funcionario funcionario = funcionarioService.buscaPorId(cursoFuncionarioRequestDTO.getIdFuncionario());
		
		Optional<CursoFuncionario> cursoFuncionarioExistente = cursoFuncionarioRepository.findByCursoAndFuncionarioAndAnoFormacao(curso, funcionario, 
				cursoFuncionarioRequestDTO.getAnoFormacao());

		if (cursoFuncionarioExistente.isPresent() && !cursoFuncionarioExistente.get().getId().equals(id)) {
			throw new ServiceException("DB-7", cursoFuncionarioRequestDTO.getIdFuncionario(), 
					cursoFuncionarioRequestDTO.getIdCurso(), 
					cursoFuncionarioRequestDTO.getAnoFormacao());
		}
		
		return cursoFuncionarioExistente.get();
	}
	
	private CursoFuncionario buscaPorId(Long id) {
		return cursoFuncionarioRepository.findById(id).orElseThrow(() -> new ServiceException("DB-6", id));
	}

	public void deleta(Long id) {
		CursoFuncionario cursoFuncionarioExistente = buscaPorId(id);
		
		cursoFuncionarioRepository.delete(cursoFuncionarioExistente);
	}
	
	public CursoFuncionarioResponseDTO consultaCursoFuncionarioPorId(Long id) {
		CursoFuncionario cursoFuncionario = buscaPorId(id);
		
		return entidadeParaCursoFuncionarioResponseDTO(cursoFuncionario);
	}

	public List<CursoFuncionario> consultaTodosInscricoesPorFuncionario(Funcionario funcionario) {
		return cursoFuncionarioRepository.findAllByFuncionario(funcionario);
	}

}


