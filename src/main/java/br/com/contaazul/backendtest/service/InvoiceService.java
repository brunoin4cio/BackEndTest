package br.com.contaazul.backendtest.service;

import br.com.contaazul.backendtest.model.Invoice;
import br.com.contaazul.backendtest.repository.InvoiceRepository;
import br.com.contaazul.backendtest.service.exception.InvoiceNotFoundException;
import br.com.contaazul.backendtest.utils.InvoiceStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.UUID;

/**
 * Created by brunoi on 18/04/18.
 */
@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private RuleFineService ruleFineService;

    public void save(Invoice invoice) {

        validateInvoice( invoice );

        UUID id = UUID.randomUUID();

        invoice.setId( id.toString() );

        invoiceRepository.save(invoice);
    }

    public List<Invoice> list() {
        return invoiceRepository.all();
    }

    public void update(String id, InvoiceStatusEnum invoiceStatusEnum) {


        Assert.notNull( id , "Id can not be null" );

        invoiceRepository.update(id, invoiceStatusEnum);
    }

    public Invoice findById(String id ) throws InvoiceNotFoundException {

        Assert.notNull( id, "Id can not be null" );

        Invoice invoice = invoiceRepository.findById( id );

        if( invoice == null ) {
            throw new InvoiceNotFoundException( "Invoice not found"  );
        }

        ruleFineService.calculateFine( invoice );

        return invoice;

    }





    private void validateInvoice( Invoice invoice ){

        Assert.notNull( invoice, "invoice can not be null" );

        Assert.notNull( invoice.getCustomer(), "Customer can not be null" );

        Assert.notNull( invoice.getDueDate(), "DueDate can not be null" );

        Assert.notNull( invoice.getStatus(), "Status de nascimento can not be null" );

        Assert.notNull( invoice.getTotalInCents(), "Total In Cents can not be null" );


        if( invoice.getStatus().equals(InvoiceStatusEnum.CANCELED)
                || invoice.getStatus().equals(InvoiceStatusEnum.PAID)
                || invoice.getStatus().equals(InvoiceStatusEnum.PENDING) ){
            throw new IllegalArgumentException( "Invoice Status not valid" );
        }

        if( invoice.getTotalInCents() < 0 ) {
            throw new IllegalArgumentException( "Invoice Total In Cents not valid" );
        }

    }

}
