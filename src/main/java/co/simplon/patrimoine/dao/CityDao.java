package co.simplon.patrimoine.dao;

import co.simplon.patrimoine.model.City;

public interface CityDao {

	City createCity(City city);
    City getCityById(Long id);
    City updateCity(City city);
    void deleteCityById(Long id);

}

