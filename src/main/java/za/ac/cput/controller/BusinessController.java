/**Author : Bukhobenkosi Mbinda*/

package za.ac.cput.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Business;
import za.ac.cput.service.IBusinessService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/businesses")
public class BusinessController {

    private final IBusinessService businessService;

    public BusinessController(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping
    public ResponseEntity<Business> createBusiness(@RequestBody Business business) {
        Business created = businessService.create(business);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public List<Business> getAllBusinesses() {
        return businessService.getAll();
    }


    @GetMapping("/registration/{regNumber}")
    public ResponseEntity<Business> getBusinessByRegistrationNumber(@PathVariable String regNumber) {
        Optional<Business> business = businessService.findByRegistrationNumber(regNumber);
        return business.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{businessId}/activate")
    public ResponseEntity<String> activateBusiness(@PathVariable Long businessId) {
        boolean success = businessService.toggleBusinessStatus(businessId, true);
        if (success) {
            return ResponseEntity.ok("Business activated successfully");
        }
        return ResponseEntity.badRequest().body("Business not found");
    }


    @PatchMapping("/{businessId}/deactivate")
    public ResponseEntity<String> deactivateBusiness(@PathVariable Long businessId) {
        boolean success = businessService.toggleBusinessStatus(businessId, false);
        if (success) {
            return ResponseEntity.ok("Business deactivated successfully");
        }
        return ResponseEntity.badRequest().body("Business not found");
    }


    @PutMapping("/{businessId}")
    public ResponseEntity<Business> updateBusiness(@PathVariable Long businessId,
                                                   @RequestBody Business businessDetails) {
        Optional<Business> existing = businessService.read(businessId);
        if (existing.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Business updated = businessService.update(
                new Business.Builder()
                        .copy(existing.get())
                        .setCompanyName(businessDetails.getCompanyName())
                        .setRegistrationNumber(businessDetails.getRegistrationNumber())
                        .setIndustry(businessDetails.getIndustry())
                        .setContactNumber(businessDetails.getContactNumber())
                        .setEmail(businessDetails.getEmail())
                        .setAddress(businessDetails.getAddress())
                        .build()
        );
        return ResponseEntity.ok(updated);
    }
}
