package com.hhernaar.telephone.service;

import java.util.List;
import com.hhernaar.telephone.collection.Telephone;
import com.hhernaar.telephone.exception.BadRequestException;
import com.hhernaar.telephone.exception.ResourceNotFoundException;

/**
 * <h2>Telephone Service</h2>
 * 
 * <p>
 * Service in charge to manage telephones and its information.
 * </p>
 * 
 * @author hhernaar.
 * @since v1.
 */
public interface TelephoneService {

  /**
   * Saves a given entity.
   * 
   * @param telephone The {@link Telephone} entity to save. Must not be {@code null}
   * @return The saved {@link Telephone}.
   * 
   */
  public Telephone save(Telephone telephone);


  /**
   * Find entity by its id.
   * 
   * @param id String that represents {@link Telephone} Id.
   * @return The object request.
   * @throws ResourceNotFoundException If The resource is not present.
   * 
   */
  public Telephone findById(String id) throws ResourceNotFoundException;


  /**
   * Find entity by its imei.
   * 
   * @param imei String that represents {@link Telephone} IMEI.
   * @return The object request.
   * @throws ResourceNotFoundException If The resource is not present.
   * 
   */
  public Telephone findByImei(String imei) throws ResourceNotFoundException;


  /**
   * Lis telephones.
   * 
   * @param page The number page
   * @param size The size page
   * @return A list with all registers.
   * 
   */
  public List<Telephone> list(int page, int size);


  /**
   * Delete entity by its id.
   * 
   * @param id String that represents {@link Telephone} Id.
   */
  public void delete(String id);


  /**
   * Validate the imei
   * 
   * @param id The resource id if its present.
   * @param imei The IMEI
   * 
   * @throws BadRequestException If the Imei is invalid
   */
  public void validImei(String id, String imei) throws BadRequestException;
}
