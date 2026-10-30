package io.student.rococo.controller;

import io.student.rococo.model.ArtistJson;
import io.student.rococo.service.api.ArtistService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artist")
public class ArtistController {

    private final ArtistService artistService;

    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping()
    public Page<ArtistJson> getAllArtist(@PageableDefault Pageable pageable) {
        return artistService.getAllArtist(pageable);
    }

    @GetMapping("/{id}")
    protected ArtistJson getArtistById(@PathVariable String id) {
        return artistService.getArtistById(id);
    }

    @GetMapping(params = "name")
    protected Page<ArtistJson> searchArtistByName(@RequestParam String name, @PageableDefault Pageable pageable) {
        return artistService.searchArtistByName(name, pageable);
    }

    @PostMapping()
    public ArtistJson createArtist(@RequestBody ArtistJson artist, @AuthenticationPrincipal Jwt principal) {
        return artistService.createArtist(artist);
    }

    @PatchMapping()
    public ArtistJson updateArtist(@RequestBody ArtistJson artist, @AuthenticationPrincipal Jwt principal) {
        return artistService.updateArtist(artist);
    }

}
