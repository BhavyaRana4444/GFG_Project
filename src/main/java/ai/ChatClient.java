package ai;

public interface ChatClient {
    String ask(String systemContext, String userQuestion);
}