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
import pe.edu.upc.center.platform.payment.domain.model.aggregates.Payment;
import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentIntentCommand;
import pe.edu.upc.center.platform.payment.domain.model.commands.UpdatePaymentStatusCommand;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetByIdQuery;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentIntentIdQuery;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentsByCaregiverIdAndPaymentStatusQuery;
import pe.edu.upc.center.platform.payment.domain.model.queries.GetPaymentsByCaregiverIdQuery;
import pe.edu.upc.center.platform.payment.domain.model.valueobjects.PaymentStatus;
import pe.edu.upc.center.platform.payment.domain.services.PaymentCommandService;
import pe.edu.upc.center.platform.payment.domain.services.PaymentQueryService;
import pe.edu.upc.center.platform.payment.infrastructure.persistence.jpa.respositories.PaymentRepository;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.CreatePaymentResource;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.PaymentResource;
import pe.edu.upc.center.platform.payment.interfaces.rest.resources.UpdatePaymentStatusResource;
import pe.edu.upc.center.platform.payment.interfaces.rest.transform.CreatePaymentCommandFromResourceAssembler;
import pe.edu.upc.center.platform.payment.interfaces.rest.transform.PaymentResourceFromEntityAssembler;
import pe.edu.upc.center.platform.payment.interfaces.rest.transform.UpdatePaymentStatusCommandFromResourceAssembler;
import pe.edu.upc.center.platform.schedules.domain.model.queries.GetAllSchedulesQuery;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.CreateScheduleResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.ScheduleResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.resources.UpdateScheduleResource;
import pe.edu.upc.center.platform.schedules.interfaces.rest.transform.CreateScheduleCommandFromResourceAssembler;
import pe.edu.upc.center.platform.schedules.interfaces.rest.transform.ScheduleResourceFromEntityAssembler;
import pe.edu.upc.center.platform.schedules.interfaces.rest.transform.UpdateScheduleCommandFromResourceAssembler;
import pe.edu.upc.center.platform.usermanagement.domain.model.aggregates.Caregiver;

import java.util.HashMap;
import java.util.List;
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
    public ResponseEntity<Map<String, Object>> createPayment(@RequestBody CreatePaymentResource resource){
        var createPaymentCommand = CreatePaymentCommandFromResourceAssembler.toCommandFromResource(resource);

        PaymentIntent paymentIntent = paymentCommandService.handle(createPaymentCommand);

        var savedPayment = this.paymentRepository.findByStripePaymentId(paymentIntent.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("client_secret", paymentIntent.getClientSecret());
        response.put("id", paymentIntent.getId());
        response.put("amount", paymentIntent.getAmount());
        response.put("currency", paymentIntent.getCurrency());
        response.put("status", paymentIntent.getStatus());

        savedPayment.ifPresent(payment -> response.put("payment_id", payment.getId()));

        return ResponseEntity.ok(response);
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


    @GetMapping("/paymentIntent/{paymentIntentId}")
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
        response.put("status", intent.getStatus());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Get Payment by ID", description = "Retrieves a payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaymentResource> getPaymentById(@PathVariable Long id) {

        var getPaymentByIdQuery = new GetByIdQuery(id);
        var optionalPayment = this.paymentQueryService.handle(getPaymentByIdQuery);
        if (optionalPayment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(optionalPayment.get());

        return ResponseEntity.ok(paymentResource);
    }

    @PutMapping("/status/{paymentId}")
    @Operation(summary = "Update Payment Status", description = "Updates the status of a payment by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid payment ID or status"),
            @ApiResponse(responseCode = "404", description = "Payment not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaymentResource> updatePaymentStatus(@PathVariable("paymentId") Long paymentId, @RequestBody UpdatePaymentStatusResource resource) {
        var updatePaymentStatusCommand = UpdatePaymentStatusCommandFromResourceAssembler.toCommandFromResource(paymentId, resource);

        var paymentOptional = this.paymentCommandService.handle(updatePaymentStatusCommand);

        if (paymentOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var updatedPayment = paymentOptional.get();
        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(updatedPayment);

        return ResponseEntity.ok(paymentResource);
    }

    //TODO: QUITAR SI NO SE LLAMA EN FLUTTER
//    @GetMapping("/by-stripe-id/{stripePaymentId}")
//    @Operation(summary = "Get Payment by Stripe Payment ID", description = "Retrieves a payment by its Stripe Payment ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Payment retrieved successfully"),
//            @ApiResponse(responseCode = "404", description = "Payment not found"),
//            @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
//    public ResponseEntity<PaymentResource> getPaymentByStripeId(@PathVariable String stripePaymentId) {
//
//        var optionalPayment = this.paymentRepository.findByStripePaymentId(stripePaymentId);
//
//        if (optionalPayment.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        var paymentResource = PaymentResourceFromEntityAssembler.toResourceFromEntity(optionalPayment.get());
//
//        return ResponseEntity.ok(paymentResource);
//    }


  @GetMapping("/caregiver/{caregiverId}")
  @Operation(summary = "Get payments by caregiver ID", description = "Retrieves a list of payments associated with a caregiver ID")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
          @ApiResponse(responseCode = "404", description = "No payments found for the specified caregiver"),
          @ApiResponse(responseCode = "500", description = "Internal server error")
  })
    public ResponseEntity<List<PaymentResource>> getPaymentByCaregiverId(@PathVariable Long caregiverId) {

        var getPaymentByCaregiverIdQuery = new GetPaymentsByCaregiverIdQuery(caregiverId);
        var optionalPayment = this.paymentQueryService.handle(getPaymentByCaregiverIdQuery);

        var paymentResources = optionalPayment.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(paymentResources);
    }


    @GetMapping("/caregiver/{caregiverId}/status/{paymentStatus}")
    @Operation(summary = "Get payments by caregiver ID and Payment status", description = "Retrieves a list of payments associated with a caregiver ID and Payment status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payments retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "No payments found for the specified caregiver"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<PaymentResource>> getPaymentByCaregiverIdAndPaymentStatus(@PathVariable Long caregiverId, @PathVariable PaymentStatus paymentStatus) {

        var getPaymentsByCaregiverIdAndPaymentStatusQuery = new GetPaymentsByCaregiverIdAndPaymentStatusQuery(caregiverId, paymentStatus);
        var optionalPayment = this.paymentQueryService.handle(getPaymentsByCaregiverIdAndPaymentStatusQuery);

        var paymentResources = optionalPayment.stream()
                .map(PaymentResourceFromEntityAssembler::toResourceFromEntity)
                .toList();

        return ResponseEntity.ok(paymentResources);
    }




}