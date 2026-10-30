package io.student.rococo.controller;

import io.student.rococo.model.MuseumJson;
import io.student.rococo.service.api.MuseumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/museum")
public class MuseumController {

    private final MuseumService museumService;

    @Autowired
    public MuseumController(MuseumService museumService) {
        this.museumService = museumService;
    }

    @GetMapping()
    public Page<MuseumJson> getAllMuseums(@PageableDefault Pageable pageable) {
        return museumService.getAllMuseums(pageable);
    }

    @GetMapping("/{id}")
    protected MuseumJson getMuseumById(@PathVariable String id) {
        return museumService.getMuseumById(id);
    }

    @GetMapping(params = "title")
    protected Page<MuseumJson> searchMuseumByName(@RequestParam String title, @PageableDefault Pageable pageable) {
        return museumService.searchMuseumByName(title, pageable);
    }

    @PostMapping()
    public MuseumJson createMuseum(@RequestBody MuseumJson museum, @AuthenticationPrincipal Jwt principal) {
        return museumService.createMuseum(museum);
    }

    @PatchMapping()
    public MuseumJson updateMuseum(@RequestBody MuseumJson museum, @AuthenticationPrincipal Jwt principal) {
        return museumService.updateMuseum(museum);
    }
}
