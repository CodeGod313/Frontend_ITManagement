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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != null ? !id.equals(client.id) : client.id != null) return false;
        if (nickname != null ? !nickname.equals(client.nickname) : client.nickname != null) return false;
        if (passportNumber != null ? !passportNumber.equals(client.passportNumber) : client.passportNumber != null)
            return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (surname != null ? !surname.equals(client.surname) : client.surname != null) return false;
        return email != null ? email.equals(client.email) : client.email == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (nickname != null ? nickname.hashCode() : 0);
        result = 31 * result + (passportNumber != null ? passportNumber.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
