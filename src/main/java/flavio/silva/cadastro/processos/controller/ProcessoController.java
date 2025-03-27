package flavio.silva.cadastro.processos.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import flavio.silva.cadastro.processos.dto.ApiResponse;
import flavio.silva.cadastro.processos.dto.ProcessoDTO;
import flavio.silva.cadastro.processos.service.ProcessoService;

@RestController
@RequestMapping("api/v1/processos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProcessoController {

	@Autowired
	private ProcessoService processoService;

	@PostMapping
	public ResponseEntity<ApiResponse> create(@RequestParam("npu") String npu, @RequestParam("uf") String uf,
			@RequestParam("municipio") String municipio, @RequestParam("pdfDocumento") MultipartFile pdfDoc)
			throws IOException {
		var response = processoService
				.save(new ProcessoDTO(null, npu, uf, municipio, pdfDoc.getBytes(), pdfDoc.getOriginalFilename()));
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PutMapping
	public ResponseEntity<ApiResponse> update(@RequestParam("id") String id, @RequestParam("npu") String npu,
			@RequestParam("uf") String uf, @RequestParam("municipio") String municipio,
			@RequestParam("pdfDocumento") MultipartFile pdfDoc) throws NumberFormatException, IOException {
		var response = processoService.update(
				new ProcessoDTO(Long.valueOf(id), npu, uf, municipio, pdfDoc.getBytes(), pdfDoc.getOriginalFilename()));
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PatchMapping("/visualizar/{id}")
	public ResponseEntity<ApiResponse> visualizar(@PathVariable Long id) {
		var response = processoService.visualizar(id);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> findById(@PathVariable Long id) {
		var response = processoService.findById(id);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping
	public ResponseEntity<ApiResponse> findAll(Pageable pageable) {
		var response = processoService.findAll(pageable);
		return ResponseEntity.status(response.getStatus()).body(response);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		var response = processoService.deleteById(id);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

}