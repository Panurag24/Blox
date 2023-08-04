import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;

public class JsonParser {

    // Function to parse JSON string into a corresponding Java object
    public static Object parseJson(String jsonString) throws IOException {
        // Create an ObjectMapper instance, which is the main entry point for Jackson's JSON processing
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Use the objectMapper to parse the JSON string into a Java object.
        // The second argument "Object.class" tells Jackson to map the JSON to the most appropriate Java data type (Object, Map, List, etc.)
        return objectMapper.readValue(jsonString, Object.class);
    }

    public static void main(String[] args) {
        try {
            // Input JSON string
            String jsonString = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";

            // Parse JSON string to Object using the parseJson function
            Object parsedObject = parseJson(jsonString);

            // Output the parsed object
            System.out.println("Parsed Object: " + parsedObject);
        } catch (IOException e) {
            // Handle any IOException that might occur during parsing
            e.printStackTrace();
        }
    }
}

// Explanation:

// import com.fasterxml.jackson.databind.ObjectMapper;: Importing the ObjectMapper class from the Jackson library, which is used to convert JSON strings to Java objects.

// public static Object parseJson(String jsonString) throws IOException { ... }: The parseJson method takes a JSON string as input and returns a Java Object, which can be either a Map, List, or any other Java object depending on the JSON structure. The method uses the Jackson ObjectMapper to perform the conversion.

// ObjectMapper objectMapper = new ObjectMapper();: Creating an instance of ObjectMapper, which is responsible for the JSON parsing.

// return objectMapper.readValue(jsonString, Object.class);: The readValue method of ObjectMapper is used to parse the JSON string into a Java object. The second argument (Object.class) specifies the type of the Java object to which the JSON should be mapped. In this case, we use Object.class, which represents any valid JSON structure.

// public static void main(String[] args) { ... }: The main method is used to test the parseJson function.

// String jsonString = "{\"name\":\"John\",\"age\":30,\"city\":\"New York\"}";: This is the input JSON string that we want to parse.

// Object parsedObject = parseJson(jsonString);: We call the parseJson method to parse the JSON string, and the result is stored in the parsedObject variable.

// System.out.println("Parsed Object: " + parsedObject);: We print the parsed object to see the result.

// Now, please run the code and provide the input JSON string. After you run it, share the input JSON string, and I'll help you interpret the output.
