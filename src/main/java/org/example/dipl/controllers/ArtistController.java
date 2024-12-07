package org.example.dipl.controllers;

import org.example.dipl.model.Artist;
import org.example.dipl.repo.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/artists")
public class ArtistController {
    @Autowired
    private ArtistRepository artistRepository;
    // Сторінка для керування авторами
    @GetMapping
    public String manageArtists(Model model) {
        model.addAttribute("artists", artistRepository.findAll());
        return "admin/manage_artists"; // HTML сторінка для перегляду/редагування авторів
    }

    @GetMapping("/add")
    public String addArtistForm(Model model) {
        model.addAttribute("artist", new Artist());
        return "admin/add_artist"; // Форма для додавання нового автора
    }

    @PostMapping("/add")
    public String addArtist(@ModelAttribute Artist artist) {
        artistRepository.save(artist);
        return "redirect:/admin/artists";
    }

    @GetMapping("/edit/{id}")
    public String editArtistForm(@PathVariable Long id, Model model) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found"));
        model.addAttribute("artist", artist);
        return "admin/edit_artist"; // Форма для редагування автора
    }

    @PostMapping("/edit/{id}")
    public String editArtist(@PathVariable Long id, @ModelAttribute Artist artist) {
        Artist existingArtist = artistRepository.findById(id).orElseThrow(() -> new RuntimeException("Artist not found"));
        existingArtist.setNameArtist(artist.getNameArtist());
        existingArtist.setDescriptioneArtist(artist.getDescriptioneArtist());
        artistRepository.save(existingArtist);
        return "redirect:/admin/artists";
    }

    @PostMapping("/delete/{id}")
    public String deleteArtist(@PathVariable Long id) {
        artistRepository.deleteById(id);
        return "redirect:/admin/artists";
    }
}
