/**Author : Bukhobenkosi Mbinda*/

package za.ac.cput.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import za.ac.cput.domain.Business;

import java.util.Optional;
import java.util.List;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long> {

    Optional<Business> findByCompanyName(String companyName);

    Optional<Business> findByRegistrationNumber(String registrationNumber);

    Optional<Business> findByEmail(String email);

    List<Business> findByActive(Boolean active);

    List<Business> findByIndustry(String industry);
}
