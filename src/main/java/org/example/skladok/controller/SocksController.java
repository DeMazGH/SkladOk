package org.example.skladok.controller;

import org.example.skladok.dto.SocksDto;
import org.example.skladok.service.SocksDataValidator;
import org.example.skladok.service.SocksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "storage/items")
public class SocksController {

    private final SocksService socksService;
    private final SocksDataValidator socksDataValidator;

    public SocksController(SocksService socksService, SocksDataValidator validator) {
        this.socksService = socksService;
        this.socksDataValidator = validator;
    }

    @PostMapping(value = "incoming")
    public ResponseEntity<?> addToStorage(@RequestBody SocksDto socksDto) {
        if (socksDataValidator.socksDtoIsValid(socksDto)) {
            socksService.addToStorage(socksDto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "outgoing")
    public ResponseEntity<?> retrieveFromStorage(@RequestBody SocksDto socksDto) {
        if (socksDataValidator.socksDtoIsValid(socksDto)) {
            socksService.retrieveFromStorage(socksDto);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Long> getSocksQuantity(
            @RequestParam String itemColor,
            @RequestParam String compareType,
            @RequestParam Integer materialPercentage) {
        if (socksDataValidator.getSocksQuantityRequestIsValid(itemColor, compareType, materialPercentage)) {
            return ResponseEntity.ok(socksService.getSocksQuantity(itemColor, compareType, materialPercentage));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
