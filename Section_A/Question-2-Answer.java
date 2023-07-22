import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarehouseLocation {

    public static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // Helper function to calculate the Euclidean distance between two points
    public static double euclideanDistance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    // Main function to find the optimal warehouse location and destination assignment
    public static Map<Point, List<Point>> findWarehouseLocation(List<Point> destinations, List<Point> warehouseCandidates,
                                                               int truckCapacity, int numTrucks) {
        double bestDistance = Double.POSITIVE_INFINITY;
        List<Point> bestWarehouseLocation = new ArrayList<>();
        Map<Point, List<Point>> bestDestinationAssignment = new HashMap<>();

        // Generate all possible combinations of warehouse assignments
        List<List<Point>> warehouseCombinations = new ArrayList<>();
        generateCombinations(warehouseCandidates, numTrucks, 0, new ArrayList<>(), warehouseCombinations);

        for (List<Point> warehouses : warehouseCombinations) {
            double totalDistance = 0;
            Map<Point, List<Point>> currentAssignment = new HashMap<>();
            for (Point warehouse : warehouses) {
                currentAssignment.put(warehouse, new ArrayList<>());
            }

            // Assign each destination to the closest warehouse with available capacity
            for (Point dest : destinations) {
                double minDistance = Double.POSITIVE_INFINITY;
                Point assignedWarehouse = null;

                for (Point warehouse : warehouses) {
                    double dist = euclideanDistance(dest, warehouse);
                    if (dist < minDistance && currentAssignment.get(warehouse).size() < truckCapacity) {
                        minDistance = dist;
                        assignedWarehouse = warehouse;
                    }
                }

                if (assignedWarehouse != null) {
                    totalDistance += minDistance;
                    currentAssignment.get(assignedWarehouse).add(dest);
                }
            }

            // Update the best assignment if the total distance is minimized
            if (totalDistance < bestDistance) {
                bestDistance = totalDistance;
                bestWarehouseLocation = new ArrayList<>(warehouses);
                bestDestinationAssignment = new HashMap<>(currentAssignment);
            }
        }

        return bestDestinationAssignment;
    }

    // Helper function to generate all possible combinations of warehouses for assignment
    private static void generateCombinations(List<Point> warehouseCandidates, int numTrucks, int start,
                                             List<Point> tempCombination, List<List<Point>> result) {
        if (tempCombination.size() == numTrucks) {
            result.add(new ArrayList<>(tempCombination));
            return;
        }

        for (int i = start; i < warehouseCandidates.size(); i++) {
            tempCombination.add(warehouseCandidates.get(i));
            generateCombinations(warehouseCandidates, numTrucks, i + 1, tempCombination, result);
            tempCombination.remove(tempCombination.size() - 1);
        }
    }

    public static void main(String[] args) {
        List<Point> destinations = new ArrayList<>();
        destinations.add(new Point(2, 5));
        destinations.add(new Point(8, 3));
        destinations.add(new Point(4, 6));
        destinations.add(new Point(7, 2));

        List<Point> warehouseCandidates = new ArrayList<>();
        warehouseCandidates.add(new Point(1, 3));
        warehouseCandidates.add(new Point(5, 9));
        warehouseCandidates.add(new Point(3, 4));

        int truckCapacity = 2;
        int numTrucks = 2;

        // Find the optimal warehouse locations and destination assignments
        Map<Point, List<Point>> destinationAssignment = findWarehouseLocation(destinations, warehouseCandidates,
                truckCapacity, numTrucks);

        // Print the results
        for (Map.Entry<Point, List<Point>> entry : destinationAssignment.entrySet()) {
            System.out.println("Warehouse Location: (" + entry.getKey().x + ", " + entry.getKey().y + ")");
            System.out.println("Assigned Destinations: " + entry.getValue());
        }
    }
}



// Explanation:

// MAX_SESSIONS: This constant defines the maximum number of concurrent sessions the system can handle. In this example, we set it to 1,000,000.

// availableIds: The BitSet stores the availability status of each session ID. A bit set to 1 represents an available ID, and a bit set to 0 represents a used ID.

// SessionIdGenerator(): The constructor initializes the availableIds BitSet and sets all session IDs (except 0) as available.

// getUniqueSessionId(): This method finds the first available session ID (bit set to 1) and marks it as used (set the bit to 0). If no available session ID is found, it throws an exception.

// releaseSessionId(int sessionId): This method takes an active session ID and marks it as available for reuse by setting the corresponding bit to 1. If the provided session ID is invalid (not in the valid range), it throws an exception.

// main: An example usage of the SessionIdGenerator is provided in the main method. It generates two unique session IDs, releases one of them, and then generates another session ID, demonstrating the reuse of released IDs.

//Generated Session ID 1: 1
//Generated Session ID 2: 2
//Released Session ID 1.
//Generated Session ID 3 (reused): 1
