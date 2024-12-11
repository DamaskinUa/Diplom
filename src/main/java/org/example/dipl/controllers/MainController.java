package org.example.dipl.controllers;

import org.example.dipl.model.Artist;
import org.example.dipl.model.Screenwriter;
import org.example.dipl.model.Translate;
import org.example.dipl.repo.ArtistRepository;
import org.example.dipl.repo.ScreenwriterRepository;
import org.example.dipl.repo.TranslateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainController {
    @Autowired
    private ArtistRepository artistRepo;
    @Autowired
    ScreenwriterRepository screenwriterRepo;
    @Autowired
    TranslateRepository translateRepo;
    @GetMapping("/artist/{id}")
    public String getArtist(@PathVariable Long id, Model model) {
        Artist artist = artistRepo.findById(id).orElse(null);  // Отримуємо сам об'єкт або null, якщо не знайдений
        if (artist != null) {
            model.addAttribute("artist", artist);
        } else {
            // Можна додати повідомлення про помилку або перенаправлення
            model.addAttribute("errorMessage", "Автор не знайдений");
        }
        return "artistPage";
    }

    @GetMapping("/screenwriter/{id}")
    public String getScreenwriter(@PathVariable Long id, Model model) {
        // Логіка отримання даних про сценариста
        Screenwriter screenwriter = screenwriterRepo.findById(id).orElse(null);  // Отримуємо сам об'єкт або null, якщо не знайдений
        if (screenwriter != null) {
            model.addAttribute("screenwriter", screenwriter);
        } else {
            // Можна додати повідомлення про помилку або перенаправлення
            model.addAttribute("errorMessage", "Автор не знайдений");
        }
        return "screenwriterPage";
    }

    @GetMapping("/translator/{id}")
    public String getTranslator(@PathVariable Long id, Model model) {
        // Логіка отримання даних про перекладача
        Translate translator = translateRepo.findById(id).orElse(null);  // Отримуємо сам об'єкт або null, якщо не знайдений
        if (translator != null) {
            model.addAttribute("translate", translator);
        } else {
            // Можна додати повідомлення про помилку або перенаправлення
            model.addAttribute("errorMessage", "Автор не знайдений");
        }
        model.addAttribute("translator", translateRepo.findById(id));
        return "translatorPage";
    }

}
