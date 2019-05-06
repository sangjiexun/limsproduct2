
package net.zjcclims.domain;

import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "findAllLabConstructionProjectDocuments", query = "select myLabConstructionProjectDocument from LabConstructionProjectDocument myLabConstructionProjectDocument"),
		@NamedQuery(name = "findLabConstructionProjectDocumentByEnable", query = "select myLabConstructionProjectDocument from LabConstructionProjectDocument myLabConstructionProjectDocument where myLabConstructionProjectDocument.enable = ?1"),
		@NamedQuery(name = "findLabConstructionProjectDocumentById", query = "select myLabConstructionProjectDocument from LabConstructionProjectDocument myLabConstructionProjectDocument where myLabConstructionProjectDocument.id = ?1"),
		@NamedQuery(name = "findLabConstructionProjectDocumentByPrimaryKey", query = "select myLabConstructionProjectDocument from LabConstructionProjectDocument myLabConstructionProjectDocument where myLabConstructionProjectDocument.id = ?1"),
		@NamedQuery(name = "findLabConstructionProjectDocumentByStage", query = "select myLabConstructionProjectDocument from LabConstructionProjectDocument myLabConstructionProjectDocument where myLabConstructionProjectDocument.stage = ?1") })

@Table(name = "lab_construction_project_document")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(namespace = "zjcclims/net/zjcclims/domain", name = "LabConstructionProjectDocument")

public class LabConstructionProjectDocument implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	* ��ʵ����Ŀ-�ĵ�����������
	* 
	 */

	@Column(name = "id", nullable = false)
	@Basic(fetch = FetchType.EAGER)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@XmlElement
	Integer id;
	/**
	* ��ǰ�׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */

	@Column(name = "stage")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Integer stage;
	/**
	* �Ƿ���Ч
	* 
	 */

	@Column(name = "enable")
	@Basic(fetch = FetchType.EAGER)

	@XmlElement
	Boolean enable;

	/**
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "document_id", referencedColumnName = "id") })
	@XmlTransient
	CommonDocument commonDocument;
	/**
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({ @JoinColumn(name = "project_id", referencedColumnName = "id") })
	@XmlTransient
	LabConstructionGrandsonProject labConstructionGrandsonProject;

	/**
	* ��ʵ����Ŀ-�ĵ�����������
	* 
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	* ��ʵ����Ŀ-�ĵ�����������
	* 
	 */
	public Integer getId() {
		return this.id;
	}

	/**
	* ��ǰ�׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */
	public void setStage(Integer stage) {
		this.stage = stage;
	}

	/**
	* ��ǰ�׶Σ�1-����ĵ���2-��֤˵����3-�ɹ��ļ���4-��غ�ͬ��5-���������
	* 
	 */
	public Integer getStage() {
		return this.stage;
	}

	/**
	* �Ƿ���Ч
	* 
	 */
	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	/**
	* �Ƿ���Ч
	* 
	 */
	public Boolean getEnable() {
		return this.enable;
	}

	/**
	 */
	public void setCommonDocument(CommonDocument commonDocument) {
		this.commonDocument = commonDocument;
	}

	/**
	 */
	@JsonIgnore
	public CommonDocument getCommonDocument() {
		return commonDocument;
	}

	/**
	 */
	public void setLabConstructionGrandsonProject(LabConstructionGrandsonProject labConstructionGrandsonProject) {
		this.labConstructionGrandsonProject = labConstructionGrandsonProject;
	}

	/**
	 */
	@JsonIgnore
	public LabConstructionGrandsonProject getLabConstructionGrandsonProject() {
		return labConstructionGrandsonProject;
	}

	/**
	 */
	public LabConstructionProjectDocument() {
	}

	/**
	 * Copies the contents of the specified bean into this bean.
	 *
	 */
	public void copy(LabConstructionProjectDocument that) {
		setId(that.getId());
		setStage(that.getStage());
		setEnable(that.getEnable());
		setCommonDocument(that.getCommonDocument());
		setLabConstructionGrandsonProject(that.getLabConstructionGrandsonProject());
	}

	/**
	 * Returns a textual representation of a bean.
	 *
	 */
	public String toString() {

		StringBuilder buffer = new StringBuilder();

		buffer.append("id=[").append(id).append("] ");
		buffer.append("stage=[").append(stage).append("] ");
		buffer.append("enable=[").append(enable).append("] ");

		return buffer.toString();
	}

	/**
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + ((id == null) ? 0 : id.hashCode()));
		return result;
	}

	/**
	 */
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof LabConstructionProjectDocument))
			return false;
		LabConstructionProjectDocument equalCheck = (LabConstructionProjectDocument) obj;
		if ((id == null && equalCheck.id != null) || (id != null && equalCheck.id == null))
			return false;
		if (id != null && !id.equals(equalCheck.id))
			return false;
		return true;
	}
}
