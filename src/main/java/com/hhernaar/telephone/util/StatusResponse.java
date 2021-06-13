package com.hhernaar.telephone.util;

public enum StatusResponse {

  // @formatter:off
  CREATED(201, "The resource will be created behind scene."),
  UPDATE(200, "Resource will be updated behind scene."),
  DELETE(200, "Resource successfully deleted."),
  
  BAD_REQUEST_HEADER(400, "The Header 'DATA' is not valid."),
  BAD_REQUEST_IMEI(400, "The IMEI is already stored by other resource.");
  //@formatter:on

  private Integer status;
  private String description;

  StatusResponse(int status, String description) {
    this.status = status;
    this.description = description;
  }

  public int val() {
    return this.status;
  }

  public String msg() {
    return this.description;
  }

}
