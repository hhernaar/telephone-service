package com.hhernaar.telephone.service.impl;

import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.hhernaar.telephone.collection.Telephone;
import com.hhernaar.telephone.exception.BadRequestException;
import com.hhernaar.telephone.exception.ResourceNotFoundException;
import com.hhernaar.telephone.mongo.repository.TelephoneReposiroty;
import com.hhernaar.telephone.redis.repository.TelephoneCacheReposiroty;
import com.hhernaar.telephone.service.TelephoneService;
import com.hhernaar.telephone.util.StatusResponse;

@Service
public class TelephoneServiceImpl implements TelephoneService {

  @Autowired
  private TelephoneReposiroty telephoneReposiroty;

  @Autowired
  private TelephoneCacheReposiroty telephoneCacheReposiroty;

  @PostConstruct
  public void postConstruct() {
    telephoneCacheReposiroty.saveAll(telephoneReposiroty.findAll());
  }

  @Override
  public Telephone save(Telephone telephone) {
    telephoneCacheReposiroty.save(telephone);
    return telephoneReposiroty.save(telephone);

  }

  @Override
  public Telephone findById(String id) throws ResourceNotFoundException {
    Optional<Telephone> telehpone = telephoneReposiroty.findById(id);
    if (telehpone.isPresent()) {
      return telehpone.get();
    }
    throw new ResourceNotFoundException();
  }

  @Override
  public Telephone findByImei(String imei) throws ResourceNotFoundException {
    Optional<Telephone> telehpone = telephoneCacheReposiroty.findByImei(imei);
    if (telehpone.isPresent()) {
      return telehpone.get();
    }
    throw new ResourceNotFoundException();
  }

  @Override
  public List<Telephone> list(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return telephoneReposiroty.findAll(pageable).getContent();
  }

  @Override
  public void delete(String id) {
    telephoneReposiroty.deleteById(id);
  }


  @Override
  public void validImei(String id, String imei) throws BadRequestException {
    Optional<Telephone> cacheTelephone = telephoneCacheReposiroty.findByImei(imei);
    if (!StringUtils.hasLength(id)) { // creation
      if (cacheTelephone.isPresent()) {
        throw new BadRequestException(StatusResponse.BAD_REQUEST_IMEI);
      }
    } else { // update
      if (cacheTelephone.isPresent() && !id.equals(cacheTelephone.get().getId())) {
        throw new BadRequestException(StatusResponse.BAD_REQUEST_IMEI);
      }
    }
  }

}
