package com.ucbcba.demo.services;

import com.ucbcba.demo.entities.LevelPrice;
import com.ucbcba.demo.repositories.LevelPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class LevelPriceServiceImpl implements LevelPriceService {

    private LevelPriceRepository levelPriceRepository;

    @Autowired
    @Qualifier(value = "levelPriceRepository")
    public void setLevelPriceRepository(LevelPriceRepository levelPriceRepository) {
        this.levelPriceRepository = levelPriceRepository;
    }

    @Override
    public Iterable<LevelPrice> listAllLevelPrices() {
        return this.levelPriceRepository.findAll();
    }

    public void saveLevelPrice(LevelPrice levelPrice) {
        this.levelPriceRepository.save(levelPrice);
    }

    public LevelPrice getLevelPrice(Integer id) {
        return this.levelPriceRepository.findOne(id);
    }

    public void deleteLevelPrice(Integer id) {
        this.levelPriceRepository.delete(id);
    }
    
}
