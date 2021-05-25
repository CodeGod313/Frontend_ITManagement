package by.vita02.frontend.dto;

public class QueryDTO {
    private final Long clientID;
    private final String query;

    public QueryDTO(Long clientID, String query) {
        this.clientID = clientID;
        this.query = query;
    }

    public Long getClientID() {
        return clientID;
    }

    public String getQuery() {
        return query;
    }
}
