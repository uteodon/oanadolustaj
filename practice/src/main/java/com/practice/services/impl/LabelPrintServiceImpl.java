package com.practice.services.impl;

import com.practice.dto.LabelCreateDTO;
import com.practice.dto.LabelListDTO;
import com.practice.exceptions.FilePathException;
import com.practice.exceptions.LabelNotFoundException;
import com.practice.model.Label;
import com.practice.model.TSCLib;
import com.practice.repository.LabelRepository;
import com.practice.services.LabelPrintService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.practice.dto.LabelPathDTO;
import com.practice.dto.LabelDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelPrintServiceImpl implements LabelPrintService {
    @Autowired
    private LabelRepository labelRepository;

    @Override
    public void printLabelById(Long id, String printerName) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new LabelNotFoundException("Etiket bulunamadı: " + id));

        LabelPathDTO labelPathDTO = new LabelPathDTO();
        BeanUtils.copyProperties(label, labelPathDTO);

        String content;
        try {
            content = Files.readString(Paths.get(labelPathDTO.getFilePath()));
            //content = Files.readString(Paths.get(label.getFilePath())); //dto olmadan
        } catch (IOException e) {
            throw new FilePathException("Dosya okunamadı: " + label.getFilePath());
        }

        //dll ile yazıcıya gönderme
        TSCLib.INSTANCE.openport(printerName);
        TSCLib.INSTANCE.sendcommand(content);
        TSCLib.INSTANCE.closeport();
    }

    @Override
    public LabelPathDTO getFilePathById(Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new LabelNotFoundException("Etiket bulunamadı: " + id));
        return new LabelPathDTO(label.getFilePath());
    }

    @Override
    public List<LabelListDTO> getAllLabels() {

        List<Label> labels = labelRepository.findAll();

        List<LabelListDTO> labelListDTOs = labels.stream()
                .map(label -> {

                    Long id = label.getId();
                    String name = label.getName();
                    String description = label.getDescription();

                    return new LabelListDTO(id, name, description);
                })
                .collect(Collectors.toList());

        return labelListDTOs;
    }

    @Override
    public LabelDTO createLabel(LabelCreateDTO labelCreateDTO) {
        Label label = new Label();
        label.setName(labelCreateDTO.getName());
        label.setDescription(labelCreateDTO.getDescription());
        label.setFilePath(labelCreateDTO.getFilePath());

        Label savedLabel = labelRepository.save(label);
        return new LabelDTO(savedLabel.getId(), savedLabel.getName(),
                            savedLabel.getDescription(), savedLabel.getFilePath());
    }

    @Override
    public void testPrintConnection() {
        String tsplCommand = """
    SIZE 95 MM,40 MM
    SET RIBBON ON
    GAP 0 MM,0 MM
    DENSITY 7
    SPEED 2
    DIRECTION 1
    REFERENCE 10,10
    OFFSET 0
    CLS
    BOX 0,0,740,1040,5
    BARCODE 250,100,"39",100,0,90,2,5,"L0R3M1PSUM"
    QRCODE 630,450,H,4,A,90,J1,M2,"L0R3M1PSUM"
    TEXT 280,100,"ARIALBD.TTF",90,12,12,"Lorem Ipsum"
    PRINT 1
    """;

        TSCLib.INSTANCE.openport("TSC MB240");
        TSCLib.INSTANCE.sendcommand(tsplCommand);
        TSCLib.INSTANCE.closeport();
    }

    @Override
    public void deleteLabel(Long id) {
        if (!labelRepository.existsById(id)) {
            throw new LabelNotFoundException("Etiket bulunamadı: ID " + id);
        }
        labelRepository.deleteById(id);
    }

}
