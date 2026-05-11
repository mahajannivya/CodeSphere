package Editor.Project.Service;

import Editor.Project.Entity.Users;
import Editor.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository repo ;

    public Users register(Users users) {
        return repo.save(users);
    }

    public Users login(String email, String password) {
        Users users = repo.findByEmail(email);

        if (users != null && users.getPassword().equals(password)) {
            return users;
        }
        return null;
    }
}
