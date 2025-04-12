package com.example.expressfood.controller;

import com.example.expressfood.dto.request.DeliveryPersonRequest;
import com.example.expressfood.dto.response.DeliveryPersonResponse;
import com.example.expressfood.dto.response.PageResponse;
import com.example.expressfood.service.IDeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    IDeliveryService iDeliveryPersonService;

    @PostMapping("/deliveryPerson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public DeliveryPersonResponse addDeliveryPerson(@RequestBody DeliveryPersonRequest deliveryPersonRequest){
        return iDeliveryPersonService.addDeliveryPerson(deliveryPersonRequest);
    }

    @PutMapping("/deliveryPerson")
    @PreAuthorize("hasAuthority('DELIVERY')")
    public DeliveryPersonResponse updateDeliveryPerson(@RequestBody DeliveryPersonRequest deliveryPersonRequest){
        return iDeliveryPersonService.updateDeliveryPerson(deliveryPersonRequest);
    }

    @GetMapping("/deliveryPerson/{deliveryPersonId}")
    @PreAuthorize("hasAuthority('ADMIN') OR hasAuthority('DELIVERY')")
    public DeliveryPersonResponse getDeliveryPersonById(@PathVariable("deliveryPersonId") Long deliveryPersonId){
        return iDeliveryPersonService.getDeliveryPersonById(deliveryPersonId);
    }

    @GetMapping("/deliveryPerson")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<DeliveryPersonResponse> getAllDeliveryPerson(){
        return iDeliveryPersonService.getAllDeliveryPerson();
    }

    @GetMapping("/deliveryPerson/search")
    @PreAuthorize("hasAuthority('ADMIN')")
    public PageResponse<DeliveryPersonResponse> searchForDeliveryPerson(@RequestParam(required = false, defaultValue = "0") int page,
                                                    @RequestParam(required = false, defaultValue = "10") int size,
                                                    @RequestParam(required = false) String keyword){
        return iDeliveryPersonService.searchDeliveryPerson(page, size, keyword);
    }

}
