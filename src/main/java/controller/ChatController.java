package controller;

import dto.ChatRequest;
import dto.ChatResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.CareerAssistantService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final CareerAssistantService aiService;
    private final String configuredModel;

    public ChatController(CareerAssistantService aiService, @Value("${ollama.model}") String configuredModel) {
        this.aiService = aiService;
        this.configuredModel = configuredModel;
    }

    @PostMapping
    public ChatResponse askAssistant(@RequestBody ChatRequest request) {
        String answer = aiService.getCareerAdvice(request.getStudentId(), request.getDriveId(), request.getMessage());
        
        // Ensure every response is labeled as advisory (FR-09)
        return new ChatResponse(answer, configuredModel, true);
    }
}