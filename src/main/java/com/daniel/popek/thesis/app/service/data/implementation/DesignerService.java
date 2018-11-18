package com.daniel.popek.thesis.app.service.data.implementation;

import com.daniel.popek.thesis.app.model.DTO.authentication.*;
import com.daniel.popek.thesis.app.model.entities.Designer;
import com.daniel.popek.thesis.app.repository.DesignerRepository;
import com.daniel.popek.thesis.app.service.data.IDesignerService;
import com.daniel.popek.thesis.app.service.utils.IEmailSenderService;
import com.daniel.popek.thesis.app.service.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class DesignerService implements IDesignerService {

    @Autowired
    DesignerRepository designerRepository;

    @Autowired
    IEmailSenderService emailSenderService;

    @Override
    public Pair<CustomResponseDTO, HttpStatus> login(String login, String password) {
        CustomResponseDTO response = new CustomResponseDTO();
        HttpStatus status;
        Designer userByEmail = this.designerRepository.findByEmail(login);
        if (userByEmail!=null) {
            String hash = userByEmail.getPassword();
            String salt = userByEmail.getSalt();
            System.out.println(hash);
            System.out.println(PasswordUtils.encryptPassword(password, salt));
            if (hash.equals(PasswordUtils.encryptPassword(password, salt))) {
                if (!userByEmail.getActive()) {
                    response = new UserNotActiveLoginResponseDTO();
                    status = HttpStatus.PRECONDITION_FAILED;
                } else {
                    response = new LoginSuccessfulResponseDTO(userByEmail.getApiKey());
                    status = HttpStatus.OK;
                }
            } else {
                response = new WrongLoginDataResponseDTO();
                status = HttpStatus.UNAUTHORIZED;
            }
        } else {
            response = new WrongLoginDataResponseDTO();
            status = HttpStatus.UNAUTHORIZED;
        }
        return Pair.of(response, status);
    }

    @Override
    public Pair<CustomResponseDTO, HttpStatus> register(RegisterRequestBodyDTO registerRequestBody) {
        CustomResponseDTO response = new CustomResponseDTO();
        HttpStatus status;

        Designer userByEmail = this.designerRepository.findByEmail(registerRequestBody.getEmail());
        if (userByEmail!=null) {

            response = new RegisterWrongEmailResponseDTO();
            status = HttpStatus.BAD_REQUEST;
            return Pair.of(response, status);
        }

        Pair<String, String> password = PasswordUtils.decryptPassword(registerRequestBody.getPassword());
        Designer userToSave = new Designer();

        userToSave.setEmail(registerRequestBody.getEmail());
        userToSave.setActive(false);
        userToSave.setApiKey(PasswordUtils.generateAccessToker());
        userToSave.setActivationCode(PasswordUtils.generateActivationCode());
        userToSave.setPassword(password.getSecond());
        userToSave.setSalt(password.getFirst());

        Designer saved=designerRepository.save(userToSave);
        emailSenderService.sendActivationLinkByEmail(saved.getId(), registerRequestBody.getEmail(), saved.getActivationCode());
        response = new RegisterSuccessfulResponseDTO();
        status = HttpStatus.OK;

        return Pair.of(response, status);
    }

    @Override
    public Pair<CustomResponseDTO, HttpStatus> activateUser(Integer id, String activationLink) {
        Designer userById = this.designerRepository.findById(id.intValue());
        if (userById!=null && !userById.getActive()) {
            if (userById.getActivationCode().equals(activationLink)) {
                Designer user = userById;
                user.setActive(true);
                designerRepository.save(user);
                CustomResponseDTO response = new ActivationSuccessfulResponseDTO();
                HttpStatus status=HttpStatus.OK;
                return Pair.of(response, status);
            } else {
                CustomResponseDTO response = new WrongActivationLinkResponseDTO();
                HttpStatus status=HttpStatus.BAD_REQUEST;
                return Pair.of(response, status);
            }
        } else {
            CustomResponseDTO response = new NoUserToActivateResponseDTO();
            HttpStatus status=HttpStatus.PRECONDITION_FAILED;
            return Pair.of(response, status);
        }
    }
}
