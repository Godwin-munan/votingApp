package com.munan.votingApp.controller;


import com.munan.votingApp.exception.AlreadyExistException;
import com.munan.votingApp.model.Position;
import com.munan.votingApp.service.PositionService;
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
@RequestMapping("/position")
@Tag(name = "Position Controller", description = "Position Controller")
@AllArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @Operation(summary = "Add new Position")
    @PostMapping("/addPosition")
    public ResponseEntity<HttpResponse> add(@RequestBody Position position) throws AlreadyExistException {

        return positionService.addPosition(position);
    }
}
