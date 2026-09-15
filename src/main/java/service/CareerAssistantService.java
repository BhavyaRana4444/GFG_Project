package service;

import ai.ChatClient;
import model.Student;
import model.PlacementDrive;
import repository.DriveRepository;
import repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class CareerAssistantService {

    private final ChatClient chatClient;
    private final StudentRepository studentRepository;
    private final DriveRepository driveRepository;

    public CareerAssistantService(ChatClient chatClient, StudentRepository studentRepository, DriveRepository driveRepository) {
        this.chatClient = chatClient;
        this.studentRepository = studentRepository;
        this.driveRepository = driveRepository;
    }

    public String getCareerAdvice(String studentId, String driveId, String userMessage) {
        // Fetch context to ground the AI's response (Mandatory Integration Architecture 8.3)
        Student student = studentRepository.findById(studentId).orElse(null);
        PlacementDrive drive = driveRepository.findById(driveId).orElse(null);

        StringBuilder systemContext = new StringBuilder();
        systemContext.append("You are an advisory campus career assistant. ");
        systemContext.append("Do not invent requirements or fabricate application state. Provide helpful, concise guidance. ");

        if (student != null) {
            systemContext.append("The student's CGPA is ").append(student.getCgpa()).append(". ");
            systemContext.append("The student's skills are: ").append(String.join(", ", student.getSkills())).append(". ");
        }
        if (drive != null) {
            systemContext.append("The placement drive role is ").append(drive.getRole()).append(". ");
            systemContext.append("The minimum CGPA required is ").append(drive.getMinCgpa()).append(". ");
        }

        return chatClient.ask(systemContext.toString(), userMessage);
    }
}