/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import domain.learningUtility.TargetGroup;

/**
 *
 * @author Append
 */
public class TargetGroupCatalog extends Catalog<TargetGroup> implements ITargetGroupCatalog  
{

    public TargetGroupCatalog() {
        super(TargetGroup.class);
    }

    @Override
    public TargetGroup getByName(String name) {
        loadEntities();
        return entities.stream().filter(entity -> entity.getName().contains(name)).findFirst().get();
    }

}
    
