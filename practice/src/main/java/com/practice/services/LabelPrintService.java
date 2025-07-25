package com.practice.services;

import com.practice.dto.LabelCreateDTO;
import com.practice.dto.LabelDTO;
import com.practice.dto.LabelListDTO;
import com.practice.dto.LabelPathDTO;

import java.util.List;

public interface LabelPrintService {
    void printLabelById(Long id, String printerName);
    LabelPathDTO getFilePathById(Long id);
    List<LabelListDTO> getAllLabels();
    LabelDTO createLabel(LabelCreateDTO labelCreateDTO);
    public void testPrintConnection();
    void deleteLabel(Long id);
}
