//These are import statements to bring in necessary classes from the Java standard library (java.util package) that we will use in our code.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//This is  a class named WarehouseLocation, which will contain the methods for finding the strategic location of the warehouse and the optimum solution in the grid setup.
// Define a nested class Point to represent the cartesian coordinates of factories, warehouses, and no-trespass areas. It has two fields, x and y, representing the coordinates in the plane.
class WarehouseLocation {
    static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

  //This method calculates the Euclidean distance between two points p1 and p2 using the standard formula for distance between two points in a 2D plane.
  
    static double euclideanDistance(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

  //This method finds the geometric median of a list of Point objects representing the cartesian coordinates of factories. The geometric median is calculated using Weiszfeld's algorithm. The method returns the Point object representing the strategic location of the warehouse (geometric median).

    static Point findGeometricMedian(List<Point> coordinates) {
        double epsilon = 1e-6;
        int n = coordinates.size();
        double xSum = 0, ySum = 0;

        for (Point point : coordinates) {
            xSum += point.x;
            ySum += point.y;
        }

        Point point = new Point((int) (xSum / n), (int) (ySum / n));

      //This is the core of Weiszfeld's algorithm, which iteratively updates the coordinates of the geometric median until it converges to a stable solution (within a certain threshold, epsilon). The algorithm calculates the numerator and denominator of the weighted mean of the coordinates, where the weights are inversely proportional to the distances between the current geometric median and the coordinates. The updated geometric median becomes the newPoint, and the loop continues until convergence.

        while (true) {
            double numerX = 0, numerY = 0, denom = 0;

            for (Point coord : coordinates) {
                double dist = euclideanDistance(coord, point);
                numerX += coord.x / dist;
                numerY += coord.y / dist;
                denom += 1 / dist;
            }

            Point newPoint = new Point((int) (numerX / denom), (int) (numerY / denom));

            if (euclideanDistance(point, newPoint) < epsilon) {
                break;
            }

            point = newPoint;
        }

        return point;
    }

  //This method calculates the total distance traveled by the truck when visiting all the factories and returning to the warehouse in the order specified by the list of Point objects (cartesian coordinates of factories). It sums up the Euclidean distances between consecutive factories.

   static double calculateTotalDistance(List<Point> points) {
    double totalDistance = 0;
    for (int i = 0; i < points.size() - 1; i++) {
        totalDistance += euclideanDistance(points.get(i), points.get(i + 1));
    }
    return totalDistance;
}


 // This method calculates the minimum distance from the warehouse (warehouse location) to all the points (factories) on the grid while avoiding the no-trespass areas. The method uses a breadth-first search (BFS) algorithm to traverse the grid and calculate distances to all reachable points.

    static Map<Point, Integer> calculateDistances(int[][] grid, Point warehouse, List<Point> noTrespassAreas) {
        int m = grid.length;
        Map<Point, Integer> distances = new HashMap<>();
        boolean[][] visited = new boolean[m][m];

        for (Point noTrespassArea : noTrespassAreas) {
            visited[noTrespassArea.x][noTrespassArea.y] = true;
        }

      //These arrays represent the possible movements in the x and y directions: right, down, left, and up (in that order).

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

      //These arrays represent the possible movements in the x and y directions: right, down, left, and up (in that order).

        List<Point> queue = new ArrayList<>();
        queue.add(warehouse);
        distances.put(warehouse, 0);
        visited[warehouse.x][warehouse.y] = true;

      //This is the BFS traversal part. We explore all four possible neighbors of the current point (currPoint) in the grid. If the neighbor is within the grid boundaries, not marked as visited, and not a no-trespass area, we add it to the queue for further exploration. We update its distance from the warehouse (currDist + 1) and mark it as visited

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

  //The method returns a Map<Point, Integer> where the keys are points (factories) reachable from the warehouse, and the values are their respective distances from the warehouse.
  //This method finds the optimum location for the warehouse on the grid. It first generates a list of warehouseCandidates, which contains all the cells on the grid except the no-trespass areas. These are the possible locations where the warehouse can be placed.
  
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
      //The method iterates over each warehouse candidate and calculates the total distance traveled from that candidate warehouse location using the calculateDistances method. It keeps track of the minimum total distance and the corresponding best warehouse location.

        double minTotalDistance = Double.POSITIVE_INFINITY;
        Point bestWarehouseLocation = null;

        for (Point warehouseCandidate : warehouseCandidates) {
            Map<Point, Integer> distances = calculateDistances(grid, warehouseCandidate, noTrespassAreas);
            double totalDistance = 0;
            for (int distance : distances.values()) {
                totalDistance += distance;
            }
            if (totalDistance < minTotalDistance) {
                minTotalDistance = totalDistance;
                bestWarehouseLocation = warehouseCandidate;
            }
        }

        return bestWarehouseLocation;
    }

  //In the main method, we provide example usages for both part a (strategic warehouse location) and part b (optimum solution in grid setup). We create sample data for factory coordinates and no-trespass areas and call the respective methods to find the strategic and optimum warehouse locations. The results are then printed to the console.

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
