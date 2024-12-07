package org.example.dipl.controllers;

import org.example.dipl.model.Chapter;
import org.example.dipl.model.TitleData;
import org.example.dipl.repo.ChapterRepository;
import org.example.dipl.repo.TitleDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/titles/{titleId}/chapters")
public class ChapterControllerAdmin {

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private TitleDataRepository titleDataRepository;

    @GetMapping
    public String getChapters(@PathVariable Long titleId, Model model) {
        TitleData title = titleDataRepository.findById(titleId)
                .orElseThrow(() -> new RuntimeException("Title not found"));
        List<Chapter> chapters = chapterRepository.findByTitle_IdTitle(titleId);
        model.addAttribute("title", title);
        model.addAttribute("chapters", chapters);
        return "admin/manage_chapters";
    }

    @GetMapping("/add")
    public String addChapterForm(@PathVariable Long titleId, Model model) {
        TitleData title = titleDataRepository.findById(titleId)
                .orElseThrow(() -> new RuntimeException("Title not found"));
        Chapter chapter = new Chapter();
        chapter.setTitle(title);
        model.addAttribute("chapter", chapter);
        return "admin/add_chapter";
    }

    @PostMapping("/add")
    public String addChapter(@PathVariable Long titleId, @ModelAttribute Chapter chapter) {
        TitleData title = titleDataRepository.findById(titleId)
                .orElseThrow(() -> new RuntimeException("Title not found"));
        chapter.setTitle(title);
        chapter.setDateReleaseChapter(LocalDate.now());
        chapterRepository.save(chapter);
        return "redirect:/admin/titles/" + titleId + "/chapters";
    }

    @GetMapping("/{chapterId}/edit")
    public String editChapterForm(@PathVariable Long titleId, @PathVariable Long chapterId, Model model) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        model.addAttribute("chapter", chapter);
        return "admin/edit_chapter";
    }

    @PostMapping("/{chapterId}/edit")
    public String editChapter(@PathVariable Long titleId, @PathVariable Long chapterId, @ModelAttribute Chapter chapterData) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        chapter.setNameChapter(chapterData.getNameChapter());
        chapter.setNumberChapter(chapterData.getNumberChapter());
        chapterRepository.save(chapter);
        return "redirect:/admin/titles/" + titleId + "/chapters";
    }

    @PostMapping("/delete/{chapterId}")
    public String deleteChapter(@PathVariable Long titleId, @PathVariable Long chapterId) {
        chapterRepository.deleteById(chapterId);
        return "redirect:/admin/titles/" + titleId + "/chapters";
    }
}

