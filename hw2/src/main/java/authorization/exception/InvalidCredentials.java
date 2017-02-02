package authorization.exception;

public class InvalidCredentials extends Exception {
    public InvalidCredentials() {
        super("Invalid username or password!");
    }
}
