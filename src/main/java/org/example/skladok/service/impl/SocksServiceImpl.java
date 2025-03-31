package org.example.skladok.service.impl;

import org.example.skladok.dto.SocksDto;
import org.example.skladok.entity.Socks;
import org.example.skladok.exception.NotEnoughSocksException;
import org.example.skladok.repository.SocksRepository;
import org.example.skladok.service.SocksService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.example.skladok.constant.Constant.GREATER_THAN;
import static org.example.skladok.constant.Constant.LESS_THAN;

@Service
public class SocksServiceImpl implements SocksService {

    private final SocksRepository socksRepository;

    public SocksServiceImpl(SocksRepository socksRepository) {
        this.socksRepository = socksRepository;
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addToStorage(SocksDto socksDto) {
        Socks socksInStock = socksRepository
                .findSocksByItemColorIgnoreCaseAndMaterialPercentage(socksDto.itemColor(), socksDto.materialPercentage());

        if (socksInStock == null) {
            Socks newBatchOfSocks = new Socks(null, socksDto.itemColor(), socksDto.materialPercentage(), socksDto.units());
            socksRepository.save(newBatchOfSocks);
        } else {
            socksInStock.setUnits(socksInStock.getUnits() + socksDto.units());
            socksRepository.save(socksInStock);
        }
    }

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void retrieveFromStorage(SocksDto socksDto) {
        Socks socksInStock = socksRepository
                .findSocksByItemColorIgnoreCaseAndMaterialPercentage(socksDto.itemColor(), socksDto.materialPercentage());

        if (socksInStock == null || socksInStock.getUnits() < socksDto.units()) {
            throw new NotEnoughSocksException("There are not enough socks with the requested parameters in stock.");
        }

        socksInStock.setUnits(socksInStock.getUnits() - socksDto.units());
        socksRepository.save(socksInStock);
    }

    @Override
    public Long getSocksQuantity(String itemColor, String compareType, Integer materialPercentage) {

        if (compareType.equalsIgnoreCase(GREATER_THAN)) {
            List<Socks> socks = socksRepository.findSocksByItemColorIgnoreCaseAndMaterialPercentageGreaterThan(itemColor, materialPercentage);
            return socks.stream()
                        .mapToLong(Socks::getUnits)
                        .sum();
        } else if (compareType.equalsIgnoreCase(LESS_THAN)) {
            List<Socks> socks = socksRepository.findSocksByItemColorIgnoreCaseAndMaterialPercentageLessThan(itemColor, materialPercentage);
            return socks.stream()
                        .mapToLong(Socks::getUnits)
                        .sum();
        } else {
            Socks socks = socksRepository.findSocksByItemColorIgnoreCaseAndMaterialPercentage(itemColor, materialPercentage);
            return socks == null ? 0 : socks.getUnits();
        }
    }

}
