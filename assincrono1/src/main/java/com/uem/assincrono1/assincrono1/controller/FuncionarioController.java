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

import com.uem.assincrono1.assincrono1.dto.request.FuncionarioRequestDTO;
import com.uem.assincrono1.assincrono1.dto.response.CursoFuncionarioResponseDTO;
import com.uem.assincrono1.assincrono1.dto.response.DependenteResponseDTO;
import com.uem.assincrono1.assincrono1.dto.response.FuncionarioResponseDTO;
import com.uem.assincrono1.assincrono1.dto.response.ResponseDTO;
import com.uem.assincrono1.assincrono1.service.FuncionarioService;


@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController extends ControllerBase{

	@Autowired
	private FuncionarioService funcionarioService;
	
	public FuncionarioController(MessageSource messageSource) {
		super(messageSource);
	}

	@PostMapping
	public ResponseEntity<ResponseDTO<FuncionarioResponseDTO>> cadastra(@Valid @RequestBody FuncionarioRequestDTO funcionarioRequestDTO) {
		
		FuncionarioResponseDTO funcionarioResponseDTO = funcionarioService.cadastra(funcionarioRequestDTO);

		System.out.println("Entrou cadastra FuncionarioController, DTO:" + funcionarioRequestDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(new ResponseDTO<>(funcionarioResponseDTO));
	}
	
	@GetMapping
	public ResponseEntity<ResponseDTO<List<FuncionarioResponseDTO>>> consultaTodos() {
	
		List<FuncionarioResponseDTO> funcionarioResponseDTO = funcionarioService.consultaTodos();
		
		return ResponseEntity.ok(new ResponseDTO<>(funcionarioResponseDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<FuncionarioResponseDTO>> consultaPorId(@PathVariable Long id) {
	
		FuncionarioResponseDTO funcionarioResponseDTO = funcionarioService.consultaFuncionarioPorId(id);
		
		System.out.println("Entrou consultaPorId FuncionarioController, id: " + id);
		
		return ResponseEntity.ok(new ResponseDTO<>(funcionarioResponseDTO));	
	}
	
	@GetMapping("/dependentes/{id}")
	public ResponseEntity<ResponseDTO<List<DependenteResponseDTO>>> consultaTodosDependentesPorFuncionario(@PathVariable Long id) {
	
		List<DependenteResponseDTO> dependentesResponseDTO = funcionarioService.consultaTodosDependentesPorFuncionario(id);
		
		System.out.println("Entrou consultaTodosDependentes FuncionarioController, id: " + id);
		
		return ResponseEntity.ok(new ResponseDTO<>(dependentesResponseDTO));	
	}
	
	@GetMapping("/cursos/{id}")
	public ResponseEntity<ResponseDTO<List<CursoFuncionarioResponseDTO>>> consultaTodosInscricoesPorFuncionario(@PathVariable Long id) {
	
		List<CursoFuncionarioResponseDTO> cursoFuncionarioResponseDTO = funcionarioService.consultaTodosCursosPorFuncionario(id);
	
		System.out.println("Entrou consultaTodosInscricoesPorFuncionario FuncionarioController, id: " + id);
		
		return ResponseEntity.ok(new ResponseDTO<>(cursoFuncionarioResponseDTO));	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO<Void>> atualiza(@PathVariable Long id, @Valid @RequestBody FuncionarioRequestDTO funcionarioRequestDTO){
		funcionarioService.atualiza(id, funcionarioRequestDTO);
		
		System.out.println("Entrou atualiza FuncionarioController, DTO:" + funcionarioRequestDTO);
		
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO<Void>> deleta(@PathVariable Long id){
		funcionarioService.deleta(id);
		
		System.out.println("Entrou deleta FuncionarioController, id:" + id);
		
		return ResponseEntity.noContent().build();
	}
	
}
