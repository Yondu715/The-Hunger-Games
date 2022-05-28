package src.game;

public class Singleton {
    private static Singleton instance;
    public String login;

    private Singleton(String login){
        this.login = login;
    }

    public static Singleton getInstance(String login){
        if (instance == null){
            instance = new Singleton(login);
        }
        return instance;
    }
}
