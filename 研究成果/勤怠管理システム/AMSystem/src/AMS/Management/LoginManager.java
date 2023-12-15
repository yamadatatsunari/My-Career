package AMS.Management;

public class LoginManager {
    private static LoginManager instance;
    private String loggedInUserName;
    private String loggedInPosition;
    private String loggedInUserFullName;
    
    // Singleton パターンを実装
    private LoginManager() {
        // プライベートコンストラクタ
    }

    public static LoginManager getInstance() {
        if (instance == null) {
            instance = new LoginManager();
        }
        return instance;
    }

    public void login() {
        // ログイン処理があればここに追加
    }

    public void logout() {
        // ログアウト処理があればここに追加
        loggedInUserName = null;
        loggedInPosition = null;
        loggedInUserFullName = null;
    }

    public void setLoggedInUserName(String loggedInUserName) {
        this.loggedInUserName = loggedInUserName;
    }

    public String getLoggedInPosition() {
        return loggedInPosition;
    }

    public void setLoggedInPosition(String loggedInPosition) {
        this.loggedInPosition = loggedInPosition;
    }
    
    public String getLoggedInUserFullName() {
        return loggedInUserFullName;
    }

    public void setLoggedInUserFullName(String loggedInUserFullName) {
        this.loggedInUserFullName = loggedInUserFullName;
    }
}
