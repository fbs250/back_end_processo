package flavio.silva.cadastro.processos.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.given;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;

import flavio.silva.cadastro.processos.dto.ApiResponse;
import flavio.silva.cadastro.processos.dto.ProcessoDTO;
import flavio.silva.cadastro.processos.model.Processo;
import flavio.silva.cadastro.processos.repository.ProcessoRepository;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProcessoServiceTest {

	@Mock
	private ProcessoRepository processoRepository;

	@InjectMocks
	private ProcessoService processoService;

	private ProcessoDTO processoDto;

	@BeforeEach
	public void setup() {
		processoDto = new ProcessoDTO(null, "12345678991234569991", "PE", "Recife", null, null);
	}

	@Test
	public void deveSalvarProcesso_quandoNpuNaoExisteNaBase() {

		var processo = new Processo(processoDto);

		given(processoRepository.existsByNpu(processoDto.getNpu())).willReturn(false);
		given(processoRepository.save(processo)).willReturn(processo);

		ApiResponse response = processoService.save(processoDto);

		assertThat(response.getResponse()).isNotNull();
		assertEquals(HttpStatus.CREATED, response.getStatus());
	}

	@Test
	public void naoDeveSalvarProcesso_quandoNpuJaExisterNaBase() {

		given(processoRepository.existsByNpu(processoDto.getNpu())).willReturn(true);

		ApiResponse response = processoService.save(processoDto);

		assertThat(response.getResponse()).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
	}

	@Test
	public void deveAtualizarProcesso_quandoNpuNaoExisteNaBase() {

		processoDto.setId(1L);
		var processo = new Processo(processoDto);

		given(processoRepository.findById(processoDto.getId())).willReturn(Optional.of(processo));
		given(processoRepository.existsByNpuAndIdNot(processoDto.getNpu(), processoDto.getId())).willReturn(false);
		given(processoRepository.save(processo)).willReturn(processo);

		ApiResponse response = processoService.update(processoDto);

		assertThat(response.getResponse()).isNotNull();
		assertEquals(HttpStatus.CREATED, response.getStatus());
	}

	@Test
	public void deveVisualizarProcesso() {

		processoDto.setId(1L);
		var processo = new Processo(processoDto);

		given(processoRepository.findById(processoDto.getId())).willReturn(Optional.of(processo));
		given(processoRepository.save(processo)).willReturn(processo);

		ApiResponse response = processoService.visualizar(processoDto.getId());
		var responseProcesso = (ProcessoDTO) response.getResponse();

		assertThat(response.getResponse()).isNotNull();
		assertEquals(HttpStatus.OK, response.getStatus());
		assertNotNull(responseProcesso.getDataVisualizacao());
	}

	@Test
	public void naoDeveAtualizarProcesso_quandoNpuJaExisteNaBase() {

		processoDto.setId(1L);
		var processo = new Processo(processoDto);

		given(processoRepository.findById(processoDto.getId())).willReturn(Optional.of(processo));
		given(processoRepository.existsByNpuAndIdNot(processoDto.getNpu(), processoDto.getId())).willReturn(true);

		ApiResponse response = processoService.update(processoDto);

		assertThat(response.getResponse()).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
	}

	@Test
	public void deveLancarNotFound_aoAtualizarProcesso_quandoIdNaoExisteNaBase() {

		processoDto.setId(1L);

		given(processoRepository.findById(processoDto.getId())).willThrow(ResourceNotFoundException.class);

		ApiResponse response = processoService.update(processoDto);

		assertThat(response.getResponse()).isNull();
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
	}

	@Test
	public void deveLancarException_aoAtualizarProcesso_quandoNpuForMaiorQue20() {

		processoDto.setId(1L);
		processoDto.setNpu(processoDto.getNpu() + "9");
		var processo = new Processo(processoDto);

		given(processoRepository.findById(processoDto.getId())).willReturn(Optional.of(processo));
		given(processoRepository.existsByNpuAndIdNot(processoDto.getNpu(), processoDto.getId())).willReturn(false);
		given(processoRepository.save(processo)).willThrow(IllegalArgumentException.class);

		ApiResponse response = processoService.update(processoDto);

		assertThat(response.getResponse()).isNull();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
	}

	@Test
	public void deveLancarException_aoSalvarProcesso_quandoNpuForMaiorQue20() {

		processoDto.setId(1L);
		processoDto.setNpu(processoDto.getNpu() + "9");
		var processo = new Processo(processoDto);

		given(processoRepository.existsByNpu(processoDto.getNpu())).willReturn(false);
		given(processoRepository.save(processo)).willThrow(IllegalArgumentException.class);

		ApiResponse response = processoService.save(processoDto);

		assertThat(response.getResponse()).isNull();
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatus());
	}

	@Test
	public void deveLancarNotFound_aoBuscarProcesso_quandoIdNaoExisteNaBase() {

		processoDto.setId(1L);

		given(processoRepository.findById(processoDto.getId())).willThrow(ResourceNotFoundException.class);

		ApiResponse response = processoService.findById(processoDto.getId());

		assertThat(response.getResponse()).isNull();
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
	}

	@Test
	public void deveRetornarProcesso_quandoIdExisteNaBase() {

		processoDto.setId(1L);
		var processo = new Processo(processoDto);

		given(processoRepository.findById(processoDto.getId())).willReturn(Optional.of(processo));

		ApiResponse response = processoService.findById(processoDto.getId());

		assertThat(response.getResponse()).isNotNull();
		assertEquals(HttpStatus.OK, response.getStatus());
	}

	@Test
	public void deveLancarNotFound_aoDeletarProcesso_quandoIdNaoExisteNaBase() {

		processoDto.setId(1L);

		given(processoRepository.findById(processoDto.getId())).willThrow(ResourceNotFoundException.class);

		ApiResponse response = processoService.deleteById(processoDto.getId());

		assertThat(response.getResponse()).isNull();
		assertEquals(HttpStatus.NOT_FOUND, response.getStatus());
	}

	@Test
	public void deveDeletarProcesso_quandoIdExisteNaBase() {

		processoDto.setId(1L);
		var processo = new Processo(processoDto);

		given(processoRepository.findById(processoDto.getId())).willReturn(Optional.of(processo));

		ApiResponse response = processoService.deleteById(processoDto.getId());

		assertEquals(HttpStatus.OK, response.getStatus());
		assertEquals("Processo deletado com sucesso.", response.getMessage());
	}

	@Test
	public void naoDeveAtualizarProcesso_quandoIdForInvalido() {

		processoDto.setId(null);
		ApiResponse response = processoService.update(processoDto);

		assertThat(response.getResponse()).isNull();
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatus());
	}

}