import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numRoads = scanner.nextInt();
        int bestRoad = 0;
        int maxMinHeight = 0;

        for (int i = 0; i < numRoads; i++) {
            int numTunnels = scanner.nextInt();
            int minHeight = Integer.MAX_VALUE;

            for (int j = 0; j < numTunnels; j++) {
                int height = scanner.nextInt();
                if (height < minHeight) {
                    minHeight = height;
                }
            }

            if (minHeight > maxMinHeight) {
                maxMinHeight = minHeight;
                bestRoad = i + 1; // Нумерация дорог с 1
            }
        }
        System.out.println(bestRoad + " " + maxMinHeight);
    }
}