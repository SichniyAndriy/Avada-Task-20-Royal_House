package edu.avada.course.repository;

import edu.avada.course.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByCityAndStreetAndHouseNumber(String city, String street, String houseNumber);
}
