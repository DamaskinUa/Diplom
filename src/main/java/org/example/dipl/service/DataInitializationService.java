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
    /*private final UserService userService;

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
        statusActive.setNameStatus("Випускається");
        statusDataRepository.save(statusActive);

        StatusData statusCompleted = new StatusData();
        statusCompleted.setNameStatus("Завершено");
        statusDataRepository.save(statusCompleted);
        StatusData statusTransleite = new StatusData();
        statusTransleite.setNameStatus("Перекладається");
        statusDataRepository.save(statusCompleted);

        // Створення жанрів
        Genre genre1 = new Genre();
        genre1.setNameGenre("Фентезі");
        genreRepository.save(genre1);

        Genre genre2 = new Genre();
        genre2.setNameGenre("Пригоди");
        genreRepository.save(genre2);

        Genre genre3 = new Genre();
        genre3.setNameGenre("Детектив");
        genreRepository.save(genre3);

        Genre genre4 = new Genre();
        genre4.setNameGenre("Романтика");
        genreRepository.save(genre4);

        Genre genre5 = new Genre();
        genre5.setNameGenre("Жахи");
        genreRepository.save(genre5);

        Genre genre6 = new Genre();
        genre6.setNameGenre("Наукова фантастика");
        genreRepository.save(genre6);

        Genre genre7 = new Genre();
        genre7.setNameGenre("Містика");
        genreRepository.save(genre7);

        Genre genre8 = new Genre();
        genre8.setNameGenre("Історичний");
        genreRepository.save(genre8);

        Genre genre9 = new Genre();
        genre9.setNameGenre("Комедія");
        genreRepository.save(genre9);

        Genre genre10 = new Genre();
        genre10.setNameGenre("Біографія");
        genreRepository.save(genre10);

        // Створення перекладачів
        Translate translate1 = new Translate();
        translate1.setNameTranslate("Манґусти");
        translate1.setDescriptionTranslate("Команда ентузіастів, які люблять манґу та хочуть зробити її доступною на українській мові.");
        translate1.setDataRegistr(LocalDate.now());
        translateRepository.save(translate1);
        Translate translate2 = new Translate();
        translate2.setNameTranslate("OPUC [One Piece Ukrainian Community]");
        translate2.setDescriptionTranslate("Команда ентузіастів, які люблять манґу та хочуть зробити її доступною на українській мові.");
        translate2.setDataRegistr(LocalDate.now());
        translateRepository.save(translate2);

        // Створення художників
        Artist artist1 = new Artist();
        artist1.setNameArtist("Ода Еїтіро");
        artist1.setDescriptioneArtist("Японський художник манґи та творець серіалу One Piece (1997–тепер). З більш ніж 516,5 мільйонами копій танкобон, One Piece є одночасно найбільш продаваною манґою в історії та найбільш продаваною серією коміксів, надрукованою багаторазово, що, у свою чергу, робить Оду одним із авторів бестселерів художньої літератури[5]. Популярність серіалу призвела до того, що Оду назвали одним із манґак, які змінили історію манґи.");
        artistRepository.save(artist1);
        Artist artist2 = new Artist();
        artist2.setNameArtist("Юсуке Мурата");
        artist2.setDescriptioneArtist("Як мангака Мурата дебютував у 1995 році, опублікувавши у спеціальному випуску журналу Weekly Shonen Jump видавництва Shueisha ваншот-мангу Partner, за яку він отримав нагороду Hop Step Award. У червні 1998 року у тому журналі Мурата опублікував мангу Samui Hanashi, отримавши її премію Акацуки. Найбільшу популярність йому принесла манга Eyeshield 21, випущена у співпраці з письменником Ріітіро Інагакі, яка друкувалася в журналі Weekly Shonen Jump з липня 2002 по червень 2009, а пізніше за нею був випущений аніме-серіал. Також Мурат проілюстрував мангу One-Punch Man автора One, яка публікується в онлайн-версії журналу Weekly Young Jump з червня 2012 року.");
        artistRepository.save(artist2);

        // Створення сценаристів
        Screenwriter screenwriter1 = new Screenwriter();
        screenwriter1.setNameScreenwriter("ONE");
        screenwriter1.setDescriptionScreenwriter("ONE — псевдонім японського мангаки, який став відомим через перемальовану версію своєї веб-манги «Ванпанчмен» від Юсуке Мурати[2][3]. ONE публікує \"Ванпанчмена\" на своєму власному веб-сайті без офіційного видавця та \"Моб Псіхо 100\" в онлайн-версії Weekly Shonen Sunday[4].Станом на грудень 2015 року його веб-сайт має понад 100 000 відвідувань на день, а загальна кількість відвідувань – понад 70 мільйонів. ONE народився в префектурі Ніігата і ріс у Коносу, Сайтам.");
        screenwriterRepository.save(screenwriter1);

        // Створення Title
        TitleData title1 = new TitleData();
        title1.setNameTitle("One Piece");
        title1.setImageTitle("/images/onetitle.webp");
        title1.setDescriptionTitle("Останні слова, сказані Королем Піратів Голд Роджером перед стратою, надихнули багатьох вийти в море: \"Мої скарби? Я заповідаю їх тому, хто зможе їх знайти. Шукайте! Я все залишив в одному місці!\". Позбавлені сну і спокою люди кинулися на Гранд Лайн, найнебезпечніше місце у світі. Так почалася велика ера піратів... Але з кожним роком романтиків ставало дедалі менше, їх поступово витісняли прагматичні пірати-розбійники, яким награбоване добро було набагато ближчим, ніж якісь \"нікчемні мрії\". Але ось, одного прекрасного дня, сімнадцятирічний Монкі Д. Луффі здійснив заповітну мрію дитинства - вирушив у море. Його мета - ні багато, ні мало стати новим Королем Піратів. За досить короткий термін юному капітану вдається зібрати команду, що складається з не менш амбітних шукачів пригод. І нехай ними рухають абсолютно різні устремління, головне, цим хлопцям важливі не стільки гроші і слава, скільки набагато цінніше - принципи і вірність друзям. І ще - служіння Мрії. Що ж, поки по Гранд Лайн плавають такі люди, Велика Ера Піратів завжди буде з нами!");
        title1.setDateReleaseTitle(LocalDate.of(2020, 5, 1));
        title1.setStatus(statusActive);
        title1.getGenres().add(genre1);
        title1.getGenres().add(genre2);
        title1.getArtists().add(artist1);
        title1.getTranslates().add(translate2);
        titleDataRepository.save(title1);

        TitleData title2 = new TitleData();
        title2.setNameTitle("One Punch-Man");
        title2.setImageTitle("/images/OnePunch-Man.webp");
        title2.setDescriptionTitle("Сайтама – супергерой, який виглядає як найпосередніша посередність. Ні вражаючих м’язів, ні крутого костюму – просто якийсь голомозий пацан. Та насправді він такий могутній, що йому вже аж нудно вершити свої супергеройські діла. Адже вистачає лише удару, щоб розправитися із будь-яким злодієм. Та все ж він не полишає надії, що нарешті знайдеться ворог, достойний справжньої битви!");
        title2.setDateReleaseTitle(LocalDate.of(2021, 7, 15));
        title2.setStatus(statusCompleted);
        title2.getGenres().add(genre2);
        title2.getArtists().add(artist2);
        title2.getScreenwriters().add(screenwriter1);
        title2.getTranslates().add(translate1);
        titleDataRepository.save(title2);

        // Створення глав для коміксів
        Chapter chapter1 = new Chapter();
        chapter1.setNameChapter("Частина 1080: Перша частина");
        chapter1.setNumberChapter(1080);
        chapter1.setDateReleaseChapter(LocalDate.of(2020, 6, 1));
        chapter1.setTitle(title1); // Прив'язуємо главу до коміксу
        chapterRepository.save(chapter1);

        Chapter chapter2 = new Chapter();
        chapter2.setNameChapter("Частина 1080: Друга частина");
        chapter2.setNumberChapter(1081);
        chapter2.setDateReleaseChapter(LocalDate.of(2020, 6, 15));
        chapter2.setTitle(title1); // Прив'язуємо главу до коміксу
        chapterRepository.save(chapter2);

        Chapter chapter3 = new Chapter();
        chapter3.setNameChapter("Частина перша - Початок");
        chapter3.setNumberChapter(1);
        chapter3.setDateReleaseChapter(LocalDate.of(2021, 8, 1));
        chapter3.setTitle(title2); // Прив'язуємо главу до фільму
        chapterRepository.save(chapter3);

        // Створення сторінок для глав
        Page page1_1 = new Page();
        page1_1.setNumberPage("1");
        page1_1.setImagePage("/images/1080-1.webp");
        page1_1.setChapter(chapter1); // Прив'язуємо сторінку до глави
        pageRepository.save(page1_1);

        Page page1_2 = new Page();
        page1_2.setNumberPage("2");
        page1_2.setImagePage("/images/1080-2.webp");
        page1_2.setChapter(chapter1); // Прив'язуємо сторінку до глави
        pageRepository.save(page1_2);

        Page page2_1 = new Page();
        page2_1.setNumberPage("1");
        page2_1.setImagePage("/images/1080-3.webp");
        page2_1.setChapter(chapter2); // Прив'язуємо сторінку до глави
        pageRepository.save(page2_1);

        Page page2_2 = new Page();
        page2_2.setNumberPage("2");
        page2_2.setImagePage("/images/1080-4.webp");
        page2_2.setChapter(chapter2); // Прив'язуємо сторінку до глави
        pageRepository.save(page2_2);

        Page page3_1 = new Page();
        page3_1.setNumberPage("1");
        page3_1.setImagePage("/images/1-1.webp");
        page3_1.setChapter(chapter3); // Прив'язуємо сторінку до глави
        pageRepository.save(page3_1);

        Page page3_2 = new Page();
        page3_2.setNumberPage("2");
        page3_2.setImagePage("/images/1-2.webp");
        page3_2.setChapter(chapter3); // Прив'язуємо сторінку до глави
        pageRepository.save(page3_2);
    }*/
}

