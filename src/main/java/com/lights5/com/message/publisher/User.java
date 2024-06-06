package com.lights5.com.message.publisher;

import java.time.LocalDateTime;

record User (
        String firstName,
        String lastName,
        String email,
        Long phoneNumber,
        Address address,
        LocalDateTime createdAt) {

    record Address (
            String city,
            String country,
            String zipcode) {

    }
}
