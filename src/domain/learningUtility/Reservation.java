/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.learningUtility;

import domain.users.User;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Ward Vanlerberghe
 */
@Entity
@Table(name = "Reservation")
@XmlRootElement
public class Reservation implements Serializable {

    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "DateWanted")
    @Temporal(TemporalType.DATE)
    private Date dateWanted;
    @Basic(optional = false)
    @Column(name = "Amount")
    private int amount;
    @Column(name = "ReservationDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reservationDate;
    @Column(name = "DaysBlocked")
    private String daysBlocked;
    @JoinColumn(name = "LearningUtility_Id", referencedColumnName = "Id")
    @ManyToOne
    private LearningUtility learningUtilityId;
    @JoinColumn(name = "User_EmailAddress", referencedColumnName = "EmailAddress")
    @ManyToOne
    private User userEmailAddress;
    @Column(name = "ReturnDate")
    @Temporal(TemporalType.DATE)
    private Date returnDate;
    @Column(name = "AmountReturned")
    private int amountReturned;

    public Reservation() {
    }

    public Reservation(Integer id) {
        this.id = id;
    }

    public Reservation(Integer id, Date dateWanted, int amount) {
        this.id = id;
        this.dateWanted = dateWanted;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDateWanted() {
        return dateWanted;
    }

    public void setDateWanted(Date dateWanted) {
        this.dateWanted = dateWanted;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getDaysBlocked() {
        return daysBlocked;
    }

    public void setDaysBlocked(String daysBlocked) {
        this.daysBlocked = daysBlocked;
    }

    public LearningUtility getLearningUtilityId() {
        return learningUtilityId;
    }

    public void setLearningUtilityId(LearningUtility learningUtilityId) {
        this.learningUtilityId = learningUtilityId;
    }

    public User getUserEmailAddress() {
        return userEmailAddress;
    }

    public void setUserEmailAddress(User userEmailAddress) {
        this.userEmailAddress = userEmailAddress;
    }
    
    public Date getReturnDate(){
        return returnDate;
    }
    
    public void setReturnDate(Date date){
        this.returnDate = date;
    }
    
    public int getAmountReturned(){
        return amountReturned;
    }
    
    public void setAmountReturned(int amountReturned){
        this.amountReturned = amountReturned;
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
            return super.equals(object);
        }
        if ((this.id == null && id != null) || (this.id != null && !this.id.equals(id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  "Reservation            : " + this.id                + "\n" +
                "User Email             : " + this.userEmailAddress  + "\n" +
                "LearningUtility Id     : " + this.learningUtilityId + "\n" +
                "Amount                 : " + this.amount            + "\n" +
                "Date Wanted            : " + this.dateWanted        + "\n" +
                "Reservation Date       : " + this.reservationDate   + "\n" +
                "Days Blocked           : " + this.daysBlocked       + "\n" ;
                
    }
    
}
