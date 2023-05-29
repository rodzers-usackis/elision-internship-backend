package eu.elision.pricing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import eu.elision.pricing.domain.AlertRule;
import eu.elision.pricing.domain.AlertSettings;
import eu.elision.pricing.domain.PriceComparisonType;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.domain.Role;
import eu.elision.pricing.domain.User;
import eu.elision.pricing.dto.AlertRuleToCreateDto;
import eu.elision.pricing.dto.ProductDto;
import eu.elision.pricing.dto.RetailerCompanyDto;
import eu.elision.pricing.exceptions.NotFoundException;
import eu.elision.pricing.mapper.AlertRuleMapper;
import eu.elision.pricing.repository.AlertRuleRepository;
import eu.elision.pricing.repository.ProductRepository;
import eu.elision.pricing.repository.RetailerCompanyRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class AlertRuleServiceImplTests {

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private RetailerCompanyRepository retailerCompanyRepository;

    @MockBean
    private AlertRuleRepository alertRuleRepository;

    @MockBean
    private AlertRuleMapper alertRuleMapper;

    @Autowired
    private AlertRuleServiceImpl alertRuleService;

    @Captor
    private ArgumentCaptor<AlertRule> alertRuleCaptor;

    @Test
    void alertRuleWithNoRetailerCompaniesCreatedCorrectly() {

        AlertSettings alertSettings = AlertSettings.builder()
            .notifyViaEmail(true)
            .alertsActive(true)
            .id(UUID.randomUUID())
            .build();

        User user = User.builder()
            .password("password")
            .email("email")
            .role(Role.CLIENT)
            .alertSettings(alertSettings)
            .build();

        alertSettings.setUser(user);

        ProductDto productDto = ProductDto.builder()
            .id(UUID.randomUUID())
            .build();

        Product product = Product.builder()
            .id(productDto.getId())
            .name("name")
            .ean("test ean")
            .manufacturerCode("test manufacturer code")
            .description("test description")
            .build();

        AlertRuleToCreateDto alertRuleDto = AlertRuleToCreateDto.builder()
            .priceComparisonType(PriceComparisonType.LOWER)
            .priceThreshold(1000.0)
            .product(productDto)
            .build();

        AlertRule alertRule = AlertRule.builder()
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(product)
            .alertSettings(alertSettings)
            .build();


        AlertRule alertRuleSaved = AlertRule.builder()
            .id(UUID.randomUUID())
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(product)
            .alertSettings(alertSettings)
            .build();

        when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(product));
        when(alertRuleRepository.save(alertRule)).thenReturn(alertRuleSaved);
        when(alertRuleMapper.domainToDto(alertRuleSaved)).thenReturn(null);

        alertRuleService.createAlertRule(user, alertRuleDto);

        verify(productRepository).findById(productDto.getId());
        verify(retailerCompanyRepository, times(0)).findAllById(any());
        verify(alertRuleRepository).save(alertRule);


    }

    @Test
    void alertRuleWithRetailerCompaniesCreatedCorrectly() {


        AlertSettings alertSettings = AlertSettings.builder()
            .notifyViaEmail(true)
            .alertsActive(true)
            .id(UUID.randomUUID())
            .build();

        User user = User.builder()
            .password("password")
            .email("email")
            .role(Role.CLIENT)
            .alertSettings(alertSettings)
            .build();

        alertSettings.setUser(user);

        ProductDto productDto = ProductDto.builder()
            .id(UUID.randomUUID())
            .build();

        Product product = Product.builder()
            .id(productDto.getId())
            .name("name")
            .ean("test ean")
            .manufacturerCode("test manufacturer code")
            .description("test description")
            .build();

        RetailerCompanyDto retailerCompanyDto = RetailerCompanyDto.builder()
            .id(UUID.randomUUID())
            .build();

        RetailerCompanyDto retailerCompanyDto2 = RetailerCompanyDto.builder()
            .id(UUID.randomUUID())
            .build();

        RetailerCompany retailerCompany = RetailerCompany.builder()
            .id(retailerCompanyDto.getId())
            .name("name1")
            .website("website1")
            .build();

        RetailerCompany retailerCompany2 = RetailerCompany.builder()
            .id(retailerCompanyDto2.getId())
            .name("name2")
            .website("website2")
            .build();

        AlertRuleToCreateDto alertRuleDto = AlertRuleToCreateDto.builder()
            .priceComparisonType(PriceComparisonType.LOWER)
            .priceThreshold(1000.0)
            .product(productDto)
            .retailerCompanies(List.of(retailerCompanyDto, retailerCompanyDto2))
            .build();

        AlertRule alertRule = AlertRule.builder()
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(product)
            .alertSettings(alertSettings)
            .build();


        AlertRule alertRuleSaved = AlertRule.builder()
            .id(UUID.randomUUID())
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(product)
            .alertSettings(alertSettings)
            .retailerCompanies(List.of(retailerCompany, retailerCompany2))
            .build();

        when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(product));
        when(retailerCompanyRepository.findAllById(
            alertRuleDto.getRetailerCompanies().stream().map(RetailerCompanyDto::getId).collect(
                Collectors.toList()))).thenReturn(
            List.of(retailerCompany, retailerCompany2));
        when(alertRuleRepository.save(alertRule)).thenReturn(alertRuleSaved);
        when(alertRuleMapper.domainToDto(alertRuleSaved)).thenReturn(null);

        alertRuleService.createAlertRule(user, alertRuleDto);

        verify(productRepository).findById(productDto.getId());
        verify(retailerCompanyRepository, times(1)).findAllById(any());
        verify(alertRuleRepository).save(alertRule);
        verify(alertRuleMapper).domainToDto(alertRuleCaptor.capture());

        AlertRule capturedAlertRule = alertRuleCaptor.getValue();

        assertEquals(2, capturedAlertRule.getRetailerCompanies().size());
        assertTrue(
            capturedAlertRule.getRetailerCompanies().stream().map(RetailerCompany::getId).collect(
                Collectors.toList()).contains(retailerCompanyDto.getId()));
        assertTrue(
            capturedAlertRule.getRetailerCompanies().stream().map(RetailerCompany::getId).collect(
                Collectors.toList()).contains(retailerCompanyDto2.getId()));
        assertEquals(alertSettings.getId(), capturedAlertRule.getAlertSettings().getId());
        assertEquals(product.getId(), capturedAlertRule.getProduct().getId());
        assertEquals(alertRuleDto.getPriceComparisonType(),
            capturedAlertRule.getPriceComparisonType());


    }

    @Test
    void creatingAlertRuleThrowsWhenRetailerCompanyNotFound() {


        AlertSettings alertSettings = AlertSettings.builder()
            .notifyViaEmail(true)
            .alertsActive(true)
            .id(UUID.randomUUID())
            .build();

        User user = User.builder()
            .password("password")
            .email("email")
            .role(Role.CLIENT)
            .alertSettings(alertSettings)
            .build();

        alertSettings.setUser(user);

        ProductDto productDto = ProductDto.builder()
            .id(UUID.randomUUID())
            .build();

        Product product = Product.builder()
            .id(productDto.getId())
            .name("name")
            .ean("test ean")
            .manufacturerCode("test manufacturer code")
            .description("test description")
            .build();

        RetailerCompanyDto retailerCompanyDto = RetailerCompanyDto.builder()
            .id(UUID.randomUUID())
            .build();

        RetailerCompanyDto retailerCompanyDto2 = RetailerCompanyDto.builder()
            .id(UUID.randomUUID())
            .build();

        RetailerCompany retailerCompany = RetailerCompany.builder()
            .id(retailerCompanyDto.getId())
            .name("name1")
            .website("website1")
            .build();

        RetailerCompany retailerCompany2 = RetailerCompany.builder()
            .id(retailerCompanyDto2.getId())
            .name("name2")
            .website("website2")
            .build();

        AlertRuleToCreateDto alertRuleDto = AlertRuleToCreateDto.builder()
            .priceComparisonType(PriceComparisonType.LOWER)
            .priceThreshold(1000.0)
            .product(productDto)
            .retailerCompanies(List.of(retailerCompanyDto, retailerCompanyDto2))
            .build();

        AlertRule alertRule = AlertRule.builder()
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(product)
            .alertSettings(alertSettings)
            .build();


        AlertRule alertRuleSaved = AlertRule.builder()
            .id(UUID.randomUUID())
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(product)
            .alertSettings(alertSettings)
            .retailerCompanies(List.of(retailerCompany, retailerCompany2))
            .build();


        when(productRepository.findById(productDto.getId())).thenReturn(Optional.of(product));
        when(retailerCompanyRepository.findAllById(
            alertRuleDto.getRetailerCompanies().stream().map(RetailerCompanyDto::getId).collect(
                Collectors.toList()))).thenReturn(
            List.of(retailerCompany));


        assertThrows(
            NotFoundException.class, () -> alertRuleService.createAlertRule(user, alertRuleDto));

    }

    @Test
    void creatingAlertRuleThrowsWhenProductNotFound() {


        AlertSettings alertSettings = AlertSettings.builder()
            .notifyViaEmail(true)
            .alertsActive(true)
            .id(UUID.randomUUID())
            .build();

        User user = User.builder()
            .password("password")
            .email("email")
            .role(Role.CLIENT)
            .alertSettings(alertSettings)
            .build();

        alertSettings.setUser(user);

        ProductDto productDto = ProductDto.builder()
            .id(UUID.randomUUID())
            .build();

        Product product = Product.builder()
            .id(productDto.getId())
            .name("name")
            .ean("test ean")
            .manufacturerCode("test manufacturer code")
            .description("test description")
            .build();

        RetailerCompanyDto retailerCompanyDto = RetailerCompanyDto.builder()
            .id(UUID.randomUUID())
            .build();

        RetailerCompanyDto retailerCompanyDto2 = RetailerCompanyDto.builder()
            .id(UUID.randomUUID())
            .build();

        RetailerCompany retailerCompany = RetailerCompany.builder()
            .id(retailerCompanyDto.getId())
            .name("name1")
            .website("website1")
            .build();

        RetailerCompany retailerCompany2 = RetailerCompany.builder()
            .id(retailerCompanyDto2.getId())
            .name("name2")
            .website("website2")
            .build();

        AlertRuleToCreateDto alertRuleDto = AlertRuleToCreateDto.builder()
            .priceComparisonType(PriceComparisonType.LOWER)
            .priceThreshold(1000.0)
            .product(productDto)
            .retailerCompanies(List.of(retailerCompanyDto, retailerCompanyDto2))
            .build();

        AlertRule alertRule = AlertRule.builder()
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(product)
            .alertSettings(alertSettings)
            .build();


        AlertRule alertRuleSaved = AlertRule.builder()
            .id(UUID.randomUUID())
            .priceComparisonType(alertRuleDto.getPriceComparisonType())
            .price(alertRuleDto.getPriceThreshold())
            .product(product)
            .alertSettings(alertSettings)
            .retailerCompanies(List.of(retailerCompany, retailerCompany2))
            .build();

        when(productRepository.findById(productDto.getId())).thenReturn(Optional.empty());
        assertThrows(
            NotFoundException.class, () -> alertRuleService.createAlertRule(user, alertRuleDto));


    }


}