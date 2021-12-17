package by.vita02.frontend.POJOs;

public class Order {
  private Long id;
  private String projectType;
  private String date;
  private String status;
  private Integer cost;
  private Integer numOfConvUnits;
  private String companyName;
  private String isPayed;

  public Order() {}

  public Order(
      Long id,
      String projectType,
      String date,
      String status,
      Integer cost,
      Integer numOfConvUnits,
      String companyName,
      String isPayed) {
    this.id = id;
    this.projectType = projectType;
    this.date = date;
    this.status = status;
    this.cost = cost;
    this.numOfConvUnits = numOfConvUnits;
    this.companyName = companyName;
    this.isPayed = isPayed;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getIsPayed() {
    return isPayed;
  }

  public void setIsPayed(String isPayed) {
    this.isPayed = isPayed;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public String getProjectType() {
    return projectType;
  }

  public void setProjectType(String projectType) {
    this.projectType = projectType;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Integer getCost() {
    return cost;
  }

  public void setCost(Integer cost) {
    this.cost = cost;
  }

  public Integer getNumOfConvUnits() {
    return numOfConvUnits;
  }

  public void setNumOfConvUnits(Integer numOfConvUnits) {
    this.numOfConvUnits = numOfConvUnits;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Order order = (Order) o;

    if (id != null ? !id.equals(order.id) : order.id != null) return false;
    if (projectType != null ? !projectType.equals(order.projectType) : order.projectType != null) return false;
    if (date != null ? !date.equals(order.date) : order.date != null) return false;
    if (status != null ? !status.equals(order.status) : order.status != null) return false;
    if (cost != null ? !cost.equals(order.cost) : order.cost != null) return false;
    if (numOfConvUnits != null ? !numOfConvUnits.equals(order.numOfConvUnits) : order.numOfConvUnits != null)
      return false;
    if (companyName != null ? !companyName.equals(order.companyName) : order.companyName != null) return false;
    return isPayed != null ? isPayed.equals(order.isPayed) : order.isPayed == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (projectType != null ? projectType.hashCode() : 0);
    result = 31 * result + (date != null ? date.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (cost != null ? cost.hashCode() : 0);
    result = 31 * result + (numOfConvUnits != null ? numOfConvUnits.hashCode() : 0);
    result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
    result = 31 * result + (isPayed != null ? isPayed.hashCode() : 0);
    return result;
  }
}
