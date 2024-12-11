package org.example.dipl.controllers;

import org.example.dipl.model.Artist;
import org.example.dipl.model.Screenwriter;
import org.example.dipl.model.TitleData;
import org.example.dipl.model.Translate;
import org.example.dipl.repo.*;
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
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private TitleDataRepository titleDataRepository;
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ScreenwriterRepository screenwriterRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private TranslateRepository translateRepository;
    @Autowired
    private StatusDataRepository statusRepository;

    // Головна сторінка адмін панелі
    @GetMapping
    public String showAdminPanel() {
        return "admin/dashboard"; // HTML сторінка з кнопками переходу
    }

    // Сторінка для керування коміксами
    @GetMapping("/titles")
    public String manageTitles(@RequestParam(value = "searchQuery", required = false) String searchQuery, Model model) {
        List<TitleData> titles;
        if (searchQuery != null && !searchQuery.isEmpty()) {
            titles = titleDataRepository.findByNameTitleContainingIgnoreCase(searchQuery);
        } else {
            titles = titleDataRepository.findAll();
        }
        model.addAttribute("titles", titles);
        model.addAttribute("searchQuery", searchQuery);
        return "admin/manage_titles"; // HTML сторінка для перегляду/редагування коміксів
    }


    @GetMapping("/titles/add")
    public String addTitleForm(Model model) {
        model.addAttribute("title", new TitleData());
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("status", statusRepository.findAll());
        model.addAttribute("artists", artistRepository.findAll());
        model.addAttribute("screenwriters", screenwriterRepository.findAll());
        model.addAttribute("translates", translateRepository.findAll());
        return "admin/add_title"; // Форма для додавання нового коміксу
    }

    @PostMapping("/titles/add")
    public String addTitle(@ModelAttribute TitleData titleData,
                           @RequestParam("image") MultipartFile image,
                           @RequestParam("artists") Set<Long> artistIds,
                           @RequestParam(name = "screenwriters",required = false) Set<Long> screenwriterIds,
                           @RequestParam("translates") Set<Long> translateIds) {
        // Перевірка і створення каталогу для зображень, якщо він не існує
        String uploadDir = "uploads/images"; // Папка для зображень в корені проєкту
        Path uploadPath = Paths.get(uploadDir);

        // Якщо каталог не існує, створюємо його
        if (!Files.exists(uploadPath)) {
            try {
                Files.createDirectories(uploadPath); // Створюємо каталог
            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/titles/add?error";  // Якщо сталася помилка при створенні каталогу
            }
        }

        // Перевірка, чи файл не порожній
        if (!image.isEmpty()) {
            try {
                // Генерація унікального імені файлу за допомогою UUID
                String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFileName = UUID.randomUUID().toString() + extension;

                // Генерація шляху для збереження файлу
                Path filePath = uploadPath.resolve(uniqueFileName); // Збереження файлу в каталозі

                // Запис файлу
                Files.write(filePath, image.getBytes());

                // Збереження шляху до файлу в базі даних
                titleData.setImageTitle("/uploads/images/" + uniqueFileName);  // Відносний шлях до файлу

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/titles/add?error";  // Якщо сталася помилка при записі файлу
            }
        }
        // Отримуємо колекції авторів, сценаристів та перекладачів за їх ідентифікаторами
        Set<Artist> artists = new HashSet<>(artistRepository.findAllByIdArtistIn(artistIds));
        Set<Screenwriter> screenwriters = new HashSet<>(screenwriterRepository.findAllByIdScreenwriterIn(screenwriterIds));
        Set<Translate> translates = new HashSet<>(translateRepository.findAllByIdTranslateIn(translateIds));

        // Застосовуємо ці колекції до об'єкта titleData
        titleData.setArtists(artists);
        if (screenwriters!=null && screenwriters.size()>0) {
            titleData.setScreenwriters(screenwriters);

        }
        titleData.setTranslates(translates);

        // Збереження поточної дати
        titleData.setDateReleaseTitle(LocalDate.now());

        // Збереження даних у базу даних
        titleDataRepository.save(titleData);

        return "redirect:/admin/titles";  // Переадресація на список коміксів
    }

    @GetMapping("/titles/edit/{id}")
    public String editTitleForm(@PathVariable Long id, Model model) {
        TitleData title = titleDataRepository.findById(id).orElseThrow(() -> new RuntimeException("Title not found"));
        model.addAttribute("title", title);
        model.addAttribute("genres", genreRepository.findAll());
        model.addAttribute("status", statusRepository.findAll());
        model.addAttribute("artists", artistRepository.findAll());
        model.addAttribute("screenwriters", screenwriterRepository.findAll());
        model.addAttribute("translates", translateRepository.findAll());
        return "admin/edit_title"; // Форма для редагування коміксу
    }

    @PostMapping("/titles/edit/{id}")
    public String editTitle(@PathVariable Long id,
                            @ModelAttribute TitleData titleData,
                            @RequestParam("image") MultipartFile image,
                            @RequestParam("artists") Set<Long> artistIds,
                            @RequestParam(value = "screenwriters",required = false) Set<Long> screenwriterIds,
                            @RequestParam("translates") Set<Long> translateIds) {
        // Знайдемо існуючий запис
        TitleData existingTitle = titleDataRepository.findById(id).orElseThrow(() -> new RuntimeException("Title not found"));
        // Оновлюємо зв'язки
        Set<Artist> artists = new HashSet<>(artistRepository.findAllByIdArtistIn(artistIds));
        Set<Screenwriter> screenwriters = new HashSet<>(screenwriterRepository.findAllByIdScreenwriterIn(screenwriterIds));
        Set<Translate> translates = new HashSet<>(translateRepository.findAllByIdTranslateIn(translateIds));

        existingTitle.setArtists(artists);
        existingTitle.setScreenwriters(screenwriters);
        existingTitle.setTranslates(translates);

        // Оновимо текстові поля
        existingTitle.setNameTitle(titleData.getNameTitle());
        existingTitle.setDescriptionTitle(titleData.getDescriptionTitle());
        existingTitle.setStatus(titleData.getStatus());
        existingTitle.setGenres(titleData.getGenres());

        // Якщо надійшло нове зображення, збережемо його
        if (!image.isEmpty()) {
            try {
                // Генерація унікального імені файлу
                String originalFilename = StringUtils.cleanPath(image.getOriginalFilename());
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String uniqueFileName = UUID.randomUUID().toString() + extension;

                // Визначення шляху для збереження зображення
                String uploadDir = "uploads/images"; // Місце для збереження файлів
                Path uploadPath = Paths.get(uploadDir);

                // Перевіряємо наявність каталогу
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath); // Створюємо каталог, якщо не існує
                }

                // Збереження зображення
                Path filePath = uploadPath.resolve(uniqueFileName);
                Files.write(filePath, image.getBytes());

                // Оновлення шляху до зображення
                existingTitle.setImageTitle("/uploads/images/" + uniqueFileName);

            } catch (IOException e) {
                e.printStackTrace();
                return "redirect:/admin/titles/edit/" + id + "?error"; // Якщо сталася помилка
            }
        }

        // Збереження оновлених даних у базу
        titleDataRepository.save(existingTitle);

        return "redirect:/admin/titles"; // Переадресація на список
    }


    @PostMapping("/titles/delete/{id}")
    public String deleteTitle(@PathVariable Long id) {
        titleDataRepository.deleteById(id);
        return "redirect:/admin/titles";
    }

}
