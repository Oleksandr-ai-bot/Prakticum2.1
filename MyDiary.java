import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import utils.FileUtils;

public class MyDiary {
    private static final int maxZapysiv = 50;

    private static LocalDateTime[] daty = new LocalDateTime[maxZapysiv];
    private static String[] teksty = new String[maxZapysiv];
    private static int kilkistZapysiv = 0;
    private static String formatDaty = "yyyy-MM-dd HH:mm:ss";
    private static boolean zminyZberezheni = true;

    public static boolean areZminyZberezheni() {
        return zminyZberezheni;
    }

    public static void dodatyZapys(Scanner skan) {
        if (kilkistZapysiv >= maxZapysiv) {
            System.out.println("\nЩоденник повний (╬▔皿▔)╯! Максимальна кількість записів: " + maxZapysiv);
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ДОДАВАННЯ НОВОГО ЗАПИСУ              │");
        System.out.println("└────────────────────────────────────┘");

        LocalDateTime data = null;

        while (data == null) {
            try {
                System.out.println("Введіть дату для запису (рік, місяць, день):");
                System.out.print("Рік: ");
                int rik = Integer.parseInt(skan.nextLine());
                System.out.print("Місяць: ");
                int misyats = Integer.parseInt(skan.nextLine());
                System.out.print("День: ");
                int den = Integer.parseInt(skan.nextLine());

                System.out.println("Додати час? (1 - так, 2 - ні)");
                int dodatyChas = 0;
                while (dodatyChas < 1 || dodatyChas > 2) {
                    try {
                        System.out.print("Ваш вибір: ");
                        dodatyChas = Integer.parseInt(skan.nextLine());
                        if (dodatyChas < 1 || dodatyChas > 2) {
                            System.out.println("Введіть 1 або 2!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Введіть коректне число!");
                    }
                }

                if (dodatyChas == 1) {
                    System.out.print("Година: ");
                    int hodyna = Integer.parseInt(skan.nextLine());
                    System.out.print("Хвилина: ");
                    int khvylyna = Integer.parseInt(skan.nextLine());
                    data = LocalDateTime.of(rik, misyats, den, hodyna, khvylyna);
                } else {
                    data = LocalDateTime.of(rik, misyats, den, 0, 0);
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка введення! Введіть ціле число.");
            } catch (DateTimeException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        for (int i = 0; i < kilkistZapysiv; i++) {
            if (daty[i] != null) {
                if (daty[i].equals(data)) {
                    System.out.println("Запис з такою датою вже існує (╬▔皿▔)╯! Спробуйте іншу дату.");
                    return;
                }
            }
        }

        System.out.println("\nВведіть текст запису. Для завершення введіть '~'.");
        System.out.println("Можна вводити декілька рядків, кожен з нових рядків.");

        String tekst = "";
        String ryadok;

        while (true) {
            ryadok = skan.nextLine();
            if (ryadok.equals("~")) {
                break;
            }

            if (!tekst.isEmpty()) {
                tekst = tekst + "\n" + ryadok;
            } else {
                tekst = ryadok;
            }
        }

        if (tekst.isEmpty()) {
            System.out.println("Запис не може бути порожнім (╬▔皿▔)╯!");
            return;
        }

        daty[kilkistZapysiv] = data;
        teksty[kilkistZapysiv] = tekst;
        kilkistZapysiv++;
        zminyZberezheni = false;

        System.out.println("\n✓ Запис успішно додано до щоденника!");
    }

    public static void vydalytyZapys(Scanner skan) {
        if (kilkistZapysiv == 0) {
            System.out.println("\nЩоденник порожній (╬▔皿▔)╯!");
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ВИДАЛЕННЯ ЗАПИСУ                     │");
        System.out.println("└────────────────────────────────────┘");

        LocalDateTime data = null;

        while (data == null) {
            try {
                System.out.println("Введіть дату запису для видалення (рік, місяць, день):");
                System.out.print("Рік: ");
                int rik = Integer.parseInt(skan.nextLine());
                System.out.print("Місяць: ");
                int misyats = Integer.parseInt(skan.nextLine());
                System.out.print("День: ");
                int den = Integer.parseInt(skan.nextLine());

                data = LocalDateTime.of(rik, misyats, den, 0, 0);
            } catch (NumberFormatException e) {
                System.out.println("Помилка введення! Введіть ціле число.");
            } catch (DateTimeException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }

        int indexZapysu = -1;

        for (int i = 0; i < kilkistZapysiv; i++) {
            if (daty[i] != null) {
                if (daty[i].toLocalDate().equals(data.toLocalDate())) {
                    indexZapysu = i;
                    break;
                }
            }
        }

        if (indexZapysu == -1) {
            System.out.println("Запис з такою датою не знайдено (╬▔皿▔)╯!");
            return;
        }

        for (int i = indexZapysu; i < kilkistZapysiv - 1; i++) {
            daty[i] = daty[i + 1];
            teksty[i] = teksty[i + 1];
        }

        daty[kilkistZapysiv - 1] = null;
        teksty[kilkistZapysiv - 1] = null;
        kilkistZapysiv--;
        zminyZberezheni = false;

        System.out.println("\n✓ Запис успішно видалено!");
    }

    public static void pokazatyVsiZapysy() {
        if (kilkistZapysiv == 0) {
            System.out.println("\nЩоденник порожній (╬▔皿▔)╯!");
            return;
        }

        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ПЕРЕЛІК ВСІХ ЗАПИСІВ                 │");
        System.out.println("└────────────────────────────────────┘");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formatDaty);

        for (int i = 0; i < kilkistZapysiv; i++) {
            String dataSformatovana = daty[i].format(formatter);
            System.out.println("\n[Запис #" + (i + 1) + ", Дата: " + dataSformatovana + "]");
            System.out.println("────────────────────────────────────");
            System.out.println(teksty[i]);
            System.out.println("────────────────────────────────────");
        }

        System.out.println("\n✓ Загальна кількість записів: " + kilkistZapysiv);
    }

    public static void zberehtyShchodennyk(Scanner skan) {
        if (kilkistZapysiv == 0) {
            System.out.println("\nЩоденник порожній (╬▔皿▔)╯! Немає що зберігати.");
            return;
        }

        System.out.print("\nВведіть шлях до файлу для збереження: ");
        String shlyakhDoFaylu = skan.nextLine();

        boolean success = FileUtils.zberehtyLogika(shlyakhDoFaylu, kilkistZapysiv, formatDaty, daty, teksty);
        if (success) {
            zminyZberezheni = true;
            System.out.println("\n✓ Щоденник успішно збережено у файл!");
        }
    }

    public static void vidnovytyZFaylu(String shlyakhDoFaylu) {
        Object[] loadedData = FileUtils.vidnovytyLogika(shlyakhDoFaylu, maxZapysiv);
        if (loadedData != null) {
            MyDiary.kilkistZapysiv = (int) loadedData[0];
            MyDiary.formatDaty = (String) loadedData[1];
            MyDiary.daty = (LocalDateTime[]) loadedData[2];
            MyDiary.teksty = (String[]) loadedData[3];
            MyDiary.zminyZberezheni = true;
            System.out.println("\n✓ Щоденник успішно відновлено з файлу!");
        }
    }

    public static void vybratyFormatDaty(Scanner skan) {
        System.out.println("\n┌────────────────────────────────────┐");
        System.out.println("│ВИБІР ФОРМАТУ ДАТИ                   │");
        System.out.println("└────────────────────────────────────┘");

        System.out.println("Виберіть формат відображення дати:");
        System.out.println("1. ISO 8601 (yyyy-MM-dd HH:mm:ss)");
        System.out.println("2. Східноєвропейський (dd.MM.yyyy HH:mm)");
        System.out.println("3. Формат США (MM/dd/yyyy h:mm a)");
        System.out.println("4. Формат Великобританії (dd MMMM yyyy HH:mm)");
        System.out.println("5. Власний формат");

        int vybir = 0;
        while (vybir < 1 || vybir > 5) {
            try {
                System.out.print("Ваш вибір: ");
                vybir = Integer.parseInt(skan.nextLine());
                if (vybir < 1 || vybir > 5) {
                    System.out.println("Введіть число від 1 до 5!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне число!");
            }
        }

        switch (vybir) {
            case 1:
                formatDaty = "yyyy-MM-dd HH:mm:ss";
                break;
            case 2:
                formatDaty = "dd.MM.yyyy HH:mm";
                break;
            case 3:
                formatDaty = "MM/dd/yyyy h:mm a";
                break;
            case 4:
                formatDaty = "dd MMMM yyyy HH:mm";
                break;
            case 5:
                System.out.println("\nВведіть власний формат дати (використовуйте символи y, M, d, H, m, s):");
                System.out.println("Наприклад: dd-MM-yyyy HH:mm:ss");

                boolean formatPravylnyi = false;

                while (!formatPravylnyi) {
                    try {
                        String formatKorystuvacha = skan.nextLine();
                        DateTimeFormatter.ofPattern(formatKorystuvacha); // Test format
                        formatDaty = formatKorystuvacha;
                        formatPravylnyi = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Неправильний формат! Спробуйте ще раз.");
                    }
                }
                break;
        }
        zminyZberezheni = false; // Зміна формату - це зміна, яку варто зберегти
        System.out.println("\n✓ Формат дати встановлено!");
    }
}