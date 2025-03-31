package org.example.skladok.service;

import org.example.skladok.dto.SocksDto;

public interface SocksDataValidator {

    boolean socksDtoIsValid(SocksDto socksDto);

    boolean getSocksQuantityRequestIsValid(String itemColor, String compareType, Integer materialPercentage);

}
