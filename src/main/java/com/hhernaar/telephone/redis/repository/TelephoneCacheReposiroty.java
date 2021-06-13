package com.hhernaar.telephone.redis.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.hhernaar.telephone.collection.Telephone;

@Repository
public interface TelephoneCacheReposiroty extends CrudRepository<Telephone, String> {

  Optional<Telephone> findByImei(String imei);
}
