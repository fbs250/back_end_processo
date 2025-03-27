package flavio.silva.cadastro.processos.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import flavio.silva.cadastro.processos.dto.ApiResponse;
import flavio.silva.cadastro.processos.dto.ProcessoDTO;
import flavio.silva.cadastro.processos.model.Processo;
import flavio.silva.cadastro.processos.repository.ProcessoRepository;
import jakarta.validation.ConstraintViolationException;

@Service
public class ProcessoService {

	@Autowired
	private ProcessoRepository processoRepository;

	public ApiResponse save(ProcessoDTO processoDto) {
		try {
			if (processoRepository.existsByNpu(processoDto.getNpu())) {
				return new ApiResponse("Processo já cadastrado.", HttpStatus.BAD_REQUEST, null);
			}

			return new ApiResponse("Processo adicionado com sucesso.", HttpStatus.CREATED,
					processoRepository.save(new Processo(processoDto)));

		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new ApiResponse(cve.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("Erro ao adicionar processo.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

	public ApiResponse update(ProcessoDTO processoDto) {

		try {
			if (processoDto.getId() == null || processoDto.getId().equals(0L)) {
				return new ApiResponse("O ID do processo é inválido.", HttpStatus.BAD_REQUEST, null);
			}

			Processo processo = processoRepository.findById(processoDto.getId()).orElseThrow(
					() -> new ResourceNotFoundException("Processo não encontrado: " + processoDto.getId()));

			if (processoRepository.existsByNpuAndIdNot(processoDto.getNpu(), processoDto.getId())) {
				return new ApiResponse("Processo já cadastrado.", HttpStatus.BAD_REQUEST, null);
			}
			processo.atualizaProcesso(processoDto);

			return new ApiResponse("Processo atualizado com sucesso.", HttpStatus.CREATED,
					new ProcessoDTO(processoRepository.save(processo)));

		} catch (ResourceNotFoundException rnfe) {
			rnfe.printStackTrace();
			return new ApiResponse(rnfe.getMessage(), HttpStatus.NOT_FOUND, null);
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new ApiResponse(cve.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("Erro ao editar processo.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}

	}

	public ApiResponse visualizar(Long id) {

		try {
			if (id == null || id.equals(0L)) {
				return new ApiResponse("O ID do processo é inválido.", HttpStatus.BAD_REQUEST, null);
			}

			Processo processo = processoRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Processo não encontrado: " + id));

			processo.setDataVisualizacao(new Date());

			processoRepository.save(processo);

			return new ApiResponse("Processo visualizado com sucesso.", HttpStatus.OK, new ProcessoDTO(processo));

		} catch (ResourceNotFoundException rnfe) {
			rnfe.printStackTrace();
			return new ApiResponse(rnfe.getMessage(), HttpStatus.NOT_FOUND, null);
		} catch (ConstraintViolationException cve) {
			cve.printStackTrace();
			return new ApiResponse(cve.getLocalizedMessage(), HttpStatus.BAD_REQUEST, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("Erro ao editar processo.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}

	}

	public ApiResponse findAll(Pageable pageable) {
		try {
			Page<Object> processoDTOList = processoRepository.findAll(pageable)
					.map(processo -> new ProcessoDTO(processo));
			return new ApiResponse(null, HttpStatus.OK, processoDTOList);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("Erro ao listar processos.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}
	}

	public ApiResponse findById(Long id) {
		try {
			if (id == null || id.equals(0L)) {
				return new ApiResponse("O ID do processo é inválido.", HttpStatus.BAD_REQUEST, null);
			}
			Processo processo = processoRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Processo não encontrado: " + id));
			return new ApiResponse(null, HttpStatus.OK, new ProcessoDTO(processo));
		} catch (ResourceNotFoundException rnfe) {
			rnfe.printStackTrace();
			return new ApiResponse(rnfe.getMessage(), HttpStatus.NOT_FOUND, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("Erro ao buscar processo.", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}

	}

	public ApiResponse deleteById(Long id) {
		try {
			if (id == null || id.equals(0L)) {
				return new ApiResponse("O ID do processo é inválido.", HttpStatus.BAD_REQUEST, null);
			}
			Processo processo = processoRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Processo não encontrado: " + id));
			processoRepository.delete(processo);
			return new ApiResponse("Processo deletado com sucesso.", HttpStatus.OK, null);
		} catch (ResourceNotFoundException rnfe) {
			rnfe.printStackTrace();
			return new ApiResponse(rnfe.getMessage(), HttpStatus.NOT_FOUND, null);
		} catch (Exception e) {
			e.printStackTrace();
			return new ApiResponse("Erro ao buscar processos", HttpStatus.INTERNAL_SERVER_ERROR, null);
		}

	}

}
