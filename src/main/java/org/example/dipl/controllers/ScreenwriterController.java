package org.example.dipl.controllers;


import org.example.dipl.model.Screenwriter;
import org.example.dipl.repo.ScreenwriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/screenwriters")
public class ScreenwriterController {
    @Autowired
    private ScreenwriterRepository screenwriterRepository;
    // Сторінка для керування авторами
    @GetMapping
    public String manageScreenwriters(Model model) {
        model.addAttribute("screenwriters", screenwriterRepository.findAll());
        return "admin/screenwriter/manage_screenwriter"; // HTML сторінка для перегляду/редагування сценаристів
    }

    // Форма для додавання нового сценариста
    @GetMapping("/add")
    public String addScreenwriterForm(Model model) {
        model.addAttribute("screenwriter", new Screenwriter());
        return "admin/screenwriter/add_screenwriter"; // Форма для додавання нового сценариста
    }

    // Обробка форми для додавання нового сценариста
    @PostMapping("/add")
    public String addScreenwriter(@ModelAttribute Screenwriter screenwriter) {
        screenwriterRepository.save(screenwriter);
        return "redirect:/admin/screenwriters"; // Перехід до списку сценаристів
    }

    // Форма для редагування сценариста
    @GetMapping("/edit/{id}")
    public String editScreenwriterForm(@PathVariable Long id, Model model) {
        Screenwriter screenwriter = screenwriterRepository.findById(id).orElseThrow(() -> new RuntimeException("Screenwriter not found"));
        model.addAttribute("screenwriter", screenwriter);
        return "admin/screenwriter/edit_screenwriter"; // Форма для редагування сценариста
    }

    // Обробка форми для редагування сценариста
    @PostMapping("/edit/{id}")
    public String editScreenwriter(@PathVariable Long id, @ModelAttribute Screenwriter screenwriter) {
        Screenwriter existingScreenwriter = screenwriterRepository.findById(id).orElseThrow(() -> new RuntimeException("Screenwriter not found"));
        existingScreenwriter.setNameScreenwriter(screenwriter.getNameScreenwriter());
        existingScreenwriter.setDescriptionScreenwriter(screenwriter.getDescriptionScreenwriter());
        screenwriterRepository.save(existingScreenwriter);
        return "redirect:/admin/screenwriters"; // Перехід до списку сценаристів
    }

    // Видалення сценариста
    @PostMapping("/delete/{id}")
    public String deleteScreenwriter(@PathVariable Long id) {
        screenwriterRepository.deleteById(id);
        return "redirect:/admin/screenwriters"; // Перехід до списку сценаристів
    }
}
