package org.example.dipl.controllers;

import jakarta.persistence.criteria.Predicate;
import org.example.dipl.model.Genre;
import org.example.dipl.model.StatusData;
import org.example.dipl.model.TitleData;
import org.example.dipl.model.User;
import org.example.dipl.repo.GenreRepository;
import org.example.dipl.repo.StatusDataRepository;
import org.example.dipl.repo.TitleDataRepository;
import org.example.dipl.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class CatalogController {

    @Autowired
    private TitleDataRepository titleDataRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private StatusDataRepository statusDataRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/catalog")
    public String showCatalog(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) String searchQuery,
            Model model, Principal principal) {

        List<Genre> genres = genreRepository.findAll();
        List<StatusData> statusData = statusDataRepository.findAll();
        List<TitleData> titles;

        if (genreId == null && statusId == null && (searchQuery == null || searchQuery.isEmpty())) {
            // Якщо фільтри не обрані, показати всі комікси
            titles = titleDataRepository.findAll();
        } else {

            // Фільтрація за умовами
            titles = titleDataRepository.findAll((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                if (genreId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("genres").get("idGenre"), genreId));
                }

                if (statusId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status").get("idStatus"), statusId));
                }

                if (searchQuery != null && !searchQuery.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("nameTitle"), "%" + searchQuery + "%"));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            });
        }

        model.addAttribute("titles", titles);
        model.addAttribute("genres", genres);
        model.addAttribute("status", statusData);
        model.addAttribute("selectedGenre", genreId);
        model.addAttribute("selectedStatus", statusId);
        model.addAttribute("searchQuery", searchQuery);

        return "catalog";
    }
    @GetMapping("/marker")
    public String showMarker(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) String searchQuery,
            Model model, Principal principal) {

        if (principal == null) {
            throw new RuntimeException("User must be authenticated to view bookmarks");
        }

        String username = principal.getName();
        User user = userRepository.findByLoginUser(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Genre> genres = genreRepository.findAll();
        List<StatusData> statusData = statusDataRepository.findAll();
        List<TitleData> titles;

        if (genreId == null && statusId == null && (searchQuery == null || searchQuery.isEmpty())) {
            // Якщо фільтри не обрані, показати комікси лише з закладок користувача
            titles = new ArrayList<>(user.getMarkedTitles());
        } else {
            // Фільтрація закладок користувача
            titles = titleDataRepository.findAll((root, query, criteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();

                // Фільтруємо комікси, які є в закладках
                predicates.add(root.in(user.getMarkedTitles()));

                if (genreId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("genres").get("idGenre"), genreId));
                }

                if (statusId != null) {
                    predicates.add(criteriaBuilder.equal(root.get("status").get("idStatus"), statusId));
                }

                if (searchQuery != null && !searchQuery.isEmpty()) {
                    predicates.add(criteriaBuilder.like(root.get("nameTitle"), "%" + searchQuery + "%"));
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            });
        }

        model.addAttribute("titles", titles);
        model.addAttribute("genres", genres);
        model.addAttribute("status", statusData);
        model.addAttribute("selectedGenre", genreId);
        model.addAttribute("selectedStatus", statusId);
        model.addAttribute("searchQuery", searchQuery);

        return "marker";
    }

}

