package br.com.contaazul.backendtest.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Created by brunoi on 25/04/18.
 */

@Entity
public class RuleFine {

    @Id
    @GeneratedValue
    private String id;

    private Integer daysOfDelayInitial;

    private Integer daysOfDelayFinal;

    private BigDecimal rate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getDaysOfDelayInitial() {
        return daysOfDelayInitial;
    }

    public void setDaysOfDelayInitial(Integer daysOfDelayInitial) {
        this.daysOfDelayInitial = daysOfDelayInitial;
    }

    public Integer getDaysOfDelayFinal() {
        return daysOfDelayFinal;
    }

    public void setDaysOfDelayFinal(Integer daysOfDelayFinal) {
        this.daysOfDelayFinal = daysOfDelayFinal;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }
}
