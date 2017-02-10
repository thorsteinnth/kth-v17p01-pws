package storage;

import bean.User;
import exceptions.UserNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserStore
{
    private static Map<Integer, User> userMap;
    private static UserStore instance = null;

    private UserStore()
    {
        userMap = new HashMap<>();
        generateUsers();
    }

    public static UserStore getUserStore()
    {
        if (instance == null)
            instance = new UserStore();

        return instance;
    }

    public ArrayList<User> getAllUsers()
    {
        return new ArrayList<User>(userMap.values());
    }

    public User getUserWithId(int id) throws UserNotFoundException
    {
        if (!userMap.containsKey(id))
            throw new UserNotFoundException("User with ID " + id + " not found.");

        return userMap.get(id);
    }

    private void generateUsers()
    {
        for (int i=0; i<=10; i++)
        {
            User user = new User();
            user.setId(i);
            user.setUsername("user" + i);
            user.setPassword("user" + i + "pass");
            user.setToken("");

            userMap.put(user.getId(), user);
        }
    }
}
