import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner skan = new Scanner(System.in);
        boolean pratsyuye = true;

        System.out.println("( ͡°͜ʖ͡°) Мій щоденник запущено ( ͡°͜ʖ͡°)");

        System.out.println("\nВи хочете відновити існуючий щоденник?");
        System.out.println("1. Так, відновити з файлу");
        System.out.println("2. Ні, створити новий");

        int vybir = 0;
        while (vybir < 1 || vybir > 2) {
            try {
                System.out.print("Ваш вибір: ");
                vybir = Integer.parseInt(skan.nextLine());
                if (vybir < 1 || vybir > 2) {
                    System.out.println("Введіть 1 або 2!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введіть коректне число!");
            }
        }

        if (vybir == 1) {
            System.out.print("Введіть шлях до файлу: ");
            String shlyakhDoFaylu = skan.nextLine();
            MyDiary.vidnovytyZFaylu(shlyakhDoFaylu);
        }

        MyDiary.vybratyFormatDaty(skan);

        while (pratsyuye) {
            pokazatyHolovneMenu();

            int vybirMenu = 0;

            try {
                System.out.print("Ваш вибір: ");
                String vybirStr = skan.nextLine();
                vybirMenu = Integer.parseInt(vybirStr);
            } catch (NumberFormatException e) {
                System.out.println("\nНевірний вибір (╬▔皿▔)╯! Спробуйте ще раз, але вже число.");
                continue;
            }

            switch (vybirMenu) {
                case 1:
                    MyDiary.dodatyZapys(skan);
                    break;

                case 2:
                    MyDiary.vydalytyZapys(skan);
                    break;

                case 3:
                    MyDiary.pokazatyVsiZapysy();
                    break;

                case 4:
                    MyDiary.zberehtyShchodennyk(skan);
                    break;

                case 5:
                    if (!MyDiary.areZminyZberezheni()) {
                        System.out.println("\nУ вас є незбережені зміни. Зберегти зміни перед виходом?");
                        System.out.println("1. Так");
                        System.out.println("2. Ні");

                        int vybirZberezhennya = 0;
                        while (vybirZberezhennya < 1 || vybirZberezhennya > 2) {
                            try {
                                System.out.print("Ваш вибір: ");
                                vybirZberezhennya = Integer.parseInt(skan.nextLine());
                                if (vybirZberezhennya < 1 || vybirZberezhennya > 2) {
                                    System.out.println("Введіть 1 або 2!");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Введіть коректне число!");
                            }
                        }

                        if (vybirZberezhennya == 1) {
                            MyDiary.zberehtyShchodennyk(skan);
                        }
                    }

                    System.out.println("\n✓ До побачення! Щоденник закрито.");
                    pratsyuye = false;
                    break;

                default:
                    System.out.println("\nНевірний вибір (╬▔皿▔)╯! Спробуйте ще раз.");
            }
        }
        skan.close();
    }

    private static void pokazatyHolovneMenu() {
        System.out.println("\n┌───────────────────────────────────────┐");
        System.out.println("│ МІЙ ЩОДЕННИК   つ◕_◕ ༽つ               │");
        System.out.println("├───────────────────────────────────────┤");
        System.out.println("│ 1. Додати запис                       │");
        System.out.println("│ 2. Видалити запис                     │");
        System.out.println("│ 3. Переглянути всі записи             │");
        System.out.println("│ 4. Зберегти щоденник у файл           │");
        System.out.println("│ 5. Вийти з програми                   │");
        System.out.println("└───────────────────────────────────────┘");
    }
}