package src.DB;

public class Player {
    private String login;
    private String password;

    public Player(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public Player() {}

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
