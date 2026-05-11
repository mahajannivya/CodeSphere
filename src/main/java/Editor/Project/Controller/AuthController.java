package Editor.Project.Controller;

        import Editor.Project.Entity.Users;
        import Editor.Project.Service.UserService;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.ResponseEntity;
        import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService service;

    @PostMapping("/register")
    public String register(@RequestBody Users users) {
        service.register(users);
        return "Registered Successfully";

    }

        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody Users users) {

            Users existing = service.login(users.getEmail(), users.getPassword());

            if (existing != null) {
                return ResponseEntity.ok(existing);
            } else {
                return ResponseEntity.status(401).body("FAIL");
            }
        }
    }
