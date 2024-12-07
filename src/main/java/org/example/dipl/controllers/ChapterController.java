package org.example.dipl.controllers;

import org.example.dipl.model.Chapter;
import org.example.dipl.model.Page;
import org.example.dipl.repo.ChapterRepository;
import org.example.dipl.repo.TitleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@Controller
public class ChapterController {
    @Autowired
    private ChapterRepository chapterRepository;

    @GetMapping("/titles/{idTitle}/chapters/{idChapter}")
    public String getChapterPages(@PathVariable Long idTitle, @PathVariable Long idChapter, Model model) {
        // Отримуємо розділ
        Chapter chapter = chapterRepository.findById(idChapter)
                .orElseThrow(() -> new RuntimeException("Розділ не знайдено"));

        // Отримуємо сторінки цього розділу
        Set<Page> pages = chapter.getPages();

        model.addAttribute("chapter", chapter);
        model.addAttribute("pages", pages);

        return "chapter-pages"; // Назва HTML файлу
    }

}
