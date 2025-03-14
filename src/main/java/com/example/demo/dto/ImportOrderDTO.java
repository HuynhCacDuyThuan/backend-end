package com.example.demo.dto;

import java.util.Date;

public class ImportOrderDTO {
    private Long id;
    private Date createdDate;
    private String name;
    private String cnShippingCode;
    private String vnShippingCode;
    private Integer packageNumbers;
    private Double packageUnitValue;
    private  Integer insurancePrice;
    private String shippingMethod;
    private String customerCode;
    private Boolean locked;
    private Long userId;
    private Long lineId;
    private Long packageUnitId;
    private Long statusId;

    // Constructor
    public ImportOrderDTO() {
        this.createdDate = new Date(); // Mặc định ngày tạo
        this.locked = false; // Mặc định là chưa khóa
        this.insurancePrice = 0; // Giá bảo hiểm mặc định = 0
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnShippingCode() {
        return cnShippingCode;
    }

    public void setCnShippingCode(String cnShippingCode) {
        this.cnShippingCode = cnShippingCode;
    }

    public String getVnShippingCode() {
        return vnShippingCode;
    }

    public void setVnShippingCode(String vnShippingCode) {
        this.vnShippingCode = vnShippingCode;
    }

    public Integer getPackageNumbers() {
        return packageNumbers;
    }

    public void setPackageNumbers(Integer packageNumbers) {
        this.packageNumbers = packageNumbers;
    }

    public Double getPackageUnitValue() {
        return packageUnitValue;
    }

    public void setPackageUnitValue(Double packageUnitValue) {
        this.packageUnitValue = packageUnitValue;
    }

    public Integer getInsurancePrice() {
        return insurancePrice;
    }

    public void setInsurancePrice( Integer insurancePrice) {
        this.insurancePrice = insurancePrice;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Long getPackageUnitId() {
        return packageUnitId;
    }

    public void setPackageUnitId(Long packageUnitId) {
        this.packageUnitId = packageUnitId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}
