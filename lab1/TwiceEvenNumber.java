import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number = scanner.nextInt();
        int hundreds = number / 100; 
        int tens = (number / 10) % 10; 
        int ones = number % 10; 
        
        int sum = hundreds + tens + ones;
        int product = hundreds * tens * ones;
        
        if (sum % 2 == 0 && product % 2 == 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}
