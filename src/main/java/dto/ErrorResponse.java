package dto;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String code;
    private String message;
    private String path;

    public ErrorResponse(LocalDateTime timestamp, int status, String code, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.code = code;
        this.message = message;
        this.path = path;
    }

    // Getters
    public LocalDateTime getTimestamp() { return timestamp; }
    public int getStatus() { return status; }
    public String getCode() { return code; }
    public String getMessage() { return message; }
    public String getPath() { return path; }
}