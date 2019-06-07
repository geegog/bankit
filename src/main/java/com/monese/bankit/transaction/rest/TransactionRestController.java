package com.monese.bankit.transaction.rest;

import com.buildit.common.application.dto.MessageDTO;
import com.buildit.procurement.application.dto.POExtensionDTO;
import com.buildit.procurement.application.dto.PlantHireRequestDTO;
import com.buildit.procurement.application.dto.PurchaseOrderDTO;
import com.buildit.procurement.application.service.ProcurementService;
import com.buildit.procurement.domain.repository.PlantHireRequestRepository;
import com.buildit.rental.application.dto.PlantDTO;
import com.buildit.rental.application.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping("/api/procurements")
@CrossOrigin(origins = "*")
public class TransactionRestController {

    @Autowired
    RentalService rentalService;
    @Autowired
    ProcurementService procurementService;

    @Autowired
    PlantHireRequestRepository phrRepository;

    //@Autowired
    //PlantHireRequestResourceAssembler assembler;

    @GetMapping("/plants")
    @Secured({"ROLE_SITE"})
    public List<PlantDTO> findAvailablePlants(
            @RequestParam(name = "name", required = false) Optional<String> plantName,
            @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> startDate,
            @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> endDate
    ) {
        return rentalService.findAvailablePlants(plantName.get(), startDate.get(), endDate.get());

    }

    @GetMapping("/plant")
    @Secured({"ROLE_SITE", "ROLE_WORK"})
    public PlantDTO getPlant(
            @RequestParam(name = "id", required = false) Long id
    ) {
        return rentalService.getPlant(id);

    }

    @GetMapping("/all-phr")
    @Secured({"ROLE_SITE", "ROLE_WORK"})
    public List<PlantHireRequestDTO> getAllPlantHireRequests() {
        List<PlantHireRequestDTO> phrs = procurementService.allPHR();//.stream()
        //.filter(b -> b.getStatus() == PHStatus.PENDING)
        //.map(assembler::toResource)
        //.collect(Collectors.toList());

        return phrs;
        //linkTo(methodOn(ProcurementRestController.class).getAllPlantHireRequests()).withSelfRel());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPlantHireRequests(@PathVariable("id") Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(phr, HttpStatus.OK);
    }

    @PostMapping("/plant-hire-request")
    @Secured({"ROLE_SITE"})
    public ResponseEntity<?> createPlantHireRequest(@RequestBody PlantHireRequestDTO plantHireRequestDTO) throws Exception {
        PlantHireRequestDTO phr = procurementService.createPHR(plantHireRequestDTO);
        HttpHeaders headers = new HttpHeaders();
        //headers.setLocation(phr.get_xlink(IanaLinkRelations.SELF).toUri());

        return new ResponseEntity<>(phr, HttpStatus.CREATED);
//         return new ResponseEntity<>(
//                 new EntityModel<>(newlyCreatePODTO,
//                        linkTo(methodOn(SalesRestController.class).fetchPurchaseOrder(partialPODTO.get_id()))
//                                .withSelfRel(),
//                        linkTo(methodOn(SalesRestController.class).retrievePurchaseOrderExtensions(partialPODTO.get_id()))
//                         .withRel("extend")),
//                         headers, HttpStatus.CREATED);

    }

    @PatchMapping("plant-hire-request/{phId}/modify")
    @Secured({"ROLE_SITE"})
    public ResponseEntity<?> modifyPHR(@PathVariable Long phId, @RequestBody PlantHireRequestDTO plantHireRequestDTO) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(phId);
        System.out.println(phr);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", phId));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.modifyPHR(plantHireRequestDTO, phId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("plant-hire-request/{phId}/modify_po")
    @Secured({"ROLE_SITE"})
    public ResponseEntity<?> modifyPO(@PathVariable Long phId, @RequestBody PurchaseOrderDTO purchaseOrderDTO) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(phId);
        System.out.println(phr);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", phId));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.modifyPO(phId,purchaseOrderDTO), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @PatchMapping("plant-hire-request/{phId}/po_extend")
    @Secured({"ROLE_SITE"})
    public ResponseEntity<?> extendPO(@PathVariable Long phId, @RequestBody POExtensionDTO poExtensionDTO) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(phId);
        System.out.println(phr);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", phId));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.extendPO(phId,poExtensionDTO), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/plants/{id}/accept")
    @Secured({"ROLE_WORK"})
    public ResponseEntity<?> acceptPHR(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.acceptPHR(id), HttpStatus.OK);
        } catch (Exception ex) {
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/phr/{id}/po_accept")
    public ResponseEntity<?> acceptPO(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.acceptPO(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/plants/{id}/reject")
    @Secured({"ROLE_WORK"})
    public ResponseEntity<?> rejectPHR(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.rejectPHR(id), HttpStatus.OK);
        } catch (Exception ex) {
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/phr/{id}/po_reject")
    public ResponseEntity<?> rejectPO(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.rejectPO(id), HttpStatus.OK);
        } catch (Exception ex) {
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/plants/{id}/cancel")
    @Secured({"ROLE_SITE"})
    public ResponseEntity<?> cancelPHR(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        System.out.println(DAYS.between(LocalDate.now(), phr.getRentalPeriod().getStartDate()));
        if(DAYS.between(LocalDate.now(), phr.getRentalPeriod().getStartDate()) == 0 || DAYS.between(LocalDate.now(), phr.getRentalPeriod().getStartDate()) == 1) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request cannot be canceled! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.FORBIDDEN);
        }
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.cancelPHR(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @DeleteMapping("/phr/{id}/po_cancel")
    public ResponseEntity<?> cancelPO(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.cancelPO(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/phr/{id}/invoice")
    public ResponseEntity<?> receiveInvoice(@PathVariable Long id){
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.receiveInvoice(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/phr/{id}/invoice/approve")
    @Secured({"ROLE_SITE"})
    public ResponseEntity<?> approveInvoice(@PathVariable Long id){
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.approveInvoice(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/phr/{id}/invoice/reject")
    @Secured({"ROLE_SITE"})
    public ResponseEntity<?> rejectInvoice(@PathVariable Long id){
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.rejectInvoice(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/phr/{id}/dispatched")
    public ResponseEntity<?> dispatchedPO(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.dispatchPO(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/phr/{id}/returned")
    public ResponseEntity<?> returnedPO(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.returnPO(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/phr/{id}/payment-notification")
    public ResponseEntity<?> paymentNotification(@PathVariable Long id) {
        PlantHireRequestDTO phr = procurementService.fetchPHR(id);
        if (phr == null) {
            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setText(String.format("Plant Hire Request not found! (PHR id: %d)", id));
            return new ResponseEntity<>(messageDTO, HttpStatus.NOT_FOUND);
        }
        try {
            return new ResponseEntity<>(procurementService.paymentNotification(id), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            // Add code to Handle Exception (Change return null with the solution)
            return new ResponseEntity<>(phr, HttpStatus.NOT_MODIFIED);
        }
    }
}
