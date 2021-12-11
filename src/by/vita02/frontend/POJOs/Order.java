package by.vita02.frontend.POJOs;

public class Order {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String projectType;
    private String date;
    private String status;
    private Integer cost;
    private Integer numOfConvUnits;
    private String companyName;
    private String isPayed;

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
}
