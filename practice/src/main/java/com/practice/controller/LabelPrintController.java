package com.practice.controller;

import com.practice.dto.LabelCreateDTO;
import com.practice.dto.LabelDTO;
import com.practice.dto.LabelListDTO;
import com.practice.services.LabelPrintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.practice.dto.LabelPathDTO;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/labels")
public class LabelPrintController {
    @Autowired
    private LabelPrintService labelPrintService;

    @PostMapping(path="/create")
    public ResponseEntity<LabelDTO> createLabel(@RequestBody @Valid LabelCreateDTO labelCreateDTO){
        LabelDTO labelDTO = labelPrintService.createLabel(labelCreateDTO);
        return ResponseEntity.ok(labelDTO);
    }

    @DeleteMapping(path="/{id}/delete")
    public ResponseEntity<?> deleteLabel(@PathVariable Long id){
        labelPrintService.deleteLabel(id);
        return ResponseEntity.ok("Etiket silindi.");
    }

    @GetMapping(path="/printers")
    public List<String> getPrinters() {
        List<String> printerNames = new ArrayList<>();
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);

        for (PrintService printer : printServices) {
            printerNames.add(printer.getName());
        }

        return printerNames;
    }

    @PostMapping("/{id}/print")
    public ResponseEntity<?> printLabel(@PathVariable Long id, @RequestParam String printerName) {
            labelPrintService.printLabelById(id, printerName);
            return ResponseEntity.ok("Yazdırma isteği gönderildi.");
    }

    @PostMapping("/testprint")
    public ResponseEntity<?> printLabelTest() {
        labelPrintService.testPrintConnection();
        return ResponseEntity.ok("Yazdırma isteği gönderildi.");
    }

    @GetMapping("/{id}/filepath")
    public ResponseEntity<?> getFilePath(@PathVariable Long id) {
            LabelPathDTO dto = labelPrintService.getFilePathById(id);
            return ResponseEntity.ok(dto);
    }
    @GetMapping("/list")
    public ResponseEntity<List<LabelListDTO>> listLabels() {
        List<LabelListDTO> labels = labelPrintService.getAllLabels();
        return ResponseEntity.ok(labels);
    }
}

