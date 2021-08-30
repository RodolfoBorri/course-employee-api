package com.uem.assincrono1.assincrono1.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uem.assincrono1.assincrono1.Exception.ServiceException;
import com.uem.assincrono1.assincrono1.dto.request.CursoRequestDTO;
import com.uem.assincrono1.assincrono1.dto.response.CursoResponseDTO;
import com.uem.assincrono1.assincrono1.entity.Curso;
import com.uem.assincrono1.assincrono1.repository.CursoRepository;

@Service
public class CursoService {

	@Autowired
	CursoRepository cursoRepository;
	
	public CursoResponseDTO cadastra(CursoRequestDTO cursoRequestDTO) {
		validaCadastro(cursoRequestDTO);
		
		Curso curso = cursoRequestDTOParaEntidade(cursoRequestDTO, new Curso());
		
		cursoRepository.save(curso);
		
		return entidadeParaCursoResponseDTO(curso);
	}

	private CursoResponseDTO entidadeParaCursoResponseDTO(Curso curso) {
		return CursoResponseDTO.builder().id(curso.getId())
										 .nomeCurso(curso.getNomeCurso())										 
										 .build();
	}

	private Curso cursoRequestDTOParaEntidade(CursoRequestDTO cursoRequestDTO, Curso curso) {
		curso.setNomeCurso(cursoRequestDTO.getNomeCurso());
		
		return curso;
	}

	private void validaCadastro(CursoRequestDTO cursoRequestDTO) {
		if(cursoRepository.findByNomeCurso(cursoRequestDTO.getNomeCurso()).isPresent()) {
			throw new ServiceException("DB-3", cursoRequestDTO.getNomeCurso());
		}
	}
	
	public List<CursoResponseDTO> consultaTodos() {
		List<CursoResponseDTO> retorno = new ArrayList<CursoResponseDTO>();
		
		List<Curso> cursos = cursoRepository.findAll();
		
		for(Curso curso : cursos) {
			retorno.add(entidadeParaCursoResponseDTO(curso));
		}
		
		return retorno;
	}

	public CursoResponseDTO consultaCursoPorId(Long id) {
		Curso curso = buscaPorId(id);
		
		return entidadeParaCursoResponseDTO(curso);
	}

	public Curso buscaPorId(Long id) {
		return cursoRepository.findById(id).orElseThrow(() -> new ServiceException("DB-5", id));
	}

	public void atualiza(Long id, CursoRequestDTO cursoRequestDTO) {
		validaAtualizacao(id, cursoRequestDTO);
		
		Curso cursoAtualizado = cursoRequestDTOParaEntidade(cursoRequestDTO, buscaPorId(id));
		
		cursoRepository.save(cursoAtualizado);
		
	}

	private void validaAtualizacao(Long id, CursoRequestDTO cursoRequestDTO) {
		Optional<Curso> cursoExistente = cursoRepository.findByNomeCurso(cursoRequestDTO.getNomeCurso());

		if (cursoExistente.isPresent() && !cursoExistente.get().getId().equals(id)) {
			throw new ServiceException("DB-3", cursoRequestDTO.getNomeCurso());
		}		
	}

}
