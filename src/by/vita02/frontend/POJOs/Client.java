package by.vita02.frontend.POJOs;

public class Client {
    private Long id;
    private String nickname;
    private String passportNumber;
    private String name;
    private String surname;
    private String email;

    public Client(Long id, String nickname, String passportNumber, String name, String surname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.passportNumber = passportNumber;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Client() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
