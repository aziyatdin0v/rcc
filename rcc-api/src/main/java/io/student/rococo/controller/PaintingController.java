package io.student.rococo.controller;

import io.student.rococo.model.PaintingJson;
import io.student.rococo.service.api.PaintingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/painting")
public class PaintingController {

    private final PaintingService paintingService;

    public PaintingController(PaintingService paintingService) {
        this.paintingService = paintingService;
    }

    @GetMapping("/{id}")
    public PaintingJson getPaintingById(@PathVariable String id) {
        return paintingService.getPaintingById(id);
    }

    @GetMapping
    public Page<PaintingJson> getAllPaintings(@PageableDefault Pageable pageable) {
        return paintingService.getAllPaintings(pageable);
    }

    @GetMapping("/author/{artistId}")
    public Page<PaintingJson> getPaintingByArtist(@PathVariable String artistId, @PageableDefault Pageable pageable) {
        return paintingService.findPaintingByAuthorId(artistId, pageable);
    }

    @GetMapping(params = "title")
    public Page<PaintingJson> getPaintingByTitle(@RequestParam String title, @PageableDefault Pageable pageable) {
        return paintingService.findPaintingByTitle(title, pageable);
    }

    @PostMapping
    public PaintingJson createPainting(@RequestBody PaintingJson painting, @AuthenticationPrincipal Jwt principal) {
        return paintingService.createPainting(painting);
    }

    @PatchMapping
    public PaintingJson updatePainting(@RequestBody PaintingJson painting, @AuthenticationPrincipal Jwt principal) {
        return paintingService.updatePainting(painting);
    }
}
