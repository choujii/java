import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество чисел (n): ");
        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Ошибка: n должно быть больше 0.");
            return;
        }
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            System.out.print("Введите число: ");
            int number = scanner.nextInt();
            if (i % 2 == 1) {
                sum += number;
            } else {
                sum -= number;
            }
        }
        System.out.println("Знакочередующаяся сумма: " + sum);
    }
}