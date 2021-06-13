package com.hhernaar.telephone.exception;

import java.util.List;
import com.hhernaar.telephone.util.StatusResponse;
import lombok.Getter;

public class BadRequestException extends Exception {
  private static final long serialVersionUID = 1L;

  @Getter
  List<String> errores;

  public BadRequestException() {
    super();
  }

  public BadRequestException(StatusResponse statusResponse) {
    super(statusResponse.msg());
  }

  public BadRequestException(List<String> errores) {
    this.errores = errores;
  }
}
