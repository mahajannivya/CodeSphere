package Editor.Project.Entity;

import java.util.Set;

public class UserUpdate {
    private int count;

    public UserUpdate(int count, Set<String> users) {
        this.count = count;
        this.users = users;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }

    private Set<String> users;

}
