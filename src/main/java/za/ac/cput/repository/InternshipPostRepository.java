/**
 * InternshipTrackingSystem
 * InternshipPostRepository.java
 * Author : Thapelo Ngwenya - 222260971
 * Date : 06 October 2025
 */
package za.ac.cput.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.ac.cput.domain.InternshipPost;

import java.util.Date;
import java.util.List;


@Repository
public interface InternshipPostRepository extends JpaRepository<InternshipPost, Integer> {

    List<InternshipPost> findByStatus(String status);

    List<InternshipPost> findByDeadlineAfter(Date date);

    List<InternshipPost> findByBusiness_BusinessId(int businessId);

    void deleteByBusiness_BusinessId(int businessId);
}

