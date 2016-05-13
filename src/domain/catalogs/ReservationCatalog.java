/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.catalogs;

import domain.interfaces.IReservationCatalog;
import domain.learningUtility.Reservation;
import java.util.Date;

/**
 *
 * @author Ward Vanlerberghe
 */
public class ReservationCatalog extends Catalog<Reservation> implements IReservationCatalog {

    public ReservationCatalog() {
        super(Reservation.class);
    }

    @Override
    public void changeFilter(Date date) {
        super.filteredData.setPredicate(entity -> {
            if(date.equals(entity.getDateWanted()))
                return true;
            return false;
        });
    }
    
}
