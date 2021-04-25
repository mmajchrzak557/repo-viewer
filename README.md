# repo-viewer

## Założenia projektowe</h2>

* Na głównym ekranie:
  - nazwa
  - opis
  - język
  - liczba gwiazdek
  - liczba forków
  
* Na ekranie ze szczegółami repozytorium:
  - informacje z ekranu głównego
  - data utworzenia i ostatniej aktualizacji
  - URL repozytorium
  - właściciel
  - wykres aktywności (ilość commitów w ciągu roku)
  - README


## Wykorzystane technologie i narzędzia

* Kotlin
* Retrofit
* Github API
* MarkdownView - biblioteka do wyświetlania plików README z zachowaniem oryginalnego formatowania (https://github.com/tiagohm/MarkdownView)
* Google Material Icons

## Przyjęte uproszczenia

* liczba repozytoriów nie przekracza 100 - wynika to z ograniczeń API (https://docs.github.com/en/rest/guides/traversing-with-pagination)
* na ekranie głównym ikony wyświetlane obok języka mają losowo wybrany kolor
* wykresy aktywności są skalowane wg największej liczby commitów dla danego repozytorium

## Kolejne kroki

* przypisanie językom stałych kolorów
* dopasowanie koloru MarkdownView do koloru tła
* możliwość przeglądania repozytoriów wszystkich użytkowników
* wyświetlanie kontrybutorów 
* wyświetlanie awatarów użytkowników
* sortowanie listy repozytoriów ze względu na liczbę gwiazdek i daty ostatniej aktualizacji
