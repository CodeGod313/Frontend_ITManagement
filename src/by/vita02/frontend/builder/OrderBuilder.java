package by.vita02.frontend.builder;

import by.vita02.frontend.POJOs.Order;

public interface OrderBuilder {
    void setProjectType(String projectType);
    void setDate(String date);
    void setStatus(String status);
    void setCost(Integer cost);
    void setNumOfConvUnits(Integer numOfConvUnits);
    void setCompanyName(String companyName);
    void setIsPayed(String isPayed);
    void setId(Long id);
    Order build();
}
