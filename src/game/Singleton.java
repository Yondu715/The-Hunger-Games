package src.game;

public class Singleton {
    private static Singleton instance = null;
    private String login;

    private Singleton(String login){
        this.login = login;
    }

    public static Singleton getInstance(String login){
        if (instance == null){
            instance = new Singleton(login);
        }
        return instance;
    }

    public static Singleton getCreatedInstance(){
        return instance;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return this.login;
    }
}
