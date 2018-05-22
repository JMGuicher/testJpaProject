package co.simplon.patrimoine.dao;

import co.simplon.patrimoine.model.Monument;

public interface MonumentDao {
	Monument createMonument(Monument monument);
    Monument getMonumentById(Long id);
    Monument updateMonument(Monument monument);
    void deleteMonumentById(Long id);
}


