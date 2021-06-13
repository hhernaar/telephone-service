package com.hhernaar.telephone.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.hhernaar.telephone.util.MapperUtil;
import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class BeanResourceConfig {

  @Value("${header.secret}")
  private String headerSecret;

  @Bean("hashSecret")
  public String hashSecret() {
    String sha256 = MapperUtil.sha256(headerSecret);
    log.info("[{}]-sha256::[{}]", headerSecret, sha256);
    return sha256;
  }


}
