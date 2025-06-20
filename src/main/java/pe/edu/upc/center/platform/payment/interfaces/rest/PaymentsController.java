package pe.edu.upc.center.platform.payment.interfaces.rest;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentIntentCommand;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentIntentIdQuery;
import pe.edu.upc.center.platform.payment.domain.services.PaymentCommandService;
import pe.edu.upc.center.platform.payment.domain.services.PaymentQueryService;
import pe.edu.upc.center.platform.payment.infrastructure.persistence.jpa.respositories.PaymentRepository;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.CreatePaymentResource;
import pe.edu.upc.center.platform.payment.interfaces.rest.transform.CreatePaymentCommandFromResourceAssembler;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/payments", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "Payment Management Endpoint")
public class PaymentsController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;
    private final PaymentRepository paymentRepository;

    @Value("${stripe.api.key.test}")
    private String key;

    public PaymentsController(PaymentQueryService paymentQueryService, PaymentCommandService paymentCommandService, PaymentQueryService paymentQueryService1, PaymentRepository paymentRepository) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService1;
        this.paymentRepository = paymentRepository;
    }

    @PostMapping
    @Operation(summary = "Process Payment", description = "Processes a payment intent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> createPayment(@RequestBody CreatePaymentResource resource){
        var createPaymentCommand = CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource);
        PaymentIntent paymentIntent = paymentCommandService.handle(createPaymentCommand);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }


    @PostMapping("confirm/{paymentIntentId}")
    @Operation(summary = "Confirm Payment Intent", description = "Confirms a payment intent by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment intent confirmed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment intent ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> updatePaymentIntent(@PathVariable("paymentIntentId") String id) throws StripeException {
        var updatePaymentIntentCommand = new UpdatePaymentIntentCommand(id);
        PaymentIntent paymentIntent = paymentCommandService.handle(updatePaymentIntentCommand);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }


    @GetMapping("/{paymentIntentId}")
    public ResponseEntity<Map<String, Object>> getPaymentIntend(@PathVariable String paymentIntentId) throws StripeException {

        Stripe.apiKey = key; // Asigna el valor directamente o desde env

        if (!paymentRepository.existsByStripePaymentId(paymentIntentId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "El PaymentIntent con el ID proporcionado no existe."));
        }

        PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);

        Map<String, Object> response = new HashMap<>();
        response.put("amount", intent.getAmount());
        response.put("id", intent.getId());
        response.put("client_secret", intent.getClientSecret());

        return ResponseEntity.ok(response);
    }






}