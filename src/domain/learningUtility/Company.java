/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.learningUtility;

import domain.interfaces.ISearchableByName;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ward Vanlerberghe
 */
@Entity
@Table(name = "Company")
@XmlRootElement
public class Company implements Serializable, ISearchableByName {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Website")
    private String website;
    @Basic(optional = false)
    @Column(name = "ContactPersonName")
    private String contactPersonName;
    @Basic(optional = false)
    @Column(name = "EmailAddress")
    private String emailAddress;

    public Company() {
    }

    public Company(Integer id) {
        this.id = id;
    }

    public Company(Integer id, String name, String website, String contactPersonName, String emailAddress) {
        this.id = id;
        this.name = name;
        this.website = website;
        this.contactPersonName = contactPersonName;
        this.emailAddress = emailAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
        return "Company                : " + this.id                + "\n" +
               "Name                   : " + this.name              + "\n" +
               "E-mail Adres           : " + this.emailAddress      + "\n" +
               "Contactpersoon         : " + this.contactPersonName + "\n" +
               "Website                : " + this.website           + "\n" ;

    }
    
}
