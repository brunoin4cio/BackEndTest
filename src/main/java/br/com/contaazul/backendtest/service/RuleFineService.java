package br.com.contaazul.backendtest.service;

import br.com.contaazul.backendtest.model.Invoice;
import br.com.contaazul.backendtest.model.RuleFine;
import br.com.contaazul.backendtest.repository.RuleFineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by brunoi on 18/04/18.
 */
@Service
public class RuleFineService {

    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private RuleFineRepository fineRuleRepository;

    public void calculateFine(Invoice invoice) {

        Date dt = null;

        try {
            dt = sf.parse(sf.format(new Date()));
        } catch (ParseException e) {
            throw new IllegalArgumentException( "Error parse data", e );
        }

        if(invoice.getDueDate().before(dt) ) {
            return;
        }

        Long diff = invoice.getDueDate().getTime() - dt.getTime();

        Long diffDays = diff / (24 * 60 * 60 * 1000);

        if(diffDays <= 0 ) {
            return;
        }

        invoice.setFine( generateFine( diffDays, invoice ) );


    }

    private BigDecimal generateFine(Long diffDays, Invoice invoice ) {

        RuleFine ruleFine =  fineRuleRepository.getRuleBydaysOfDelay( diffDays.intValue() );

        Integer value = invoice.getTotalInCents();

        BigDecimal rate =  new BigDecimal( diffDays.intValue() ).multiply(ruleFine.getRate());

        BigDecimal rateResult = rate.divide(BigDecimal.valueOf(100));

        return rateResult.multiply(BigDecimal.valueOf(value));

    }




}