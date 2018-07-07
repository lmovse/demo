package info.lmovse.oauth.client.domain;

/**
 * Created by lmovse on 2017/8/25.
 * Tomorrow is a nice day.
 */
public class User {
    private Long userId;
    private String username;
    private String password;
    private Integer age;
    private String hobby;

    public String getUsername() {
        return username;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}

