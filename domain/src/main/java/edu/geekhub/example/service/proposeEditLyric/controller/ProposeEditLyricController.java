package edu.geekhub.example.service.proposeEditLyric.controller;

import edu.geekhub.example.authentication.user.model.User;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyric;
import edu.geekhub.example.service.proposeEditLyric.model.ProposeEditLyricDto;
import edu.geekhub.example.service.proposeEditLyric.service.ProposeEditLyricService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProposeEditLyricController {

    private final ProposeEditLyricService proposeEditLyricService;

    public ProposeEditLyricController(ProposeEditLyricService proposeEditLyricService) {
        this.proposeEditLyricService = proposeEditLyricService;
    }

    @PostMapping("/propose-edit/add")
    public ResponseEntity<ProposeEditLyricDto> addPropose(@RequestBody ProposeEditLyric proposeEdit,
        @RequestParam("songId") UUID songId) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (proposeEdit.getLyricText() != null && !proposeEdit.getLyricText().isBlank()) {
            ProposeEditLyricDto addedPropose = proposeEditLyricService.addPropose(user, proposeEdit,
                songId);
            if (addedPropose != null) {
                return ResponseEntity.ok(addedPropose);
            }
        }
        return ResponseEntity.badRequest().build();
    }

}
