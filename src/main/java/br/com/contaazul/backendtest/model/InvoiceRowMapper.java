package br.com.contaazul.backendtest.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by brunoi on 18/04/18.
 */
@SuppressWarnings("Since15")
public class InvoiceRowMapper implements RowMapper< Invoice >  {

    @Override
    public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
        Invoice invoice = new Invoice();
        invoice.setId(rs.getString("id"));
        invoice.setCustomer(rs.getString("customer"));
        invoice.setDueDate( rs.getDate("due_date"));
        invoice.setStatus( rs.getString("status") );
        invoice.setTotalInCents( rs.getInt("total_in_cents") );
        return invoice;
    }
}

