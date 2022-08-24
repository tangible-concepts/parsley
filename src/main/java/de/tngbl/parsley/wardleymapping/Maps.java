package de.tngbl.parsley.wardleymapping;

import org.jmolecules.ddd.annotation.Repository;

@Repository
public interface Maps {

    void persist(WardleyMap map);

}
