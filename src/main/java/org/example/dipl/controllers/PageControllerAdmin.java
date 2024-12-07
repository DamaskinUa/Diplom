package org.example.dipl.controllers;

import org.example.dipl.model.Chapter;
import org.example.dipl.model.Page;
import org.example.dipl.repo.ChapterRepository;
import org.example.dipl.repo.PageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/titles/{titleId}/chapters/{chapterId}/pages")
public class PageControllerAdmin {

    @Autowired
    private PageRepository pageRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @GetMapping
    public String getPages(@PathVariable Long titleId, @PathVariable Long chapterId, Model model) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        List<Page> pages = pageRepository.findByChapter_IdChapter(chapterId);
        model.addAttribute("chapter", chapter);
        model.addAttribute("pages", pages);
        return "admin/manage_pages";
    }

    @GetMapping("/add")
    public String addPageForm(@PathVariable Long titleId, @PathVariable Long chapterId, Model model) {
        Page page = new Page();
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        page.setChapter(chapter);
        model.addAttribute("page", page);
        return "admin/add_page";
    }

    @PostMapping("/add")
    public String addPage(@PathVariable Long titleId, @PathVariable Long chapterId, @ModelAttribute Page page, @RequestParam("image") MultipartFile image) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new RuntimeException("Chapter not found"));
        page.setChapter(chapter);

        // Збереження зображення
        String uploadDir = "uploads/pages";
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/titles/" + titleId + "/chapters/" + chapterId + "/pages/add?error";
            }
        }

        if (!image.isEmpty()) {
            try {
                String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFileName = UUID.randomUUID().toString() + extension;
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.write(filePath, image.getBytes());
                page.setImagePage("/uploads/pages/" + uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/titles/" + titleId + "/chapters/" + chapterId + "/pages/add?error";
            }
        }

        pageRepository.save(page);
        return "redirect:/admin/titles/" + titleId + "/chapters/" + chapterId + "/pages";
    }

    @GetMapping("/edit/{pageId}")
    public String editPageForm(@PathVariable Long titleId, @PathVariable Long chapterId, @PathVariable Long pageId, Model model) {
        Page page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Page not found"));
        model.addAttribute("page", page);
        return "admin/edit_page";
    }

    @PostMapping("/edit/{pageId}")
    public String editPage(@PathVariable Long titleId, @PathVariable Long chapterId, @PathVariable Long pageId, @ModelAttribute Page pageData, @RequestParam("image") MultipartFile image) {
        Page page = pageRepository.findById(pageId)
                .orElseThrow(() -> new RuntimeException("Page not found"));
        page.setNumberPage(pageData.getNumberPage());

        if (!image.isEmpty()) {
            try {
                String uploadDir = "uploads/pages";
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFileName = UUID.randomUUID().toString() + extension;
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.write(filePath, image.getBytes());
                page.setImagePage("/uploads/pages/" + uniqueFileName);
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/titles/" + titleId + "/chapters/" + chapterId + "/pages/edit/" + pageId + "?error";
            }
        }

        pageRepository.save(page);
        return "redirect:/admin/titles/" + titleId + "/chapters/" + chapterId + "/pages";
    }

    @PostMapping("/delete/{pageId}")
    public String deletePage(@PathVariable Long titleId, @PathVariable Long chapterId, @PathVariable Long pageId) {
        pageRepository.deleteById(pageId);
        return "redirect:/admin/titles/" + titleId + "/chapters/" + chapterId + "/pages";
    }
}

