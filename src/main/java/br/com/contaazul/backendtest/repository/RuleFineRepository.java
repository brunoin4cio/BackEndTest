package br.com.contaazul.backendtest.repository;

import br.com.contaazul.backendtest.model.Invoice;
import br.com.contaazul.backendtest.model.InvoiceRowMapper;
import br.com.contaazul.backendtest.model.RuleFine;
import br.com.contaazul.backendtest.utils.InvoiceStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by brunoi on 18/04/18.
 */
@Repository
public class RuleFineRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public RuleFine getRuleBydaysOfDelay(Integer daysOfDelay ) {
        return jdbcTemplate.queryForObject("select * from rule_fine where ? between days_of_delay_initial  and days_of_delay_final", new Object[] {
                        daysOfDelay
                },
                new BeanPropertyRowMapper< RuleFine >(RuleFine.class));
    }


}

