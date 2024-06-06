package com.lights5.com.message.publisher;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.lights5.com.message.publisher.EventType.USER_CREATED_EVENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
class UsersController {

    private final UsersService usersService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody User user) {

        usersService.publishMessage(user, USER_CREATED_EVENT);
    }
}
