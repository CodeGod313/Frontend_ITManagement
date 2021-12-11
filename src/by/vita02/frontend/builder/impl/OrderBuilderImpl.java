package by.vita02.frontend.builder.impl;

import by.vita02.frontend.POJOs.Order;
import by.vita02.frontend.builder.OrderBuilder;

public class OrderBuilderImpl implements OrderBuilder {
  Long id;
  private String projectType;
  private String date;
  private String status;
  private Integer cost;
  private Integer numOfConvUnits;
  private String companyName;
  private String isPayed;

  @Override
  public void setProjectType(String projectType) {
    this.projectType = projectType;
  }

  @Override
  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public void setCost(Integer cost) {
    this.cost = cost;
  }

  @Override
  public void setNumOfConvUnits(Integer numOfConvUnits) {
    this.numOfConvUnits = numOfConvUnits;
  }

  @Override
  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  @Override
  public void setIsPayed(String isPayed) {
    this.isPayed = isPayed;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public Order build() {
    return new Order(id, projectType, date, status, cost, numOfConvUnits, companyName, isPayed);
  }
}
