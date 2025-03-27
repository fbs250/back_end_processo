package flavio.silva.cadastro.processos.dto;

import java.util.Date;
import java.util.Objects;

import flavio.silva.cadastro.processos.model.Processo;

public class ProcessoDTO {

	private Long id;

	private String npu;

	private Date dataCadastro;

	private Date dataVisualizacao;

	private String uf;

	private String municipio;

	private byte[] pdfDocumento;

	private String pdfDocumentoFileName;

	public ProcessoDTO(Processo processo) {
		id = processo.getId();
		npu = processo.getNpu();
		dataCadastro = processo.getDataCadastro();
		dataVisualizacao = processo.getDataVisualizacao();
		uf = processo.getUf();
		municipio = processo.getMunicipio();
		pdfDocumento = processo.getPdfDocumento();
		pdfDocumentoFileName = processo.getPdfDocumentoFileName();
	}

	public ProcessoDTO(Long id, String npu, String uf, String municipio, byte[] pdfDoc, String pdfDocFileName) {
		this.id = id;
		this.npu = npu;
		this.uf = uf;
		this.municipio = municipio;
		this.pdfDocumento = pdfDoc;
		this.pdfDocumentoFileName = pdfDocFileName;
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
		ProcessoDTO other = (ProcessoDTO) obj;
		return Objects.equals(npu, other.npu);
	}

	public ProcessoDTO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getNpu() {
		return npu;
	}

	public void setNpu(String npu) {
		this.npu = npu;
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

	public byte[] getPdfDocumento() {
		return pdfDocumento;
	}

	public void setPdfDocumento(byte[] pdfDocumento) {
		this.pdfDocumento = pdfDocumento;
	}

	public String getPdfDocumentoFileName() {
		return pdfDocumentoFileName;
	}

	public void setPdfDocumentoFileName(String pdfDocumentoFileName) {
		this.pdfDocumentoFileName = pdfDocumentoFileName;
	}

}
