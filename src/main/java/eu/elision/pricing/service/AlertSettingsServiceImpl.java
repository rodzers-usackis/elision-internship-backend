package eu.elision.pricing.service;

import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertSettingsDto;
import eu.elision.pricing.exceptions.NotFoundException;
import eu.elision.pricing.mapper.AlertSettingsMapper;
import eu.elision.pricing.repository.AlertSettingsRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Implementation of {@link AlertSettingsService}.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AlertSettingsServiceImpl implements AlertSettingsService {

    private final AlertSettingsRepository alertSettingRepository;
    private final AlertSettingsMapper alertSettingsMapper;

    @Override
    public AlertSettingsDto getAlertSettings(User user) {
        AlertSettings alertSettings = user.getAlertSettings();

        return alertSettingsMapper.domainToDto(alertSettings);
    }

    @Override
    public AlertSettings createAlertSettings(AlertSettings alertSettings) {
        alertSettingRepository.save(alertSettings);

        return alertSettings;
    }


    @Transactional
    @Override
    public AlertSettingsDto getNotificationSettings(User user) {
        AlertSettings alertSettings =
            alertSettingRepository.findAlertSettingsByUser_Id(user.getId());

        log.debug(">>> getting notifications settings for client company id: {}", user.getClientCompany().getId());

        if (alertSettings == null) {
            throw new EntityNotFoundException("Notification settings not found");
        }
        return alertSettingsMapper.domainToDto(alertSettings);

    }

    @Override
    public void updateNotificationSettings(User user,
                                           AlertSettingsDto alertSettingsDto) {

        AlertSettings alertSettings = alertSettingRepository.findAlertSettingsByUser_Id(user.getId());

        if (alertSettings == null) {
            throw new NotFoundException("Settings not found");
        }

//        alertSettings.setEmailAddress(alertSettingsDto.getEmailAddress());
        alertSettings.setNotifyViaEmail(alertSettingsDto.isNotifyViaEmail());
        alertSettings.setAlertsActive(alertSettingsDto.isAlertsActive());
//        alertSettings.setAlertStorageDuration(alertSettingsDto.getAlertStorageDuration());

        alertSettingRepository.save(alertSettings);

    }

}
