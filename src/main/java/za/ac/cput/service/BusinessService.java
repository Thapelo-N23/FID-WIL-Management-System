/**Author : Bukhobenkosi Mbinda*/

package za.ac.cput.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.ac.cput.domain.Business;
import za.ac.cput.repository.BusinessRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
    public class BusinessService implements IBusinessService {

    private final BusinessRepository repository;

    public BusinessService(BusinessRepository repository) {
        this.repository = repository;
    }


    @Override
    public Business create(Business entity) {

        if (repository.findByRegistrationNumber(entity.getRegistrationNumber()).isPresent()) {
            throw new IllegalArgumentException("Business with registration number already exists: "
                    + entity.getRegistrationNumber());
        }


        if (repository.findByEmail(entity.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Business with email already exists: "
                    + entity.getEmail());
        }

        return repository.save(entity);
    }

    @Override
    public Optional<Business> read(Long id) {
        return repository.findById(id);
    }

    @Override
    public Business update(Business entity) {
        if (entity.getId() == null || !repository.existsById(entity.getId())) {
            throw new IllegalArgumentException("Business not found with ID: " + entity.getId());
        }
        return repository.save(entity);
    }

    @Override
    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    @Override
    public List<Business> getAll() {
        return repository.findAll();
    }


    @Override
    public Optional<Business> findByRegistrationNumber(String registrationNumber) {
        return repository.findByRegistrationNumber(registrationNumber);
    }

    @Override
    public Optional<Business> findByCompanyName(String companyName) {
        return repository.findByCompanyName(companyName);
    }

    @Override
    public Optional<Business> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<Business> findByActive(Boolean active) {
        return repository.findByActive(active);
    }

    @Override
    public List<Business> findByIndustry(String industry) {
        return repository.findByIndustry(industry);
    }

    @Override
    public boolean toggleBusinessStatus(Long businessId, boolean activate) {
        Optional<Business> businessOpt = repository.findById(businessId);
        if (businessOpt.isEmpty()) {
            return false;
        }
        Business business = businessOpt.get();
        business.setActive(activate);
        repository.save(business);
        return true;
    }
}

