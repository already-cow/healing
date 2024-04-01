package misoya.healing.domain.chatbot;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Service
public class ChatbotService {

    private final ObjectMapper objectMapper;

    public ChatbotService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public List<Chatbot> loadChatbotData() throws IOException {
        ClassPathResource resource = new ClassPathResource("chat-data.json");
        return Arrays.asList(objectMapper.readValue(resource.getInputStream(), Chatbot[].class));
    }
}