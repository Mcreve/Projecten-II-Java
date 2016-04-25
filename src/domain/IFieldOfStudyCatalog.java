/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;
import domain.learningUtility.FieldOfStudy;
/**
 *
 * @author Append
 */
public interface IFieldOfStudyCatalog extends ICatalog<FieldOfStudy>
{
    public FieldOfStudy getByName(String name);

}