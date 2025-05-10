package pe.edu.upc.center.platform.payment.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentByUserQuery;
import pe.edu.upc.center.platform.payment.domain.services.PaymentCommandService;
import pe.edu.upc.center.platform.payment.domain.services.PaymentQueryService;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.CreatePaymentResource;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.PaymentResource;
import pe.edu.upc.center.platform.payment.interfaces.rest.transform.CreatePaymentCommandFromResourceAssembler;
import pe.edu.upc.center.platform.payment.interfaces.rest.transform.PaymentResourceFromEntityAssembler;

import java.util.List;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "Payment Management Endpoint")
public class PaymentsController {

    private final PaymentQueryService paymentQueryService;
    private final PaymentCommandService paymentCommandService;

    public PaymentsController(PaymentQueryService paymentQueryService, PaymentCommandService paymentCommandService) {
        this.paymentQueryService = paymentQueryService;
        this.paymentCommandService = paymentCommandService;
    }

    @GetMapping("/{profileId}")
    ResponseEntity<List<PaymentResource>> handle(@PathVariable Long userId) {
        GetPaymentByUserQuery getPaymentByUserQuery = new GetPaymentByUserQuery(userId);
        var payments = this.paymentQueryService.handle(getPaymentByUserQuery);

        return new ResponseEntity<>(
                payments
                        .stream()
                        .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                        .toList()
                , HttpStatus.OK);

    }


    @PostMapping
    public ResponseEntity<PaymentResource> createPayment(@RequestBody CreatePaymentResource resource) {
        var createPaymentCommand = CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource);
        var payment = this.paymentCommandService.handle(createPaymentCommand);

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(payment);
        return ResponseEntity.ok(paymentResource);

    }


}