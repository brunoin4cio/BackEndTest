package br.com.contaazul.backendtest.controller;

import br.com.contaazul.backendtest.model.Invoice;
import br.com.contaazul.backendtest.service.InvoiceService;
import br.com.contaazul.backendtest.service.exception.InvoiceNotFoundException;
import br.com.contaazul.backendtest.utils.InvoiceStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.regex.Pattern;


/**
 * Created by brunoi on 18/04/18.
 */

@Controller
@RequestMapping("/rest/bankslips")
public class InvoiceController {

    private static final Pattern UUID = Pattern.compile("([0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12})");


    @Autowired
    private InvoiceService invoiceService;


    @PostMapping("")
    public ResponseEntity add(@RequestBody Invoice invoice) {
        try {

            if (invoice == null || (invoice.getCustomer() == null
                    && invoice.getDueDate() == null && invoice.getStatus() == null
                    && invoice.getDueDate() == null)) {
                return new ResponseEntity<>("Bankslip not provided in the request body", HttpStatus.BAD_REQUEST);
            }

            String isValid = validateFields( invoice );

            if( ! StringUtils.isEmpty( isValid )  ) {
                return new ResponseEntity<>("Invalid bankslip provided.The possible reasons are: " +
                        "A field of the provided bankslip was null or with invalid values: Field = " + isValid, HttpStatus.UNPROCESSABLE_ENTITY);
            }

            invoiceService.save(invoice);


            return new ResponseEntity<>("Bankslip created", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Invoice>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<List<Invoice>> list() {
        try {
            return new ResponseEntity<List<Invoice>>(invoiceService.list(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<Invoice>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ResponseBody
    @GetMapping("/{id}")
    public ResponseEntity<Invoice> findById(@PathVariable("id") String id) {
        try {

            if( ! UUID.matcher(id).matches()){
                return new ResponseEntity<Invoice>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<Invoice>(invoiceService.findById(id), HttpStatus.OK);
        } catch (InvoiceNotFoundException e) {
            return new ResponseEntity<Invoice>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<Invoice>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}/pay")
    public ResponseEntity<Invoice> update(@PathVariable("id") String id) {
        try {
            invoiceService.update(id, InvoiceStatusEnum.PAID);
            return new ResponseEntity<Invoice>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Invoice>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/{id}/cancel")
    public ResponseEntity<Invoice> delete(@PathVariable("id") String id) {
        try {
            invoiceService.update(id, InvoiceStatusEnum.CANCELED);

            return new ResponseEntity<Invoice>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Invoice>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    private String  validateFields(Invoice invoice){


        if( invoice.getCustomer() == null ){
            return "Customer";
        }
        if( invoice.getStatus() == null ){
            return "Status";
        }
        if( invoice.getDueDate() == null ){
            return "Due Date";
        }
        if( invoice.getTotalInCents() == null ){
            return "Total In Cents";
        }

        return "";

    }


}

