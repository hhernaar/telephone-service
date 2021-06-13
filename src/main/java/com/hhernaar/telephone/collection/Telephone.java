package com.hhernaar.telephone.collection;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

/**
 * Telephone Entity Representation.
 */
@Data
@Document(collection = "telephone")
public class Telephone {

  @Id
  private String id;

  @NotNull(message = "Must not be null")
  @Pattern(regexp = "[a-zA-Z0-9]+", message = "Must not contain special characters")
  private String brand;

  @NotNull(message = "Must not be null")
  @Pattern(regexp = "[a-zA-Z0-9\\s]+", message = "Must not contain special characters")
  private String model;

  @NotNull(message = "Must not be null")
  @Pattern(regexp = "[a-zA-Z0-9\\s]+", message = "Must not contain special characters")
  private String shortName;

  @NotNull
  @Size(min = 15, max = 17, message = "The size must has between 15 and 17 characters.")
  private String imei;

  @Pattern(regexp = "(^$|[0-9]{12})",
      message = "Invalid number '${validatedValue}', the size must be of 12 digits")
  private String cellphoneNumber;

  @Pattern(regexp = "^[\\w\\W]*@[\\w\\W]*", message = "Invalid email '${validatedValue}'")
  private String email;

  private Boolean isIOS = false;

  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date creationDate;

}
