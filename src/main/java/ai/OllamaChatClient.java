package ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import service.DomainException;

import java.util.List;
import java.util.Map;

@Component
public class OllamaChatClient implements ChatClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final String model;

    public OllamaChatClient(
            @Value("${ollama.base-url}") String baseUrl,
            @Value("${ollama.model}") String model,
            @Value("${ollama.connect-timeout-seconds}") int connectTimeout,
            @Value("${ollama.read-timeout-seconds}") int readTimeout) {
        
        this.baseUrl = baseUrl;
        this.model = model;

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(connectTimeout * 1000);
        factory.setReadTimeout(readTimeout * 1000);
        this.restTemplate = new RestTemplate(factory);
    }

    @Override
    @SuppressWarnings("unchecked")
    public String ask(String systemContext, String userQuestion) {
        String url = baseUrl + "/api/chat";

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "stream", false,
                "messages", List.of(
                        Map.of("role", "system", "content", systemContext),
                        Map.of("role", "user", "content", userQuestion)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        try {
            // FIX: Using Map.class instead of ParameterizedTypeReference
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);
            if (response.getBody() != null && response.getBody().containsKey("message")) {
                Map<String, String> message = (Map<String, String>) response.getBody().get("message");
                return message.get("content");
            }
            return "The assistant could not generate a valid response.";
        } catch (RestClientException e) {
            throw new DomainException(503, "OLLAMA_UNAVAILABLE", "AI Career Assistant is currently offline. Please try again later.");
        }
    }
}