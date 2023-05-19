package eu.elision.pricing.service;

import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertSettingsDto;
import eu.elision.pricing.mapper.AlertSettingsMapper;
import eu.elision.pricing.repository.AlertSettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


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


}
