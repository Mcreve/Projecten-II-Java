/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ward Vanlerberghe
 */
@Entity
@Table(name = "FieldOfStudy")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FieldOfStudy.findAll", query = "SELECT f FROM FieldOfStudy f"),
    @NamedQuery(name = "FieldOfStudy.findById", query = "SELECT f FROM FieldOfStudy f WHERE f.id = :id"),
    @NamedQuery(name = "FieldOfStudy.findByName", query = "SELECT f FROM FieldOfStudy f WHERE f.name = :name")})
public class FieldOfStudy implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @JoinTable(name = "LearningUtility_FieldOfStudy", joinColumns = {
        @JoinColumn(name = "FieldOfStudyId", referencedColumnName = "Id")}, inverseJoinColumns = {
        @JoinColumn(name = "LearningUtilityId", referencedColumnName = "Id")})
    @ManyToMany
    private List<LearningUtility> learningUtilityList;

    public FieldOfStudy() {
    }

    public FieldOfStudy(Integer id) {
        this.id = id;
    }

    public FieldOfStudy(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<LearningUtility> getLearningUtilityList() {
        return learningUtilityList;
    }

    public void setLearningUtilityList(List<LearningUtility> learningUtilityList) {
        this.learningUtilityList = learningUtilityList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FieldOfStudy)) {
            return false;
        }
        FieldOfStudy other = (FieldOfStudy) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.FieldOfStudy[ id=" + id + " ]";
    }
    
}
