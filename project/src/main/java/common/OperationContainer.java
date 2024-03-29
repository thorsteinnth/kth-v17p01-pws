package common;

public class OperationContainer
{
    public String name;
    public MessageContainer inputMessage;
    public MessageContainer outputMessage;

    public OperationContainer(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return "OperationContainer{" +
                "name='" + name + '\'' +
                ", inputMessage=" + inputMessage +
                ", outputMessage=" + outputMessage +
                '}';
    }
}
