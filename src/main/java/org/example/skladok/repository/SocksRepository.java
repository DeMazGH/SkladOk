package org.example.skladok.repository;

import org.example.skladok.entity.Socks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocksRepository extends JpaRepository<Socks, Long> {

    Socks findSocksByItemColorIgnoreCaseAndMaterialPercentage(String itemColor, int materialPercentage);

    List<Socks> findSocksByItemColorIgnoreCaseAndMaterialPercentageGreaterThan(String itemColor,
                                                                               int materialPercentage);

    List<Socks> findSocksByItemColorIgnoreCaseAndMaterialPercentageLessThan(String itemColor, int materialPercentage);

}
