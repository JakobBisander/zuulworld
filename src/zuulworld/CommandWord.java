package zuulworld;



public enum CommandWord
{
    GO("go"), QUIT("quit"), HELP("help"), UNKNOWN("?"), ATTACK("attack"), FLEE("flee"), TAKE("take"), DROP("drop"), STATS("stats"), TALK("talk");
    
    private final String commandString;
    
    CommandWord(String commandString)
    {
        this.commandString = commandString;
    }
    
    public String toString()
    {
        return commandString;
    }
}
