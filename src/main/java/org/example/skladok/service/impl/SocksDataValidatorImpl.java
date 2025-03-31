package org.example.skladok.service.impl;

import org.example.skladok.dto.SocksDto;
import org.example.skladok.service.SocksDataValidator;
import org.springframework.stereotype.Service;

import static org.example.skladok.constant.Constant.*;

@Service
public class SocksDataValidatorImpl implements SocksDataValidator {

    @Override
    public boolean socksDtoIsValid(SocksDto socksDto) {
        if (socksDto == null) {
            return false;
        } else if (socksDto.itemColor() == null || socksDto.units() == null || socksDto.materialPercentage() == null) {
            return false;
        } else if (socksDto.itemColor().isBlank()) {
            return false;
        } else if (socksDto.materialPercentage() < 0 || socksDto.materialPercentage() > 100) {
            return false;
        } else {
            return socksDto.units() > 0;
        }
    }

    @Override
    public boolean getSocksQuantityRequestIsValid(String itemColor, String compareType, Integer materialPercentage) {
        if (itemColor == null || compareType == null || materialPercentage == null) {
            return false;
        } else if (itemColor.isBlank()) {
            return false;
        } else if (materialPercentage < 0 || materialPercentage > 100) {
            return false;
        } else {
            return compareType.equals(GREATER_THAN)
                    || compareType.equals(LESS_THAN)
                    || compareType.equals(EQUAL);
        }
    }

}
