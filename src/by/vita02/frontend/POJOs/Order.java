package by.vita02.frontend.POJOs;

public class Order {
    private String projectType;
    private String date;
    private String status;
    private Integer cost;
    private Integer numOfConvUnits;
    private String companyName;

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
}
