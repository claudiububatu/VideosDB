# Object Oriented Programming Course
# VideosDB
## Implementare
Am creat cate un
pachet pentru fiecare tip de actiune: commands, query.

- Pachetul commands contine metodele de input (create cu scopul de a le 
putea modifica) si cele 3 comenzi aferente unei comenzi: AddFavorite, View
si Rating. Cele 3 metode:
     1. addfavorite - verifica daca videoclipul primit ca parametru exista in 
    lista de videoclipuri favorite ale user-ului si in caz negativ il adauga
    daca user-ul a vazut video-ul.
     2. view - verifica daca user-ul a vazut videoclipul primit ca parametru; in
    caz pozitiv, incrementeaza numarul de vizionari iar in caz negativ il adauga
    il lista.
     3. rating contine doua metode: rateMovie/rateSerial - aceste metode primesc
    ca parametrii doua liste in care se salveaza fiecare rating dat pentru filme/
    seriale.

- Pachetul query contine clasele de query pentru actori, useri si video-uri:
QueryActors, QueryUsers si QueryVideos.
- In QueryVideos vom gasi urmatoarele metode:
      1. ratingMovie - sortam primele n filme dupa rating.
      2. ratingSerial - sortam primele n seriale dupa rating.
      3. longestmovie - sortam primele n filme dupa durata.
      4. longestserial - sortam primele n seriale dupa durata.
      5. mostviewedmovie - sortam primele n filme dupa numarul de vizualizari.
      6. mostviewedserial - sortam primele n filme dupa numarul de vizualizari.
      7. favoritemovie - sortam primele n filme dupa numarul de aparitii.
     in lista de video-uri favorite ale utilizatorilor.
      8. favoriteserial - sortam primele n seriale dupa numarul de aparitii
     in lista de video-uri favorite ale utilizatorilor.
  Toate metodele din QueryVideos returneaza un String ce reprezinta output-ul
dorit pentru fiecare program in parte.

-In QueryActors vom gasi urmatoarele metode:
      1. average - gasim primii n actori sortati dupa media ratingurilor
     filmelor si serialelor in care au jucat.
      2. awards - gasim primii n actori sortati dupa numarul de premii
     pe care il are fiecare.
      3. filterdescription - gasim actorii in descrierea carora apar
     toate keywords-urile.
  Toate metodele din QueryActors returneaza un String ce reprezinta output-ul
dorit pentru fiecare program in parte.

- In QueryUsers gasim metoda numofratings ce gaseste cei mai activi n
utilizatori.