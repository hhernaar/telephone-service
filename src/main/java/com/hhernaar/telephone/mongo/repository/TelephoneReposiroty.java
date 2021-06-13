package com.hhernaar.telephone.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.hhernaar.telephone.collection.Telephone;

public interface TelephoneReposiroty extends MongoRepository<Telephone, String> {

  // Optional<Telephone> findByImei(String imei);
}
