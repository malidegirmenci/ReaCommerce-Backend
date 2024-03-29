package org.workintech.controller.user;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import org.workintech.converter.DtoConverter;
import org.workintech.dto.user.LoginUserResponse;
import org.workintech.service.user.UserService;


@AllArgsConstructor
@RestController
@RequestMapping("/verify")
public class VerifyController {
    private UserService userService;
    @GetMapping("/{token}")
    public LoginUserResponse verify(@Valid @PathVariable String token ) {
        return DtoConverter.convertToLoginUserResponse(userService.findByToken(token)) ;
    }
}
