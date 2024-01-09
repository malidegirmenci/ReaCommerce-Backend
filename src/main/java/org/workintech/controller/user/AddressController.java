package org.workintech.controller.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.workintech.dto.user.AddressResponse;
import org.workintech.entity.user.Address;
import org.workintech.service.user.AddressService;

import java.util.List;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/address")
public class AddressController {
    private AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AddressResponse save(@Validated @RequestBody Address address){
        return addressService.save(address);
    }

    @GetMapping("/{token}")
    public List<AddressResponse> getAll(@NotBlank @PathVariable String token){
        return addressService.getAll(token);
    }

}
