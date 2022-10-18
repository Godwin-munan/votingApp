package com.munan.votingApp.controller;

import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.exception.GlobalExceptionHandling;
import com.munan.votingApp.model.Gender;
import com.munan.votingApp.service.GenderService;
import com.munan.votingApp.util.HttpResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gender")
@Tag(name = "Gender Controller", description = "Gender Controller")
@AllArgsConstructor

public class GenderController extends GlobalExceptionHandling {
    private final GenderService genderService;

    @Operation(summary = "Add new gender type")
    @PostMapping("/add")
    public ResponseEntity<HttpResponse> add(@RequestBody Gender gender) throws AlreadyExistException {
        return genderService.addGender(gender);
    }

}
