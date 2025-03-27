package flavio.silva.cadastro.processos.model;

import java.util.Date;
import java.util.Objects;

import flavio.silva.cadastro.processos.dto.ProcessoDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "processo")
public class Processo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@NotBlank(message = "O NPU não pode estar em branco.")
	@Size(min = 20, max = 20, message = "O tamanho do NPU deve ser de 20 caracteres.")
	@Column(name = "npu", length = 20)
	private String npu;

	@NotNull
	@Column(name = "data_cadastro")
	private Date dataCadastro;

	@Column(name = "data_visualizacao")
	private Date dataVisualizacao;

	@NotBlank(message = "O UF não pode estar em branco.")
	@Size(min = 2, max = 2, message = "O tamanho da UF deve ser 2 caracteres.")
	@Column(name = "uf", length = 2)
	private String uf;

	@NotBlank(message = "O Município não pode estar em branco.")
	@Size(max = 255, message = "O tamanho do município deve ser de no máximo 255 caracteres.")
	@Column(name = "municipio", length = 255)
	private String municipio;

	@Lob
	@NotNull
	@Column(name = "pdf_documento")
	private byte[] pdfDocumento;

	@NotEmpty
	@Column(name = "pdf_documento_file_name")
	private String pdfDocumentoFileName;

	public Processo() {
	}

	public Processo(ProcessoDTO processoDTO) {
		atualizaProcesso(processoDTO);
		dataCadastro = new Date();
	}

	public void atualizaProcesso(ProcessoDTO processoDTO) {
		npu = processoDTO.getNpu();
		uf = processoDTO.getUf();
		municipio = processoDTO.getMunicipio();
		pdfDocumento = processoDTO.getPdfDocumento();
		pdfDocumentoFileName = processoDTO.getPdfDocumentoFileName();
	}

	public Long getId() {
		return id;
	}

	public byte[] getPdfDocumento() {
		return pdfDocumento;
	}

	public void setPdfDocumento(byte[] pdfDocumento) {
		this.pdfDocumento = pdfDocumento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(npu);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Processo other = (Processo) obj;
		return Objects.equals(npu, other.npu);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNpu() {
		return npu;
	}

	public void setNpu(String npu) {
		this.npu = npu;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataVisualizacao() {
		return dataVisualizacao;
	}

	public void setDataVisualizacao(Date dataVisualizacao) {
		this.dataVisualizacao = dataVisualizacao;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getPdfDocumentoFileName() {
		return pdfDocumentoFileName;
	}

	public void setPdfDocumentoFileName(String pdfDocumentoFileName) {
		this.pdfDocumentoFileName = pdfDocumentoFileName;
	}

}
