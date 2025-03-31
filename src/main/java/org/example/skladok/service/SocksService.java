package org.example.skladok.service;

import org.example.skladok.dto.SocksDto;

public interface SocksService {


    void addToStorage(SocksDto socksDto);

    void retrieveFromStorage(SocksDto socksDto);

    Long getSocksQuantity(String itemColor, String compareType, Integer materialPercentage);

}
