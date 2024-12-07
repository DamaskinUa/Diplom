package org.example.dipl.service;

import org.example.dipl.model.*;
import org.example.dipl.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.annotation.PostConstruct;

import java.time.LocalDate;

@Service
public class DataInitializationService {
   /* private final UserService userService;

    // Inject UserService via constructor
    @Autowired
    public DataInitializationService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TitleDataRepository titleDataRepository;


    @Autowired
    private RoleUserRepository roleUserRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ScreenwriterRepository screenwriterRepository;

    @Autowired
    private StatusDataRepository statusDataRepository;

    @Autowired
    private TranslateRepository translateRepository;
    @Autowired
    private ChapterRepository chapterRepository;
    @Autowired
    private PageRepository pageRepository;

    @Transactional
    @PostConstruct
    public void initData() {
        // Створення ролей
        RoleUser adminRole = new RoleUser();
        adminRole.setNameRole("ADMIN");
        roleUserRepository.save(adminRole);

        RoleUser userRole = new RoleUser();
        userRole.setNameRole("USER");
        roleUserRepository.save(userRole);

        // Створення тестових користувачів через UserService
        userService.registerUser("admin", "admin", "admin@example.com", adminRole);
        userService.registerUser("testUser1", "password1", "test1@example.com", userRole);
        User user3 = new User();
        user3.setLoginUser("testUser2");
        user3.setPasswordUser("password2");
        user3.setEmail("test2@example.com");  // Встановлюємо email
        user3.setDataRegistri(LocalDate.now());  // Встановлюємо дату реєстрації
        user3.setRole(adminRole);  // Встановлюємо роль
        userRepository.save(user3);
        User user4 = new User();
        user4.setLoginUser("testUser3");
        user4.setPasswordUser("password3");
        user4.setEmail("test3@example.com");  // Встановлюємо email
        user4.setDataRegistri(LocalDate.now());  // Встановлюємо дату реєстрації
        user4.setRole(userRole);  // Встановлюємо роль
        userRepository.save(user4);


        // Створення статусів
        StatusData statusActive = new StatusData();
        statusActive.setNameStatus("Active");
        statusDataRepository.save(statusActive);

        StatusData statusCompleted = new StatusData();
        statusCompleted.setNameStatus("Completed");
        statusDataRepository.save(statusCompleted);

        // Створення жанрів
        Genre genre1 = new Genre();
        genre1.setNameGenre("Action");
        genreRepository.save(genre1);

        Genre genre2 = new Genre();
        genre2.setNameGenre("Adventure");
        genreRepository.save(genre2);

        // Створення перекладачів
        Translate translate1 = new Translate();
        translate1.setNameTranslate("English");
        translate1.setDescriptionTranslate("Original English version");
        translate1.setDataRegistr(LocalDate.now());
        translateRepository.save(translate1);

        // Створення художників
        Artist artist1 = new Artist();
        artist1.setNameArtist("John Doe");
        artistRepository.save(artist1);

        // Створення сценаристів
        Screenwriter screenwriter1 = new Screenwriter();
        screenwriter1.setNameScreenwriter("Jane Smith");
        screenwriterRepository.save(screenwriter1);

        // Створення Title
        TitleData title1 = new TitleData();
        title1.setNameTitle("Amazing Comic");
        title1.setImageTitle("image.jpg");
        title1.setDescriptionTitle("An action-packed comic series");
        title1.setDateReleaseTitle(LocalDate.of(2020, 5, 1));
        title1.setStatus(statusActive);
        title1.getGenres().add(genre1);
        title1.getGenres().add(genre2);
        title1.getArtists().add(artist1);
        title1.getScreenwriters().add(screenwriter1);
        title1.getTranslates().add(translate1);
        titleDataRepository.save(title1);

        TitleData title2 = new TitleData();
        title2.setNameTitle("Fantastic Movie");
        title2.setImageTitle("movie.jpg");
        title2.setDescriptionTitle("A thrilling adventure movie");
        title2.setDateReleaseTitle(LocalDate.of(2021, 7, 15));
        title2.setStatus(statusCompleted);
        title2.getGenres().add(genre2);
        title2.getArtists().add(artist1);
        title2.getScreenwriters().add(screenwriter1);
        title2.getTranslates().add(translate1);
        titleDataRepository.save(title2);

        // Створення глав для коміксів
        Chapter chapter1 = new Chapter();
        chapter1.setNameChapter("Chapter 1: The Beginning");
        chapter1.setNumberChapter(1);
        chapter1.setDateReleaseChapter(LocalDate.of(2020, 6, 1));
        chapter1.setTitle(title1); // Прив'язуємо главу до коміксу
        chapterRepository.save(chapter1);

        Chapter chapter2 = new Chapter();
        chapter2.setNameChapter("Chapter 2: The Adventure Continues");
        chapter2.setNumberChapter(2);
        chapter2.setDateReleaseChapter(LocalDate.of(2020, 6, 15));
        chapter2.setTitle(title1); // Прив'язуємо главу до коміксу
        chapterRepository.save(chapter2);

        Chapter chapter3 = new Chapter();
        chapter3.setNameChapter("Chapter 1: The Thrill Begins");
        chapter3.setNumberChapter(1);
        chapter3.setDateReleaseChapter(LocalDate.of(2021, 8, 1));
        chapter3.setTitle(title2); // Прив'язуємо главу до фільму
        chapterRepository.save(chapter3);

        // Створення сторінок для глав
        Page page1_1 = new Page();
        page1_1.setNumberPage("1");
        page1_1.setImagePage("chapter1_page1.jpg");
        page1_1.setChapter(chapter1); // Прив'язуємо сторінку до глави
        pageRepository.save(page1_1);

        Page page1_2 = new Page();
        page1_2.setNumberPage("2");
        page1_2.setImagePage("chapter1_page2.jpg");
        page1_2.setChapter(chapter1); // Прив'язуємо сторінку до глави
        pageRepository.save(page1_2);

        Page page2_1 = new Page();
        page2_1.setNumberPage("1");
        page2_1.setImagePage("chapter2_page1.jpg");
        page2_1.setChapter(chapter2); // Прив'язуємо сторінку до глави
        pageRepository.save(page2_1);

        Page page2_2 = new Page();
        page2_2.setNumberPage("2");
        page2_2.setImagePage("chapter2_page2.jpg");
        page2_2.setChapter(chapter2); // Прив'язуємо сторінку до глави
        pageRepository.save(page2_2);

        Page page3_1 = new Page();
        page3_1.setNumberPage("1");
        page3_1.setImagePage("chapter3_page1.jpg");
        page3_1.setChapter(chapter3); // Прив'язуємо сторінку до глави
        pageRepository.save(page3_1);

        Page page3_2 = new Page();
        page3_2.setNumberPage("2");
        page3_2.setImagePage("chapter3_page2.jpg");
        page3_2.setChapter(chapter3); // Прив'язуємо сторінку до глави
        pageRepository.save(page3_2);
    }
*/
}

