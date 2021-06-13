package com.hhernaar.telephone.controller;

import java.util.List;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.hhernaar.telephone.collection.Telephone;
import com.hhernaar.telephone.dto.ResponseApiDto;
import com.hhernaar.telephone.exception.BadRequestException;
import com.hhernaar.telephone.exception.ResourceNotFoundException;
import com.hhernaar.telephone.service.TelephoneService;
import com.hhernaar.telephone.util.MapperUtil;
import com.hhernaar.telephone.util.StatusResponse;
import com.hhernaar.telephone.util.ValidatorUtil;

@RestController
@RequestMapping("/telephone")
public class TelephoneController {

  @Autowired
  private TelephoneService telephoneService;

  @Autowired
  private ValidatorUtil validator;

  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Value("${rabbit.queue.telephone.save}")
  private String queueTelephoneSave;

  @Autowired
  @Qualifier("hashSecret")
  private String hashSecret;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseApiDto save(@RequestHeader(name = "DATA", required = true) String hash,
      @RequestBody Telephone telephone) throws BadRequestException {
    this.validateHeader(hash);
    validator.validate(telephone, Telephone.class.getName());
    telephoneService.validImei(telephone.getId(), telephone.getImei());
    rabbitTemplate.convertAndSend(queueTelephoneSave, MapperUtil.toJson(telephone));
    return new ResponseApiDto(StatusResponse.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseApiDto update(@RequestHeader(name = "DATA", required = true) String hash,
      @PathVariable("id") String id, @RequestBody Telephone telephone) throws BadRequestException {
    this.validateHeader(hash);
    telephone.setId(id);
    validator.validate(telephone, Telephone.class.getName());
    telephoneService.validImei(telephone.getId(), telephone.getImei());
    rabbitTemplate.convertAndSend(queueTelephoneSave, MapperUtil.toJson(telephone));
    return new ResponseApiDto(StatusResponse.UPDATE);
  }

  @GetMapping("/{id}")
  public Telephone find(@RequestHeader(name = "DATA", required = true) String hash,
      @PathVariable("id") String id) throws ResourceNotFoundException, BadRequestException {
    this.validateHeader(hash);
    return telephoneService.findById(id);
  }

  @GetMapping("imei/{imei}")
  public Telephone findByImei(@RequestHeader(name = "DATA", required = true) String hash,
      @PathVariable("imei") String imei) throws ResourceNotFoundException, BadRequestException {
    this.validateHeader(hash);
    return telephoneService.findByImei(imei);
  }

  @GetMapping()
  public List<Telephone> list(@RequestHeader(name = "DATA", required = true) String hash,
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size)
      throws BadRequestException {
    this.validateHeader(hash);
    return telephoneService.list(page, size);
  }

  @DeleteMapping("/{id}")
  public ResponseApiDto delete(@RequestHeader(name = "DATA", required = true) String hash,
      @PathVariable("id") String id) throws BadRequestException {
    this.validateHeader(hash);
    telephoneService.delete(id);
    return new ResponseApiDto(StatusResponse.DELETE);
  }

  private void validateHeader(String hash) throws BadRequestException {
    if (!hashSecret.equals(hash)) {
      throw new BadRequestException(StatusResponse.BAD_REQUEST_HEADER);
    }
  }

}
