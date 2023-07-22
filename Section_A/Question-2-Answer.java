import java.util.BitSet;

public class SessionIdGenerator {
    private static final int MAX_SESSIONS = 1000000; // Maximum number of concurrent sessions
    private BitSet availableIds; // BitSet to store available session IDs

    public SessionIdGenerator() {
        availableIds = new BitSet(MAX_SESSIONS);
        availableIds.set(1, MAX_SESSIONS); // Mark all IDs as available except 0 (reserved for special use)
    }

    public int getUniqueSessionId() {
        int sessionId = availableIds.nextSetBit(0); // Find the first available session ID

        if (sessionId == -1) {
            throw new IllegalStateException("No available session IDs.");
        }

        availableIds.clear(sessionId); // Mark the session ID as used
        return sessionId;
    }

    public void releaseSessionId(int sessionId) {
        if (sessionId >= 1 && sessionId < MAX_SESSIONS) {
            availableIds.set(sessionId); // Mark the session ID as available for reuse
        } else {
            throw new IllegalArgumentException("Invalid session ID.");
        }
    }

    public static void main(String[] args) {
        SessionIdGenerator generator = new SessionIdGenerator();

        // Example usage
        int sessionId1 = generator.getUniqueSessionId();
        System.out.println("Generated Session ID 1: " + sessionId1);

        int sessionId2 = generator.getUniqueSessionId();
        System.out.println("Generated Session ID 2: " + sessionId2);

        generator.releaseSessionId(sessionId1);
        System.out.println("Released Session ID 1.");

        int sessionId3 = generator.getUniqueSessionId();
        System.out.println("Generated Session ID 3 (reused): " + sessionId3);
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
