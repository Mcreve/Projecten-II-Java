/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.learningUtility;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ward Vanlerberghe
 */
@Entity
@Table(name = "Location")
@XmlRootElement
public class Location implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "locationId")
    private List<LearningUtility> learningUtilityList;

    public Location() {
    }

    public Location(Integer id) {
        this.id = id;
    }

    public Location(Integer id, String name) {
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
        if (!(object instanceof Integer)) {
            return false;
        }
        if ((this.id == null && id != null) || (this.id != null && !this.id.equals(id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "domain.Location[ id=" + id + " ]";
    }
    
}
