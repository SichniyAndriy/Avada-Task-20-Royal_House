package edu.avada.course.configuration;

import edu.avada.course.AppUtil;
import edu.avada.course.model.entity.Unit.UnitType;
import edu.avada.course.repository.AddressRepository;
import edu.avada.course.repository.BidRepository;
import edu.avada.course.repository.CompanyServiceRepository;
import edu.avada.course.repository.NewBuildingRepository;
import edu.avada.course.repository.UnitRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "edu.avada.course")
public class AppConfig {
    @Bean
    AppUtil appUtil () {
        return new AppUtil();
    }

    @Bean
    CommandLineRunner commandLineRunner(
            AppUtil appUtil,
            BidRepository bidRepository,
            AddressRepository addressRepository,
            UnitRepository unitRepository,
            NewBuildingRepository newBuildingRepository,
            CompanyServiceRepository companyServiceRepository
    ) {
        return args -> {
            appUtil.initBids(bidRepository, 25);
            appUtil.initAddress(addressRepository, 100);
            appUtil.initUnits(unitRepository, UnitType.FLAT, 15);
            appUtil.initUnits(unitRepository, UnitType.HOUSE, 5);
            appUtil.initNewBuildings(newBuildingRepository, 10);
            appUtil.initService(companyServiceRepository, 10);
        };
    }
}
