package br.com.contaazul.backendtest.model;

import br.com.contaazul.backendtest.utils.CustomerDateAndTimeDeserialize;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by brunoi on 25/04/18.
 */
@Entity
public class Invoice {

    @Id
    private String id;

    @JsonDeserialize(using=CustomerDateAndTimeDeserialize.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dueDate;

    private String customer;

    private Integer totalInCents;

    private BigDecimal fine;

    private String status;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getTotalInCents() {
        return totalInCents;
    }

    public void setTotalInCents(Integer totalInCents) {
        this.totalInCents = totalInCents;
    }

    public BigDecimal getFine() {
        return fine;
    }

    public void setFine(BigDecimal fine) {
        this.fine = fine;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
