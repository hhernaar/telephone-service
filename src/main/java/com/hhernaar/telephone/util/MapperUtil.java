package com.hhernaar.telephone.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

/**
 * Utility class to transform objects
 * 
 * @author hhernaar
 * @since v1.
 */
@UtilityClass
@Log4j2
public class MapperUtil {

  private ObjectMapper mapper =
      new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

  /**
   * Write object as its json string representation.
   * 
   * @param obj The object to transform
   * @return JSON string
   * 
   */
  public String toJson(Object obj) {
    try {
      return mapper.writeValueAsString(obj);
    } catch (Exception ex) {
      log.error("Can not parse - [{}]", obj);
      log.error(ex);
      ex.printStackTrace();
      return "";
    }
  }


  /**
   * Transform string msg to DTO
   * 
   * @param msg The string source
   * @param clazz The target class
   * @return The final object
   */
  public <T> T ToDTO(String msg, Class<T> clazz) {
    try {
      Map<String, Object> mapMessage =
          mapper.readValue(msg, new TypeReference<Map<String, Object>>() {});
      return mapper.convertValue(mapMessage, clazz);
    } catch (Exception ex) {
      log.error("an not parse::[{}]", msg);
      log.error(ex);
      ex.printStackTrace();
      return null;
    }
  }


  /**
   * Generate SHA-256 form string.
   * 
   * @param src The source string.
   * @return Hexadecimal string that represents the sha256.
   */
  public String sha256(String src) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] hash = digest.digest(src.getBytes(StandardCharsets.UTF_8));

      StringBuilder hexString = new StringBuilder(2 * hash.length);
      for (int i = 0; i < hash.length; i++) {
        String hex = Integer.toHexString(0xff & hash[i]);
        if (hex.length() == 1) {
          hexString.append('0');
        }
        hexString.append(hex);
      }
      return hexString.toString();
    } catch (Exception e) {
      log.error("Error creating hash");
      log.error(e);
      e.printStackTrace();
      return "";
    }
  }



}
