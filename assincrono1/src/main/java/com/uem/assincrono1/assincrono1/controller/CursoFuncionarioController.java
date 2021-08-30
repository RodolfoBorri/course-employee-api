package com.uem.assincrono1.assincrono1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uem.assincrono1.assincrono1.dto.request.CursoFuncionarioRequestDTO;
import com.uem.assincrono1.assincrono1.dto.response.CursoFuncionarioResponseDTO;
import com.uem.assincrono1.assincrono1.dto.response.ResponseDTO;
import com.uem.assincrono1.assincrono1.service.CursoFuncionarioService;

@RestController
@RequestMapping("/api/v1/cursosFuncionario")
public class CursoFuncionarioController extends ControllerBase{

	@Autowired
	private CursoFuncionarioService cursoFuncionarioService;
	
	public CursoFuncionarioController(MessageSource messageSource) {
		super(messageSource);
	}
	
	@PostMapping
	public ResponseEntity<ResponseDTO<CursoFuncionarioResponseDTO>> cadastra(@Valid @RequestBody CursoFuncionarioRequestDTO cursoFuncionarioRequestDTO) {
		
		CursoFuncionarioResponseDTO cursoFuncionarioResponseDTO = cursoFuncionarioService.cadastra(cursoFuncionarioRequestDTO);

		System.out.println("Entrou cadastra CursoFuncionarioController, DTO:" + cursoFuncionarioRequestDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(new ResponseDTO<>(cursoFuncionarioResponseDTO));
	}
	
	@GetMapping
	public ResponseEntity<ResponseDTO<List<CursoFuncionarioResponseDTO>>> consultaTodos() {
	
		List<CursoFuncionarioResponseDTO> cursoFuncionarioResponseDTO = cursoFuncionarioService.consultaTodos();
		
		return ResponseEntity.ok(new ResponseDTO<>(cursoFuncionarioResponseDTO));
	}

	@GetMapping("/funcionarios/{id}")
	public ResponseEntity<ResponseDTO<List<CursoFuncionarioResponseDTO>>> consultaPorIdFuncionario(@PathVariable Long id) {
	
		List<CursoFuncionarioResponseDTO> cursoFuncionarioResponseDTO = cursoFuncionarioService.consultaCursosFuncionarioPorIdFuncionario(id);
		
		System.out.println("Entrou consultaPorIdFuncionario CursoFuncionarioController, id: " + id);
		
		return ResponseEntity.ok(new ResponseDTO<>(cursoFuncionarioResponseDTO));	
	}
	
	@GetMapping("/cursos/{id}")
	public ResponseEntity<ResponseDTO<List<CursoFuncionarioResponseDTO>>> consultaPorIdCurso(@PathVariable Long id) {
	
		List<CursoFuncionarioResponseDTO> cursoFuncionarioResponseDTO = cursoFuncionarioService.consultaCursosFuncionarioPorIdCurso(id);
		
		System.out.println("Entrou consultaPorIdCurso CursoFuncionarioController, id: " + id);
		
		return ResponseEntity.ok(new ResponseDTO<>(cursoFuncionarioResponseDTO));	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO<Void>> atualiza(@PathVariable Long id, @Valid @RequestBody CursoFuncionarioRequestDTO cursoFuncionarioRequestDTO){
		cursoFuncionarioService.atualiza(id, cursoFuncionarioRequestDTO);
		
		System.out.println("Entrou atualiza CursoFuncionarioController, DTO:" + cursoFuncionarioRequestDTO);
		
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<CursoFuncionarioResponseDTO>> consultaPorId(@PathVariable Long id) {
	
		CursoFuncionarioResponseDTO cursoFuncionarioResponseDTO = cursoFuncionarioService.consultaCursoFuncionarioPorId(id);
		
		System.out.println("Entrou consultaPorIdCurso CursoFuncionarioController, id: " + id);
		
		return ResponseEntity.ok(new ResponseDTO<>(cursoFuncionarioResponseDTO));	
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO<Void>> deleta(@PathVariable Long id){
		cursoFuncionarioService.deleta(id);
		
		System.out.println("Entrou deleta CursoFuncionarioController, id:" + id);
		
		return ResponseEntity.noContent().build();
	}
}
