package exceptions;

public class UsernameOrPasswordIncorrectException extends Exception
{
    public UsernameOrPasswordIncorrectException(String message)
    {
        super(message);
    }
}
