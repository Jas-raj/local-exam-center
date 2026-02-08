// server/src/main/java/com/exam/server/service/SessionService.java
@Service
public class SessionService {
    
    @Autowired
    private ExamSessionRepository sessionRepo;
    
    public ExamSession startBatch(String batchId, List<Student> students) {
        ExamSession session = new ExamSession();
        session.setBatchId(batchId);
        session.setStatus(ExamStatus.ACTIVE);
        session.setStartTime(Instant.now());
        
        // Assign roll numbers to PCs
        Map<String, String> pcAssignment = assignPCs(students);
        session.setPcAssignments(pcAssignment);
        
        return sessionRepo.save(session);
    }
    
    public void dispatchCommand(String pcId, ExamCommand command) {
        // Send via gRPC or WebSocket
        CommandDispatcher.dispatch(pcId, command);
    }
}