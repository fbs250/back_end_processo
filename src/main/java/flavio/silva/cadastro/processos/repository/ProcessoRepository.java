package flavio.silva.cadastro.processos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import flavio.silva.cadastro.processos.model.Processo;

@Repository
public interface ProcessoRepository extends JpaRepository<Processo, Long> {
	Boolean existsByNpu(String npu);

	Boolean existsByNpuAndIdNot(String npu, Long id);

}
