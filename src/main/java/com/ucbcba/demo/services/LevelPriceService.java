package com.ucbcba.demo.services;

import com.ucbcba.demo.entities.LevelPrice;

public interface LevelPriceService {

    Iterable<LevelPrice> listAllLevelPrices();

    void saveLevelPrice(LevelPrice levelPrice);

    LevelPrice getLevelPrice(Integer id);

    void deleteLevelPrice(Integer id);

}
