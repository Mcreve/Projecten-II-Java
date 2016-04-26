/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.learningUtility;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Ward Vanlerberghe
 */
@Entity
@Table(name = "LearningUtility")
@XmlRootElement
public class LearningUtility implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Basic(optional = false)
    @Column(name = "Description")
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "Price")
    private BigDecimal price;
    @Basic(optional = false)
    @Column(name = "Loanable")
    private boolean loanable;
    @Column(name = "ArticleNumber")
    private String articleNumber;
    @Column(name = "Picture")
    private String picture;
    @Basic(optional = false)
    @Column(name = "AmountInCatalog")
    private int amountInCatalog;
    @Basic(optional = false)
    @Column(name = "AmountUnavailable")
    private int amountUnavailable;
    @JoinTable(name = "LearningUtility_TargetGroup", joinColumns = {
        @JoinColumn(name = "LearningUtilityId", referencedColumnName = "Id")}, inverseJoinColumns = {
        @JoinColumn(name = "TargetGroupId", referencedColumnName = "Id")})
    @ManyToMany
    private List<TargetGroup> targetGroupList;
     @JoinTable(name = "LearningUtility_FieldOfStudy", joinColumns = {
        @JoinColumn(name = "LearningUtilityId", referencedColumnName = "Id")}, inverseJoinColumns = {
        @JoinColumn(name = "FieldOfStudyId", referencedColumnName = "Id")})
    @ManyToMany
    private List<FieldOfStudy> fieldOfStudyList;
    @JoinColumn(name = "Company_Id", referencedColumnName = "Id")
    @ManyToOne
    private Company companyId;
    @JoinColumn(name = "Location_Id", referencedColumnName = "Id")
    @ManyToOne(optional = false)
    private Location locationId;

    public LearningUtility() {
    }

    public LearningUtility(Integer id) {
        this.id = id;
    }

    public LearningUtility(Integer id, String name, String description, BigDecimal price, boolean loanable, int amountInCatalog, int amountUnavailable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.loanable = loanable;
        this.amountInCatalog = amountInCatalog;
        this.amountUnavailable = amountUnavailable;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean getLoanable() {
        return loanable;
    }

    public void setLoanable(boolean loanable) {
        this.loanable = loanable;
    }

    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getAmountInCatalog() {
        return amountInCatalog;
    }

    public void setAmountInCatalog(int amountInCatalog) {
        this.amountInCatalog = amountInCatalog;
    }

    public int getAmountUnavailable() {
        return amountUnavailable;
    }

    public void setAmountUnavailable(int amountUnavailable) {
        this.amountUnavailable = amountUnavailable;
    }

    @XmlTransient
    public List<TargetGroup> getTargetGroupList() {
        return targetGroupList;
    }

    public void setTargetGroupList(List<TargetGroup> targetGroupList) {
        this.targetGroupList = targetGroupList;
    }

    @XmlTransient
    public List<FieldOfStudy> getFieldOfStudyList() {
        return fieldOfStudyList;
    }

    public void setFieldOfStudyList(List<FieldOfStudy> fieldOfStudyList) {
        this.fieldOfStudyList = fieldOfStudyList;
    }

    public Company getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Company companyId) {
        this.companyId = companyId;
    }

    public Location getLocationId() {
        return locationId;
    }

    public void setLocationId(Location locationId) {
        this.locationId = locationId;
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
        return  "LearningUtility         : " + this.id                          + "\n" +
                "Name                    : " + this.name                        + "\n" +
                "Article Number          : " + this.articleNumber               + "\n" +
                "Description             : " + this.description                 + "\n" +
                "Picture URL             : " + this.picture                     + "\n" +
                "Amount In Catalog       : " + this.amountInCatalog             + "\n" +   
                "Amount Unavailable      : " + this.amountUnavailable           + "\n" +
                "Company        Id       : " + this.companyId                   + "\n" +
                "Loanable                : " + this.loanable                    + "\n" +
                "Location       Id       : " + this.locationId                  + "\n" +
                "TargetGroups   Id       : " + this.targetGroupList.toString()  + "\n" +
                "FieldsOfStudy  Id       : " + this.fieldOfStudyList.toString() + "\n";                              
    }
    
}
