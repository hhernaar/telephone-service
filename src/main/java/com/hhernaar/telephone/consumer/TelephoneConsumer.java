package com.hhernaar.telephone.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.hhernaar.telephone.collection.Telephone;
import com.hhernaar.telephone.service.TelephoneService;
import com.hhernaar.telephone.util.MapperUtil;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class TelephoneConsumer {

  @Autowired
  private TelephoneService telephoneService;

  @RabbitListener(queues = "${rabbit.queue.telephone.save}")
  private void listener(String msg) {
    log.info("TelephoneConsumer msg::[{}]", msg);
    telephoneService.save(MapperUtil.ToDTO(msg, Telephone.class));
  }

}
