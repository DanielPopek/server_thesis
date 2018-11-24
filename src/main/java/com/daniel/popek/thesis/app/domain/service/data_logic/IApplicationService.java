package com.daniel.popek.thesis.app.domain.service.data_logic;

import com.daniel.popek.thesis.app.domain.DTO.design.ApplicationDTO;
import org.springframework.stereotype.Service;

import java.util.List;

/*
This service is used to extract/update Application entities from/in database
 */
@Service
public interface IApplicationService {

    public void saveApplication(ApplicationDTO dto,String header);
    public void deleteApplication(ApplicationDTO dto);
    public void deleteApplicationByToken(String token);
    public List<ApplicationDTO> getAllByDesignerId(Integer id);

    List<ApplicationDTO> getAllByDesignerHash(String hash);

    void editApplication(ApplicationDTO applicationDTO);
}
