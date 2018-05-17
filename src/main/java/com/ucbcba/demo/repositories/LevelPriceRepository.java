package com.ucbcba.demo.repositories;

import com.ucbcba.demo.entities.LevelPrice;
import org.springframework.data.repository.CrudRepository;
import javax.transaction.Transactional;

@Transactional
public interface LevelPriceRepository extends CrudRepository<LevelPrice, Integer>  {
}
