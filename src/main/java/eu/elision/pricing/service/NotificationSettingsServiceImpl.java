package eu.elision.pricing.service;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.RetailerCompanyDto;
import eu.elision.pricing.dto.notifications.AlertRuleDto;
import eu.elision.pricing.dto.notifications.NotificationSettingsWithAlertRulesDto;
import eu.elision.pricing.dto.notifications.NotificationSettingsDto;
import eu.elision.pricing.mapper.AlertRuleMapper;
import eu.elision.pricing.mapper.NotificationSettingsMapper;
import eu.elision.pricing.repository.AlertRuleRepository;
import eu.elision.pricing.repository.NotificationSettingsRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NotificationSettingsServiceImpl implements NotificationSettingsService {

    private final NotificationSettingsRepository notificationSettingsRepository;
    private final RetailerCompanyRepository retailerCompanyRepository;
    private final AlertRuleRepository alertRuleRepository;
    private final ProductRepository productRepository;
    private final NotificationSettingsMapper notificationSettingsMapper;
    private final AlertRuleMapper alertRuleMapper;

    @Transactional
    @Override
    public NotificationSettingsWithAlertRulesDto getNotificationSettings(User user) {
        AlertSettings alertSettings =
            notificationSettingsRepository.findByClientCompany_Id(user.getClientCompany().getId());

        log.debug(">>> getting notifications settings for client company id: {}", user.getClientCompany().getId());

        if (alertSettings == null) {
            throw new EntityNotFoundException("Notification settings not found");
        }
        return notificationSettingsMapper.domainToDto(alertSettings);

    }

    @Override
    public void updateNotificationSettings(User user,
                                           NotificationSettingsDto notificationSettingsDto) {

        user.getClientCompany().getAlertSettings()
            .setNotifyViaEmail(notificationSettingsDto.isNotifyViaEmail());
        notificationSettingsRepository.save(user.getClientCompany().getAlertSettings());

    }



}