package br.com.contaazul.backendtest.repository;

import br.com.contaazul.backendtest.model.Invoice;
import br.com.contaazul.backendtest.model.InvoiceRowMapper;
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
public class InvoiceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public void save(final Invoice invoice) {

        final String INSERT_SQL = "insert into invoice ( id, due_date, customer, total_in_cents, fine, status) values(?,  ?, ?, ?, ?, ? )";

        final java.sql.Timestamp sq = new java.sql.Timestamp(invoice.getDueDate().getTime());


        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(INSERT_SQL);
                ps.setString(1, String.valueOf(invoice.getId()));
                ps.setTimestamp(2, sq);
                ps.setString(3, String.valueOf(invoice.getCustomer()));
                ps.setInt(4, invoice.getTotalInCents());
                ps.setInt( 5, 0 );
                ps.setString(  6, invoice.getStatus() );
                return ps;
            }
        } );

    }

    public List<Invoice> all() {
        return jdbcTemplate.query("select * from invoice", new InvoiceRowMapper());
    }

    public int update(String id, InvoiceStatusEnum invoiceStatusEnum) {
        return jdbcTemplate.update("update invoice " + " set status = ? " + " where id = ?",
                new Object[] {
                        invoiceStatusEnum.name(), id
                });
    }

    public Invoice findById( String id ) {
        return jdbcTemplate.queryForObject("select * from invoice where id=?", new Object[] {
                    id
                },
                new BeanPropertyRowMapper< Invoice >(Invoice.class));
    }


}

