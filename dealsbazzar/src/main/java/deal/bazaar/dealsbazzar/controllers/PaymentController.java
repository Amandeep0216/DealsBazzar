package deal.bazaar.dealsbazzar.controllers;

import deal.bazaar.dealsbazzar.models.Payment;
import deal.bazaar.dealsbazzar.services.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dealbazzar")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" })
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/payment")
    public ResponseEntity savePayment(@RequestBody Payment payment)
    {
        Payment newPayment= paymentService.savePayment(payment);
        if(newPayment==null)
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        else
            return ResponseEntity.ok(newPayment);
    }
}
