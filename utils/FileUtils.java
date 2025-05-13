package utils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.DateTimeException;
import java.util.Scanner;

public class FileUtils {

    public static boolean zberehtyLogika(String shlyakhDoFaylu, int kilkistZapysivParam, String formatDatyParam, LocalDateTime[] datyParam, String[] tekstyParam) {
        try {
            FileWriter fileWriter = new FileWriter(shlyakhDoFaylu);

            fileWriter.write(kilkistZapysivParam + "\n");
            fileWriter.write(formatDatyParam + "\n");

            for (int i = 0; i < kilkistZapysivParam; i++) {
                fileWriter.write(datyParam[i].toString() + "\n");

                String tekstZaminenyi = "";
                for (int j = 0; j < tekstyParam[i].length(); j++) {
                    char symvol = tekstyParam[i].charAt(j);
                    if (symvol == '\n') {
                        tekstZaminenyi = tekstZaminenyi + "\\n";
                    } else {
                        tekstZaminenyi = tekstZaminenyi + symvol;
                    }
                }
                fileWriter.write(tekstZaminenyi + "\n");
            }

            fileWriter.close();
            return true;
        } catch (IOException e) {
            System.out.println("\nПомилка збереження файлу: " + e.getMessage());
            return false;
        }
    }

    public static Object[] vidnovytyLogika(String shlyakhDoFaylu, int maxZapysivParam) {
        try {
            File file = new File(shlyakhDoFaylu);

            if (!file.exists()) {
                System.out.println("\nФайл не знайдено (╬▔皿▔)╯!");
                return null;
            }

            FileReader fileReader = new FileReader(file);
            Scanner fileSkan = new Scanner(fileReader);

            if (!fileSkan.hasNextLine()) {
                System.out.println("\nФайл порожній (╬▔皿▔)╯!");
                fileSkan.close();
                return null;
            }

            int kilkist = Integer.parseInt(fileSkan.nextLine());
            String formatDatyLoaded = fileSkan.nextLine();

            LocalDateTime[] datyLoaded = new LocalDateTime[maxZapysivParam];
            String[] tekstyLoaded = new String[maxZapysivParam];
            int kilkistZapysivLoaded = 0;

            for (int i = 0; i < kilkist; i++) {
                if (!fileSkan.hasNextLine()) break;

                String dataStr = fileSkan.nextLine();
                LocalDateTime data = LocalDateTime.parse(dataStr);

                if (!fileSkan.hasNextLine()) break;

                String tekst = fileSkan.nextLine();
                String tekstVidnovlenyi = "";

                for (int j = 0; j < tekst.length(); j++) {
                    if (j < tekst.length() - 1 && tekst.charAt(j) == '\\' && tekst.charAt(j + 1) == 'n') {
                        tekstVidnovlenyi = tekstVidnovlenyi + "\n";
                        j++;
                    } else {
                        tekstVidnovlenyi = tekstVidnovlenyi + tekst.charAt(j);
                    }
                }

                datyLoaded[kilkistZapysivLoaded] = data;
                tekstyLoaded[kilkistZapysivLoaded] = tekstVidnovlenyi;
                kilkistZapysivLoaded++;
            }

            fileSkan.close();
            return new Object[]{kilkistZapysivLoaded, formatDatyLoaded, datyLoaded, tekstyLoaded};

        } catch (IOException e) {
            System.out.println("\nПомилка читання файлу: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("\nПомилка формату файлу: неправильний формат числа");
        } catch (DateTimeException e) {
            System.out.println("\nПомилка формату дати у файлі");
        } catch (Exception e) {
            System.out.println("\nНеочікувана помилка: " + e.getMessage());
        }
        return null;
    }
}