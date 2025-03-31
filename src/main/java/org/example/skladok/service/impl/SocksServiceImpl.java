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

    /**
     * Метод регистрирует прибытие носков на склад. Если носков с характеристиками переданными в запросе нет на складе,
     * то будет создана новая запись в БД, в противном случае количество носков суммируется. itemColor не учитывает
     * регистр. Метод потокобезопасный.
     *
     * @param socksDto характеристики партии носков.
     */
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

    /**
     * Метод регистрирует убытие носков со склада. Если носков с характеристиками переданными в запросе нет на складе
     * или их количество недостаточно, то будет выброшено исключение. itemColor не учитывает
     * регистр. Метод потокобезопасный.
     *
     * @param socksDto характеристики партии носков.
     *
     * @throws NotEnoughSocksException при недостаточном количестве носков на складе.
     */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void retrieveFromStorage(SocksDto socksDto) throws NotEnoughSocksException {
        Socks socksInStock = socksRepository
                .findSocksByItemColorIgnoreCaseAndMaterialPercentage(socksDto.itemColor(), socksDto.materialPercentage());

        if (socksInStock == null || socksInStock.getUnits() < socksDto.units()) {
            throw new NotEnoughSocksException("There are not enough socks with the requested parameters in stock.");
        }

        socksInStock.setUnits(socksInStock.getUnits() - socksDto.units());
        socksRepository.save(socksInStock);
    }

    /**
     * Метод возвращает количество носков оставшихся на складе. itemColor не учитывает регистр.
     *
     * @param itemColor          цвет носков.
     * @param compareType        тип оператора сравнения для значения количества материала в составе носков.
     * @param materialPercentage значение процента материала в составе носков.
     *
     * @return общее количество носков на складе.
     */
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
