package by.vita02.frontend.dto;

public class QueryDTO {
  private Long clientID;
  private String query;

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

  public void setClientID(Long clientID) {
    this.clientID = clientID;
  }

  public void setQuery(String query) {
    this.query = query;
  }
}
