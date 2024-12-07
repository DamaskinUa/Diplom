package org.example.dipl.controllers;

import org.example.dipl.model.Marker;
import org.example.dipl.model.TitleData;
import org.example.dipl.model.User;
import org.example.dipl.repo.MarkerRepository;
import org.example.dipl.repo.TitleDataRepository;
import org.example.dipl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.Arrays;

@Controller
@RequestMapping("/titles")
public class TitleController {
    @Autowired
    private Environment environment;

    @Autowired
    private TitleDataRepository titleDataRepository;
    @Autowired
    private MarkerRepository markerRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public String getTitle(@PathVariable Long id, Model model, Principal principal) {
        TitleData title = titleDataRepository.findById(id).orElseThrow(() -> new RuntimeException("Title not found"));
        model.addAttribute("title", title);
        if (principal != null) { // Перевірка, чи користувач автентифікований
            String username = principal.getName();
            User user = userRepository.findByLoginUser(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            boolean isMarked = markerRepository.existsByUserAndTitle(user, title);
            model.addAttribute("isMarked", isMarked); // Додаємо інформацію в модель
        }

        // Перевіряємо, який профіль активний: JAAS, Spring Security, або Apache Shiro
        boolean isJaasProfile = Arrays.asList(environment.getActiveProfiles()).contains("jaas");
        boolean isSpringSecurityProfile = Arrays.asList(environment.getActiveProfiles()).contains("spring-security");
        boolean isApacheShiroProfile = Arrays.asList(environment.getActiveProfiles()).contains("apache-shiro");

        // Передаємо інформацію у view
        model.addAttribute("isJaasProfile", isJaasProfile);
        model.addAttribute("isSpringSecurityProfile", isSpringSecurityProfile);
        model.addAttribute("isApacheShiroProfile", isApacheShiroProfile);
        return "title";
    }
    @PostMapping("/{id}/addBookmark")
    public String addBookmark(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User must be authenticated to add bookmarks");
        }

        String username = principal.getName();
        User user = userRepository.findByLoginUser(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TitleData title = titleDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Title not found"));

        if (!markerRepository.existsByUserAndTitle(user, title)) {
            Marker marker = new Marker();
            marker.setUser(user);
            marker.setTitle(title);
            markerRepository.save(marker);
        }

        return "redirect:/titles/" + id;
    }

    @PostMapping("/{id}/removeBookmark")
    public String removeBookmark(@PathVariable Long id, Principal principal) {
        if (principal == null) {
            throw new RuntimeException("User must be authenticated to remove bookmarks");
        }

        String username = principal.getName();
        User user = userRepository.findByLoginUser(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        TitleData title = titleDataRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Title not found"));

        Marker marker = markerRepository.findByUserAndTitle(user, title)
                .orElseThrow(() -> new RuntimeException("Bookmark not found"));
        markerRepository.delete(marker);

        return "redirect:/titles/" + id;
    }

}