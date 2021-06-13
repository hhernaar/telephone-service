package com.hhernaar.telephone.dto;

import java.io.Serializable;
import org.springframework.http.HttpStatus;
import com.hhernaar.telephone.util.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Generic API Response Object.
 */
@Data
@AllArgsConstructor
public class ResponseApiDto implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer status;
  private Object message;

  public ResponseApiDto(HttpStatus httpStatus) {
    this.status = httpStatus.value();
    this.message = httpStatus.getReasonPhrase();
  }

  public ResponseApiDto(StatusResponse statusResponse) {
    this.status = statusResponse.val();
    this.message = statusResponse.msg();
  }


}
