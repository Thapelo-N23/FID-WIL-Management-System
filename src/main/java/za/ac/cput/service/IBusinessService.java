/**Author : Bukhobenkosi Mbinda*/

package za.ac.cput.service;

import za.ac.cput.domain.Business;

import java.util.List;
import java.util.Optional;

public interface IBusinessService extends IService<Business, Long> {

    // Find by registration number
    Optional<Business> findByRegistrationNumber(String registrationNumber);

    // Find by company name
    Optional<Business> findByCompanyName(String companyName);

    // Find by email
    Optional<Business> findByEmail(String email);

    // Find active/inactive businesses
    List<Business> findByActive(Boolean active);

    // Find businesses by industry
    List<Business> findByIndustry(String industry);

    // Activate / deactivate business
    boolean toggleBusinessStatus(Long businessId, boolean activate);
}

