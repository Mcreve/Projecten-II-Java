/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.interfaces;

import domain.learningUtility.Reservation;
import java.util.Date;

/**
 *
 * @author Ward Vanlerberghe
 */
public interface IReservationCatalog extends ICatalog<Reservation> {
    
    public void changeFilter(Date date);
    
}
