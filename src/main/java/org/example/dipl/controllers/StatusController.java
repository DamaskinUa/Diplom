package org.example.dipl.controllers;

import org.example.dipl.model.StatusData;
import org.example.dipl.repo.StatusDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/statuses")
public class StatusController {

    @Autowired
    private StatusDataRepository statusDataRepository;

    // Сторінка для керування статусами
    @GetMapping
    public String manageStatuses(Model model) {
        model.addAttribute("statuses", statusDataRepository.findAll());
        return "admin/status/manage_statuses"; // HTML сторінка для перегляду/редагування статусів
    }

    // Сторінка для додавання нового статусу
    @GetMapping("/add")
    public String addStatusForm(Model model) {
        model.addAttribute("status", new StatusData());
        return "admin/status/add_status"; // Форма для додавання нового статусу
    }

    // Обробка додавання нового статусу
    @PostMapping("/add")
    public String addStatus(@ModelAttribute StatusData statusData) {
        statusDataRepository.save(statusData);
        return "redirect:/admin/statuses"; // Перехід до списку статусів
    }

    // Сторінка для редагування статусу
    @GetMapping("/edit/{id}")
    public String editStatusForm(@PathVariable Long id, Model model) {
        StatusData statusData = statusDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status not found"));
        model.addAttribute("status", statusData);
        return "admin/status/edit_status"; // Форма для редагування статусу
    }

    // Обробка редагування статусу
    @PostMapping("/edit/{id}")
    public String editStatus(@PathVariable Long id, @ModelAttribute StatusData statusData) {
        StatusData existingStatus = statusDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Status not found"));
        existingStatus.setNameStatus(statusData.getNameStatus());
        statusDataRepository.save(existingStatus);
        return "redirect:/admin/statuses"; // Перехід до списку статусів
    }

    // Видалення статусу
    @PostMapping("/delete/{id}")
    public String deleteStatus(@PathVariable Long id) {
        statusDataRepository.deleteById(id);
        return "redirect:/admin/statuses"; // Перехід до списку статусів
    }
}
