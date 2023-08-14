import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;
import java.lang.*;
import java.io.*;

class WarehouseLocation {
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static BigDecimal euclideanDistance(Point p1, Point p2) {
        // Calculate Euclidean distance using BigDecimal for arbitrary precision
        BigDecimal xDiff = BigDecimal.valueOf(p1.x - p2.x);
        BigDecimal yDiff = BigDecimal.valueOf(p1.y - p2.y);
        return BigDecimal.valueOf(Math.sqrt(xDiff.multiply(xDiff).add(yDiff.multiply(yDiff)).doubleValue()));
    }

    static Point findGeometricMedian(List<Point> coordinates) {
        // Implement Weiszfeld's algorithm to find the geometric median
        // ...

        return point;
    }

    static BigDecimal calculateTotalDistance(List<Point> points) {
        // Calculate the total distance traveled by the truck
        BigDecimal totalDistance = BigDecimal.ZERO;
        for (int i = 0; i < points.size() - 1; i++) {
            totalDistance = totalDistance.add(euclideanDistance(points.get(i), points.get(i + 1)));
        }
        return totalDistance;
    }

    static Map<Point, Integer> calculateDistances(int[][] grid, Point warehouse, List<Point> noTrespassAreas) {
        int m = grid.length;
        Map<Point, Integer> distances = new HashMap<>();
        boolean[][] visited = new boolean[m][m];

        for (Point noTrespassArea : noTrespassAreas) {
            visited[noTrespassArea.x][noTrespassArea.y] = true;
        }

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        List<Point> queue = new ArrayList<>();
        queue.add(warehouse);
        distances.put(warehouse, 0);
        visited[warehouse.x][warehouse.y] = true;

        // BFS traversal to calculate distances to reachable points
        while (!queue.isEmpty()) {
            Point currPoint = queue.remove(0);
            int currDist = distances.get(currPoint);

            for (int i = 0; i < 4; i++) {
                int newX = currPoint.x + dx[i];
                int newY = currPoint.y + dy[i];

                if (newX >= 0 && newX < m && newY >= 0 && newY < m && !visited[newX][newY]) {
                    Point newPoint = new Point(newX, newY);
                    queue.add(newPoint);
                    distances.put(newPoint, currDist + 1);
                    visited[newX][newY] = true;
                }
            }
        }

        return distances;
    }

    static Point findOptimumWarehouseLocation(int[][] grid, List<Point> noTrespassAreas) {
        int m = grid.length;
        List<Point> warehouseCandidates = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (!noTrespassAreas.contains(new Point(i, j))) {
                    warehouseCandidates.add(new Point(i, j));
                }
            }
        }

        BigDecimal minTotalDistance = BigDecimal.valueOf(Double.POSITIVE_INFINITY);
        Point bestWarehouseLocation = null;

        for (Point warehouseCandidate : warehouseCandidates) {
            Map<Point, Integer> distances = calculateDistances(grid, warehouseCandidate, noTrespassAreas);
            BigDecimal totalDistance = BigDecimal.ZERO;
            for (int distance : distances.values()) {
                totalDistance = totalDistance.add(BigDecimal.valueOf(distance));
            }
            if (totalDistance.compareTo(minTotalDistance) < 0) {
                minTotalDistance = totalDistance;
                bestWarehouseLocation = warehouseCandidate;
            }
        }

        return bestWarehouseLocation;
    }

    public static void main(String[] args) {
        // Example usage for part a (strategic warehouse location)
        List<Point> factoriesCoordinates = new ArrayList<>();
        factoriesCoordinates.add(new Point(1, 2));
        factoriesCoordinates.add(new Point(3, 5));
        factoriesCoordinates.add(new Point(6, 9));
        factoriesCoordinates.add(new Point(8, 3));
        factoriesCoordinates.add(new Point(10, 12));
        Point warehouseLocation = findGeometricMedian(factoriesCoordinates);
        System.out.println("Strategic Warehouse Location: (" + warehouseLocation.x + ", " + warehouseLocation.y + ")");

        // Example usage for part b (optimum solution in grid setup)
        int m = 5;
        int[][] grid = new int[m][m];
        List<Point> noTrespassAreas = new ArrayList<>();
        noTrespassAreas.add(new Point(1, 2));
        noTrespassAreas.add(new Point(2, 2));
        noTrespassAreas.add(new Point(3, 2));
        noTrespassAreas.add(new Point(3, 3));
        Point optimumWarehouseLocation = findOptimumWarehouseLocation(grid, noTrespassAreas);
        System.out.println("Optimum Warehouse Location: (" + optimumWarehouseLocation.x + ", " + optimumWarehouseLocation.y + ")");
    }
}
