/**Author : Bukhobenkosi Mbinda*/

package za.ac.cput.factory;

import za.ac.cput.domain.Business;
import za.ac.cput.util.Helper;

public class BusinessFactory {

    public static Business createBusiness(String companyName,
                                          String registrationNumber,
                                          String industry,
                                          String contactNumber,
                                          String email,
                                          String address) {

        if (Helper.isNullOrEmpty(companyName)) {
            throw new IllegalArgumentException("Company name is required");
        }
        if (Helper.isNullOrEmpty(registrationNumber)) {
            throw new IllegalArgumentException("Registration number is required");
        }
        if (!Helper.isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format: " + email);
        }

        return new Business.Builder()
                .setCompanyName(companyName.trim())
                .setRegistrationNumber(registrationNumber.trim())
                .setIndustry(industry != null ? industry.trim() : null)
                .setContactNumber(contactNumber != null ? contactNumber.trim() : null)
                .setEmail(email.trim().toLowerCase())
                .setAddress(address != null ? address.trim() : null)
                .setActive(true)
                .build();
    }
}
