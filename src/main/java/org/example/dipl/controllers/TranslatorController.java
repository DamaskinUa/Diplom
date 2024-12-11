package org.example.dipl.controllers;

import org.example.dipl.model.Translate;
import org.example.dipl.repo.TranslateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/admin/translators")
public class TranslatorController {
    @Autowired
    private TranslateRepository translateRepository;

    // Сторінка для керування перекладачами
    @GetMapping
    public String manageTranslators(Model model) {
        model.addAttribute("translators", translateRepository.findAll());
        return "admin/translator/manage_translators"; // HTML сторінка для перегляду/редагування перекладачів
    }

    // Форма для додавання нового перекладача
    @GetMapping("/add")
    public String addTranslatorForm(Model model) {
        model.addAttribute("translator", new Translate());
        return "admin/translator/add_translator"; // Форма для додавання нового перекладача
    }

    // Обробка форми для додавання нового перекладача
    @PostMapping("/add")
    public String addTranslator(@ModelAttribute Translate translate) {
        translate.setDataRegistr(LocalDate.now());
        translateRepository.save(translate);
        return "redirect:/admin/translators"; // Перехід до списку перекладачів
    }

    // Форма для редагування перекладача
    @GetMapping("/edit/{id}")
    public String editTranslatorForm(@PathVariable Long id, Model model) {
        Translate translate = translateRepository.findById(id).orElseThrow(() -> new RuntimeException("Translator not found"));
        model.addAttribute("translator", translate);
        return "admin/translator/edit_translator"; // Форма для редагування перекладача
    }

    // Обробка форми для редагування перекладача
    @PostMapping("/edit/{id}")
    public String editTranslator(@PathVariable Long id, @ModelAttribute Translate translate) {
        Translate existingTranslator = translateRepository.findById(id).orElseThrow(() -> new RuntimeException("Translator not found"));
        existingTranslator.setNameTranslate(translate.getNameTranslate());
        existingTranslator.setDescriptionTranslate(translate.getDescriptionTranslate());
        translateRepository.save(existingTranslator);
        return "redirect:/admin/translators"; // Перехід до списку перекладачів
    }

    // Видалення перекладача
    @PostMapping("/delete/{id}")
    public String deleteTranslator(@PathVariable Long id) {
        translateRepository.deleteById(id);
        return "redirect:/admin/translators"; // Перехід до списку перекладачів
    }
}

