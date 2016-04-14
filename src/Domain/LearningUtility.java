/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@NamedQueries({
    @NamedQuery(name = "LearningUtility.findAll", query = "SELECT l FROM LearningUtility l"),
    @NamedQuery(name = "LearningUtility.findById", query = "SELECT l FROM LearningUtility l WHERE l.id = :id"),
    @NamedQuery(name = "LearningUtility.findByName", query = "SELECT l FROM LearningUtility l WHERE l.name = :name"),
    @NamedQuery(name = "LearningUtility.findByDescription", query = "SELECT l FROM LearningUtility l WHERE l.description = :description"),
    @NamedQuery(name = "LearningUtility.findByPrice", query = "SELECT l FROM LearningUtility l WHERE l.price = :price"),
    @NamedQuery(name = "LearningUtility.findByLoanable", query = "SELECT l FROM LearningUtility l WHERE l.loanable = :loanable"),
    @NamedQuery(name = "LearningUtility.findByArticleNumber", query = "SELECT l FROM LearningUtility l WHERE l.articleNumber = :articleNumber"),
    @NamedQuery(name = "LearningUtility.findByPicture", query = "SELECT l FROM LearningUtility l WHERE l.picture = :picture"),
    @NamedQuery(name = "LearningUtility.findByAmountInCatalog", query = "SELECT l FROM LearningUtility l WHERE l.amountInCatalog = :amountInCatalog"),
    @NamedQuery(name = "LearningUtility.findByAmountUnavailable", query = "SELECT l FROM LearningUtility l WHERE l.amountUnavailable = :amountUnavailable")})
public class LearningUtility implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
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
    @ManyToMany(mappedBy = "learningUtilityList")
    private List<FieldOfStudy> fieldOfStudyList;
    @JoinTable(name = "Wishlist_LearningUtility", joinColumns = {
        @JoinColumn(name = "LearningUtilityId", referencedColumnName = "Id")}, inverseJoinColumns = {
        @JoinColumn(name = "WishlistId", referencedColumnName = "Id")})
    @ManyToMany
    private List<Wishlist> wishlistList;
    @OneToMany(mappedBy = "learningUtilityId")
    private List<Reservation> reservationList;
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

    @XmlTransient
    public List<Wishlist> getWishlistList() {
        return wishlistList;
    }

    public void setWishlistList(List<Wishlist> wishlistList) {
        this.wishlistList = wishlistList;
    }

    @XmlTransient
    public List<Reservation> getReservationList() {
        return reservationList;
    }

    public void setReservationList(List<Reservation> reservationList) {
        this.reservationList = reservationList;
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
        if (!(object instanceof LearningUtility)) {
            return false;
        }
        LearningUtility other = (LearningUtility) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Domain.LearningUtility[ id=" + id + " ]";
    }
    
}
