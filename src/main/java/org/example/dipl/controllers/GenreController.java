package org.example.dipl.controllers;

import org.example.dipl.model.Genre;
import org.example.dipl.repo.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/genres")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    // Сторінка для керування жанрами
    @GetMapping
    public String manageGenres(Model model) {
        model.addAttribute("genres", genreRepository.findAll());
        return "admin/genre/manage_genres"; // HTML сторінка для перегляду/редагування жанрів
    }

    // Сторінка для додавання нового жанру
    @GetMapping("/add")
    public String addGenreForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "admin/genre/add_genre"; // Форма для додавання нового жанру
    }

    // Обробка додавання нового жанру
    @PostMapping("/add")
    public String addGenre(@ModelAttribute Genre genre) {
        genreRepository.save(genre);
        return "redirect:/admin/genres"; // Перехід до списку жанрів
    }

    // Сторінка для редагування жанру
    @GetMapping("/edit/{id}")
    public String editGenreForm(@PathVariable Long id, Model model) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        model.addAttribute("genre", genre);
        return "admin/genre/edit_genre"; // Форма для редагування жанру
    }

    // Обробка редагування жанру
    @PostMapping("/edit/{id}")
    public String editGenre(@PathVariable Long id, @ModelAttribute Genre genre) {
        Genre existingGenre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));
        existingGenre.setNameGenre(genre.getNameGenre());
        genreRepository.save(existingGenre);
        return "redirect:/admin/genres"; // Перехід до списку жанрів
    }

    // Видалення жанру
    @PostMapping("/delete/{id}")
    public String deleteGenre(@PathVariable Long id) {
        genreRepository.deleteById(id);
        return "redirect:/admin/genres"; // Перехід до списку жанрів
    }
}

