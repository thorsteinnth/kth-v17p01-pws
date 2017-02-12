package storage;

import bean.User;
import exceptions.UserNotFoundException;
import exceptions.UsernameAlreadyExistsException;
import exceptions.UsernameOrPasswordIncorrectException;

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

    public User getUserByUsername(String username)
    {
        for (User user : userMap.values())
        {
            if (user.getUsername().equals(username))
                return user;
        }

        return null;
    }

    public User login(String username, String password) throws UsernameOrPasswordIncorrectException
    {
        User foundUser = getUserByUsername(username);

        if (foundUser == null)
            throw new UsernameOrPasswordIncorrectException("Username or password incorrect");

        if (!foundUser.getPassword().equals(password))
            throw new UsernameOrPasswordIncorrectException("Username or password incorrect");

        return foundUser;
    }

    public User createUser(String username, String password) throws UsernameAlreadyExistsException
    {
        if (getUserByUsername(username) != null)
            throw new UsernameAlreadyExistsException("Username " + username + " is already taken");

        User newUser = new User();
        newUser.setId(getNextId());
        newUser.setUsername(username);
        newUser.setPassword(password);

        userMap.put(newUser.getId(), newUser);

        return newUser;
    }

    public User updateUser(User userToUpdate) throws UserNotFoundException, UsernameAlreadyExistsException
    {
        // Return the user instance from the map

        User foundUser = getUserWithId(userToUpdate.getId());

        // Check that username is unique
        User foundUserByUsername = getUserByUsername(userToUpdate.getUsername());
        if (foundUserByUsername != null && foundUserByUsername.getId() != userToUpdate.getId())
            throw new UsernameAlreadyExistsException("Username " + userToUpdate.getUsername() + " is already taken");

        foundUser.setUsername(userToUpdate.getUsername());
        foundUser.setPassword(userToUpdate.getPassword());

        return foundUser;
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

            userMap.put(user.getId(), user);
        }
    }
}
