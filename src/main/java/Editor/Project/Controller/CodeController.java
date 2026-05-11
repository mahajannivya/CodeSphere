package Editor.Project.Controller;

import Editor.Project.Service.CodeExecutionService;
import Editor.Project.dto.CodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CodeExecutionService service;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;//utility helper class


     //when user clicks run btn this method executes
    @PostMapping("/run/{roomId}")
    public String runCode(@PathVariable String roomId,
                          @RequestBody CodeRequest req) {

        String result = service.executeJavaCode(
                req.getCode(),
                req.getInput(),
                req.getLanguage()
        );
        //sharing output live
        messagingTemplate.convertAndSend("/topic/output/" + roomId, result);

        return result;
    }
}