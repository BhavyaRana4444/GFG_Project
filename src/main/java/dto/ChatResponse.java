package dto;

public class ChatResponse {
    private String answer;
    private String model;
    private boolean advisory;

    public ChatResponse(String answer, String model, boolean advisory) {
        this.answer = answer;
        this.model = model;
        this.advisory = advisory;
    }

    // Getters
    public String getAnswer() { return answer; }
    public String getModel() { return model; }
    public boolean isAdvisory() { return advisory; }
}