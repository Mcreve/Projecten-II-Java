/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain.Interfaces;
import domain.learningUtility.TargetGroup;

/**
 *
 * @author Append
 */
public interface ITargetGroupCatalog extends ICatalog<TargetGroup>{
        public TargetGroup getByName(String name);

}