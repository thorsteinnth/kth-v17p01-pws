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

    public User createUser(String username, String password)
    {
        // TODO Check that username is unique?

        User newUser = new User();
        newUser.setId(getNextId());
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setToken("");

        userMap.put(newUser.getId(), newUser);

        return newUser;
    }

    private int getNextId()
    {
        // Next ID should be one higher than the largest ID already present

        int highestId = Integer.MIN_VALUE;

        for (int id : userMap.keySet())
        {
            if (id > highestId)
                highestId = id;
        }

        return highestId + 1;
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
