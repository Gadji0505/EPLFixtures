# EPL Fixtures — курсовой проект

Android-приложение на **Kotlin + Jetpack Compose**, которое показывает список матчей
английской Премьер-лиги и детали каждого матча.

## Как открыть проект

1. Установите **Android Studio** (Giraffe/Koala или новее).
2. `File → Open` → выберите папку `EPLFixtures`.
3. Дождитесь Gradle Sync (Android Studio сама подтянет `gradle-wrapper.jar`,
   если он отсутствует — при первом открытии среда предложит это сделать).
4. Запустите конфигурацию `app` на эмуляторе или устройстве (Run ▶).

## Что реализовано (соответствие ТЗ)

| Требование | Статус |
|---|---|
| Retrofit для сети | ✅ `data/remote/ApiService.kt`, `RetrofitClient.kt` |
| Kotlin Coroutines | ✅ `suspend fun`, `viewModelScope.launch`, `Dispatchers.IO` |
| Архитектура MVVM | ✅ `ui/viewmodel/FixtureViewModel.kt` + `StateFlow` |
| LiveData/Flow | ✅ `StateFlow` (`uiState`, `searchQuery`) |
| Single Activity | ✅ `MainActivity.kt` + Navigation Compose |
| Конвертация DateUtc → локальное время | ✅ `utils/DateUtils.kt` |
| Экран списка матчей | ✅ `ui/screens/FixtureListScreen.kt` |
| Экран деталей матча | ✅ `ui/screens/FixtureDetailScreen.kt` |
| Jetpack Compose (бонус) | ✅ весь UI на Compose |
| Кеширование (бонус) | ✅ `FixtureRepository` хранит результат в памяти |
| Локальный поиск по команде (бонус) | ✅ поле поиска на экране списка |
| Юнит-тесты (бонус) | ✅ `src/test/.../DateUtilsTest.kt`, `FixtureViewModelTest.kt` |
| Пагинация (бонус) | ⛔ не реализована — API отдаёт весь список одним ответом, реальной пагинации на сервере нет |

## Структура проекта

```
app/src/main/java/com/example/eplfixtures/
├── MainActivity.kt                  — Single Activity + NavHost
├── data/
│   ├── model/Fixture.kt             — модель данных API
│   ├── remote/ApiService.kt         — Retrofit-интерфейс
│   ├── remote/RetrofitClient.kt     — сборка Retrofit-клиента
│   └── repository/FixtureRepository.kt — источник данных + кеш
├── ui/
│   ├── viewmodel/FixtureViewModel.kt — состояние экрана (StateFlow)
│   ├── screens/FixtureListScreen.kt  — список матчей + поиск
│   ├── screens/FixtureDetailScreen.kt — детали матча
│   └── theme/                        — Material 3 тема
└── utils/DateUtils.kt                — конвертация UTC → локальное время
```

## API

`GET https://fixturedownload.com/feed/json/epl-2023` — публичный, без ключа.

## Тесты

Запустить: `./gradlew test` (или через Android Studio: правый клик на папке
`test` → Run Tests).
