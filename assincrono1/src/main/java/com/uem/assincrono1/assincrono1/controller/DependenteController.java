package com.uem.assincrono1.assincrono1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uem.assincrono1.assincrono1.dto.request.DependenteRequestDTO;
import com.uem.assincrono1.assincrono1.dto.response.DependenteResponseDTO;
import com.uem.assincrono1.assincrono1.dto.response.ResponseDTO;
import com.uem.assincrono1.assincrono1.service.DependenteService;

@RestController
@RequestMapping("/api/v1/dependentes")
public class DependenteController extends ControllerBase{

	@Autowired
	private DependenteService dependenteService;
	
	public DependenteController(MessageSource messageSource) {
		super(messageSource);
	}
	
	@PostMapping
	public ResponseEntity<ResponseDTO<DependenteResponseDTO>> cadastra(@Valid @RequestBody DependenteRequestDTO dependenteRequestDTO) {
		
		DependenteResponseDTO dependenteResponseDTO = dependenteService.cadastra(dependenteRequestDTO);
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(new ResponseDTO<>(dependenteResponseDTO));
	}
	
	@GetMapping
	public ResponseEntity<ResponseDTO<List<DependenteResponseDTO>>> consultaTodos() {
	
		List<DependenteResponseDTO> dependenteResponseDTO = dependenteService.consultaTodos();
		
		return ResponseEntity.ok(new ResponseDTO<>(dependenteResponseDTO));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO<DependenteResponseDTO>> consultaPorId(@PathVariable Long id) {
	
		DependenteResponseDTO dependenteResponseDTO = dependenteService.consultaDependentePorId(id);
		
		return ResponseEntity.ok(new ResponseDTO<>(dependenteResponseDTO));	
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO<Void>> atualiza(@PathVariable Long id, @Valid @RequestBody DependenteRequestDTO dependenteRequestDTO){
		dependenteService.atualiza(id, dependenteRequestDTO);
		
		return ResponseEntity.noContent().build();
	}
}
