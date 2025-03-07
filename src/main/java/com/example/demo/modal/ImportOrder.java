package com.example.demo.modal;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "import_order")
public class ImportOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date", columnDefinition = "datetime default current_timestamp")
    private Date createdDate = new Date();

    @Column(name = "cn_shipping_code" ,nullable = false, length = 255)
    private String cnShippingCode;

    @Column(name = "vn_shipping_code", length = 255, nullable = true)
    private String vnShippingCode;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "package_numbers", nullable = true)
    private Integer packageNumbers;

    @Column(name = "package_unit_value", nullable = true)
    private Double packageUnitValue;

    @Column(name = "insurance_price", nullable = true, columnDefinition = "decimal(10,2) default 0")
    private Integer insurancePrice = 0;

    
    @Column(name = "shipping_method", length = 255)
    private String shippingMethod;

    @Column(name = "email_customer", length = 255)
    private String emailCustomer;

   
    // Updated fields to String type
    @Column(name = "line_id", nullable = false)
    private String lineId;

    @Column(name = "package_unit_id", nullable = true)
    private String packageUnitId;

    @Column(name = "status_id", nullable = true)
    private String statusId;
    @Column(name = "locked", nullable = false, columnDefinition = "boolean default false")
    private Boolean locked = false;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public String getCnShippingCode() { return cnShippingCode; }
    public void setCnShippingCode(String cnShippingCode) { this.cnShippingCode = cnShippingCode; }

    public String getVnShippingCode() { return vnShippingCode; }
    public void setVnShippingCode(String vnShippingCode) { this.vnShippingCode = vnShippingCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getPackageNumbers() { return packageNumbers; }
    public void setPackageNumbers(Integer packageNumbers) { this.packageNumbers = packageNumbers; }

    public Double getPackageUnitValue() { return packageUnitValue; }
    public void setPackageUnitValue(Double packageUnitValue) { this.packageUnitValue = packageUnitValue; }

    public Integer getInsurancePrice() { return insurancePrice; }
    public void setInsurancePrice( Integer insurancePrice) { this.insurancePrice = insurancePrice; }

    public String getShippingMethod() { return shippingMethod; }
    public void setShippingMethod(String shippingMethod) { this.shippingMethod = shippingMethod; }


    public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	public String getEmailCustomer() {
		return emailCustomer;
	}
	public void setEmailCustomer(String emailCustomer) {
		this.emailCustomer = emailCustomer;
	}
	public String getLineId() { return lineId; }
    public void setLineId(String lineId) { this.lineId = lineId; }

    public String getPackageUnitId() { return packageUnitId; }
    public void setPackageUnitId(String packageUnitId) { this.packageUnitId = packageUnitId; }

    public String getStatusId() { return statusId; }
    public void setStatusId(String statusId) { this.statusId = statusId; }
}