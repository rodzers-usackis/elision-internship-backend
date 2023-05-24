package eu.elision.pricing.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import eu.elision.pricing.domain.Price;
import eu.elision.pricing.domain.PriceScrapingConfig;
import eu.elision.pricing.domain.Product;
import eu.elision.pricing.domain.ProductCategory;
import eu.elision.pricing.domain.RetailerCompany;
import eu.elision.pricing.repository.PriceRepository;
import eu.elision.pricing.repository.PriceScrapingConfigRepository;
import java.io.IOException;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest()
class ScraperServiceImplMockingTests {

    @MockBean
    private PriceScrapingConfigRepository priceScrapingConfigRepository;

    @MockBean
    private PriceRepository priceRepository;

    @MockBean
    private HttpRequestService httpRequestService;

    @Autowired
    private ScraperService scraperService;


    @SuppressWarnings({"checkstyle:Indentation", "checkstyle:FileTabCharacter",
        "checkstyle:LineLength"})
    @Test
    void webScrapingWorksCorrectly() throws IOException {

        Product p1 = Product.builder()
            .name("iPhone 14 Pro Max")
            .id(UUID.randomUUID())
            .category(ProductCategory.CONSUMER_ELECTRONICS)
            .build();

        RetailerCompany rc1 = RetailerCompany.builder()
            .name("Alternate.be")
            .id(UUID.randomUUID())
            .build();

        PriceScrapingConfig psc1 = PriceScrapingConfig.builder()
            .commaSeparatedDecimal(true)
            .cssSelector("span.price")
            .product(p1)
            .retailerCompany(rc1)
            .active(true)
            .url(
                "invalid url here because the request should be mocked anyway https://www.alternate.be/Apple/iPhone-14-Pro-Max-smartphone/html/product/1866555")
            .build();

        String alternateiPhone14Page;

        {
            alternateiPhone14Page = """
                <div id="product-top" class="card p-3 mb-3 ">
                              <div class="row">
                                
                                <div class="col-12 mb-2">
                              <div class="badge bg-black open-promo-dropdown cursor-pointer mr-2 text-uppercase">
                                <span>Gratis verzending</span>
                              </div>
                                </div>
                                
                                <div class="col-12 my-0 d-flex justify-content-between">
                                  <div class="product-name">
                                    <h1>
                                
                                      <span>Apple</span>
                                
                                      <span>iPhone 14 Pro Max smartphone</span>
                                        <small class="product-name-sub d-block font-weight-normal">(Zwart, 512GB, iOS)</small>
                                    </h1>
                                  </div>
                                
                                  <a class="d-none d-md-block product-manufacturer-logo" href="/listing.xhtml?q=Apple&amp;filterManufacturer=Apple" title="Alles van Apple bekijken">
                                      Apple
                                   \s
                                  </a>
                                </div>
                              </div>
                                
                              <div class="row">
                                <div class="col-12 col-md-6 text-center" id="product-top-left">
                    <div class="row position-relative" id="product-image-carousel">
                      <span class="pinned-award">
                       \s
                      </span>
                      <div class="col-12 text-center">
                        <div class="mt-3" id="product-pic">
                          <div class="d-md-none">
                            <div>
                              <div class="swiper-button-prev button-details-prev swiper-button-disabled swiper-button-lock" tabindex="-1" role="button" aria-label="Previous slide" aria-controls="product-gallery-big-slider" aria-disabled="true"></div>
                            </div>
                          </div>
                                
                          <div class="swiper product-gallery-big swiper-initialized swiper-horizontal swiper-pointer-events swiper-watch-progress swiper-backface-hidden">
                            <div id="product-gallery-big-slider" class="swiper-wrapper" aria-live="polite" style="transition-duration: 0ms; transform: translate3d(0px, 0px, 0px); height: auto;">
                            <div class="swiper-slide swiper-slide-visible swiper-slide-active" role="group" aria-label="1 / 1" style="width: 656px;">
                              <div class="position-relative">
                                <img src="/p/600x600/5/5/Apple_iPhone_14_Pro_Max_smartphone@@1866555.jpg" alt="Apple iPhone 14 Pro Max smartphone Zwart, 512GB, iOS" class="img-fluid cursor-magnify swiper-lazy lazy-img product-img swiper-lazy-loaded" width="60" height="60">
                              </div>
                            </div>
                        </div>
                          <span class="swiper-notification" aria-live="assertive" aria-atomic="true"></span></div>
                          <div class="d-md-none">
                            <div>
                              <div class="swiper-button-next button-details-next swiper-button-disabled swiper-button-lock" tabindex="-1" role="button" aria-label="Next slide" aria-controls="product-gallery-big-slider" aria-disabled="true"></div>
                            </div>
                          </div>
                        </div>
                                
                                
                        <div id="swiper-images-big" style="display:none;">
                            <div class="swiper-slide">
                              <div class="position-relative">
                                <img src="data:image/gif;base64,R0lGODdhAQABAIAAAP///////ywAAAAAAQABAAACAkQBADs=" data-src="/p/600x600/5/5/Apple_iPhone_14_Pro_Max_smartphone@@1866555.jpg" alt="Apple iPhone 14 Pro Max smartphone Zwart, 512GB, iOS" class="img-fluid cursor-magnify swiper-lazy lazy-img product-img" width="60" height="60">
                              </div>
                            </div>
                        </div>
                                
                        <div id="swiper-videos-big" class="d-none">
                        </div>
                                
                        <small>Afbeeldingen kunnen van het origineel afwijken.</small>
                                
                        <div class="row align-items-center my-2" id="product-pics">
                          <div class="col-12 col-md-2 d-none d-md-block">
                          </div>
                          <div class="col-md-8 d-none d-md-block">
                            <div class="row">
                              <div class="col-2 d-flex justify-content-center align-items-center">
                                <div class="swiper-button-prev button-details-prev swiper-button-disabled swiper-button-lock" tabindex="-1" role="button" aria-label="Previous slide" aria-controls="product-gallery-big-slider" aria-disabled="true"></div>
                              </div>
                              <div class="col-8">
                                <div class="product-gallery-small swiper">
                                  <div id="product-gallery-small-slider" class="swiper-wrapper"></div>
                                </div>
                              </div>
                              <div class="col-2 d-flex justify-content-center align-items-center">
                                <div class="swiper-button-next button-details-next swiper-button-disabled swiper-button-lock" tabindex="-1" role="button" aria-label="Next slide" aria-controls="product-gallery-big-slider" aria-disabled="true"></div>
                              </div>
                            </div>
                          </div>
                          <div class="col-12 col-md-2 d-none d-md-block">
                          </div>
                        </div>
                                
                        <div id="swiper-images-small" class="d-none">
                            <div class="swiper-slide cursor-pointer">
                              <img src="/p/160x160/5/5/Apple_iPhone_14_Pro_Max_smartphone@@1866555.jpg" data-src="/p/160x160/5/5/Apple_iPhone_14_Pro_Max_smartphone@@1866555.jpg" alt="Apple iPhone 14 Pro Max smartphone Zwart, 512GB, iOS" class="img-fluid product-img swiper-lazy lazy-img border rounded w-100" width="16" height="16">
                            </div>
                        </div>
                                
                        <div id="swiper-videos-small" class="d-none">
                        </div>
                                
                      </div>
                    </div>
                                </div>
                                
                                <div class="col-12 col-md-6" id="product-top-right">
                    <div class="row py-3 border-bottom">
                                
                      <div class="col">
                                
                        <div class="row align-items-end text-center text-md-left">
                          <div class="col-12 col-md-auto"><span class="price ">€ 1.739,00</span>
                          </div>
                          <div class="col-12 col-md-auto">
                            <div class="d-flex flex-column justify-content-center justify-content-md-start base-price">
                                <div id="shipping-price">
                                  <span class="vat-and-shipping-costs"><span class="mr-1">Inclusief btw en Recupelbijdrage, exclusief</span><span class=" ">€ 0,00</span><span class="ml-1">verzendkosten.</span>
                          </span>
                                </div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="row border-bottom">
                                
                      <a href="#product-details-tab" id="product-details-anchor" class="col p-3 d-flex flex-column align-items-center text-dark justify-content-between" title="Details">
                        <img class="d-block mx-auto" src="/mobile/javax.faces.resource/icons/Details.svg.xhtml?ln=pix" alt="Details" width="25" height="25">
                        <span>
                          <p class="text-center mb-0">Details</p>
                        </span>
                      </a>
                        <div class="col p-3 d-flex flex-column align-items-center justify-content-between">
                              <span class="d-flex align-items-center text-nowrap">
                              </span><a href="/addRating.xhtml?p=1866555" title="Je hebt dit product nog niet gereviewd" class="text-decoration-none"><span class="ratingstars"><i class="far fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i><i class="far fa-star"></i></span></a><a href="/addRating.xhtml?p=1866555" title="Je hebt dit product nog niet gereviewd" class="text-center">Je hebt dit product nog niet gereviewd</a>
                        </div>
                                
                    </div>
                      <div class="row justify-content-center py-3 border-bottom" id="promo-section">
                        <div class="col-12">
                                
                          <div class="dropdown-center dropdown-scrollable btn-group d-grid">
                            <button class="btn btn-yellow dropdown-toggle" type="button" id="product-promotions" data-bs-toggle="dropdown" data-bs-auto-close="outside" aria-haspopup="true" aria-expanded="false">
                              <i class="fas fa-cog fa-spin d-none"></i>
                              <span class="btnText">
                                1 actie beschikbaar
                              </span>
                            </button>
                            <div class="dropdown-menu shadow p-2" aria-labelledby="product-promotions">
                                  <div>
                                    <div class="font-weight-bold">Gratis verzending</div><span class="d-block">Geniet bij aankoop van dit product van gratis verzending.</span>
                                  </div>
                            </div>
                          </div>
                                
                        </div>
                      </div>
                <form id="add-to-cart-form" name="add-to-cart-form" method="post" action="/mobile/details.xhtml" class="tp-form" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="add-to-cart-form" value="add-to-cart-form">
                                
                                      <div class="row">
                                        <div class="col-12 py-2">
                                          <div class="row gx-2 align-items-center">
                                            <div class="col d-grid"><select id="add-to-cart-form:warrantyextension" name="add-to-cart-form:warrantyextension" class="form-select btn btn-outline-gray-light" size="1">	<option value="-1" selected="selected">Geen garantieverlenging</option>
                	<option value="1178141">Garantieverlenging + 1 jaar  (+ € 169,90)</option>
                	<option value="1178151">Garantieverlenging + 2 jaar  (+ € 219,90)</option>
                </select>
                                            </div>
                                            <div class="col-auto">
                                              <span class="icon-click-size cursor-pointer" data-bs-toggle="popover" data-bs-content="<a href=&quot;/content.xhtml?c=872d5d1c-cb6e-4615-8cde-5aac5b2d9c13&quot; target=&quot;_warranty&quot; class=&quot;d-block font-weight-bold text-dark mt-1&quot;>Meer informatie</a>" data-bs-placement="top" tabindex="0">
                                                <img src="/mobile/javax.faces.resource/icons/Info.svg.xhtml?ln=pix" width="16" height="16" class="img-fluid" alt="Informatie">
                                              </span>
                                            </div>
                                          </div>
                                        </div>
                                      </div>
                      <div class="row border-bottom">
                        <div class="col-12 p-3 text-center">
                          <span class="d-flex justify-content-center align-items-center">
                              <b style="color: #009824;">Op voorraad
                              </b>
                          </span>
                        </div>
                      </div><div id="add-to-cart-form:add-to-cart-section" class="row">
                                        <div class="col-12"><div class="d-grid border-bottom"><input id="add-to-cart-form:tle-details-add-to-cart-button:vt" type="hidden" name="add-to-cart-form:tle-details-add-to-cart-button:vt" value="6187f8a258f5827727189c9c7195a308"><input id="add-to-cart-form:tle-details-add-to-cart-button:ft" type="hidden" name="add-to-cart-form:tle-details-add-to-cart-button:ft" value="412d90f2a08a28a44498013a32756f92"><input id="add-to-cart-form:tle-details-add-to-cart-button:rt" type="hidden" name="add-to-cart-form:tle-details-add-to-cart-button:rt" value="1500">
                                
                      <a href="#" class="add-to-cart details-cart-button btn btn-primary btn-lg d-flex justify-content-center tp-button my-3" onclick="global.checkTarpit(this, event)" title="Leg in de winkelwagen">
                        <i class="fas fa-cog fa-spin d-none"></i>
                        <span class="cart-btn-text">
                          <img src="/mobile/javax.faces.resource/icons/Warenkorb-white.svg.xhtml?ln=pix" alt="Leg in de winkelwagen" width="20" height="20"><span class="mx-2">Leg in de winkelwagen</span>
                        </span>
                      </a><input id="add-to-cart-form:tle-details-add-to-cart-button:add-to-cart-btn" type="submit" name="add-to-cart-form:tle-details-add-to-cart-button:add-to-cart-btn" value="" class="d-none add-to-cart" onclick="mojarra.ab(this,event,'action','@form','@form messages tle-add-to-cart-modal:add-to-cart-modal-dialog j_idt1053:add-to-cart-form',{'p':'1866555'});return false"></div>
                                        </div></div>
                    <nav class="fixed-top navbar shadow py-3" id="details-fixed-header" style="height: 102.35px; opacity: 1; display: none;">
                      <div class="container">
                        <div class="row align-items-center w-100">
                          <div class="col-8 text-truncate">
                            <div class="d-flex">
                              <span class="position-relative px-5">
                      <img src="/p/160x160/5/5/Apple_iPhone_14_Pro_Max_smartphone@@1866555.jpg" alt="Apple iPhone 14 Pro Max smartphone Zwart, 512GB, iOS" class="productPicture img-fluid m-auto  " width="160" height="160">
                              </span>
                              <div class="pl-2">
                                <strong class="product-name">Apple iPhone 14 Pro Max smartphone</strong>
                                  <span class="product-name-sub d-block">(Zwart, 512GB, iOS)</span>
                              </div>
                            </div>
                          </div>
                          <div class="col-4"><div class="d-grid "><input id="add-to-cart-form:j_idt706:j_idt727:vt" type="hidden" name="add-to-cart-form:j_idt706:j_idt727:vt" value="6187f8a258f5827727189c9c7195a308"><input id="add-to-cart-form:j_idt706:j_idt727:ft" type="hidden" name="add-to-cart-form:j_idt706:j_idt727:ft" value="412d90f2a08a28a44498013a32756f92"><input id="add-to-cart-form:j_idt706:j_idt727:rt" type="hidden" name="add-to-cart-form:j_idt706:j_idt727:rt" value="1479">
                                
                      <a href="#" class="add-to-cart details-cart-button btn btn-primary btn-lg d-flex justify-content-center tp-button w-100" onclick="global.checkTarpit(this, event)" title="Leg in de winkelwagen">
                        <i class="fas fa-cog fa-spin d-none"></i>
                        <span class="cart-btn-text">
                          <img src="/mobile/javax.faces.resource/icons/Warenkorb-white.svg.xhtml?ln=pix" alt="Leg in de winkelwagen" width="20" height="20"><span class="mx-2">Leg in de winkelwagen</span>
                        </span>
                      </a><input id="add-to-cart-form:j_idt706:j_idt727:add-to-cart-btn" type="submit" name="add-to-cart-form:j_idt706:j_idt727:add-to-cart-btn" value="" class="d-none add-to-cart" onclick="mojarra.ab(this,event,'action','@form','@form messages tle-add-to-cart-modal:add-to-cart-modal-dialog j_idt1053:add-to-cart-form',{'p':'1866555'});return false"></div>
                          </div>
                        </div>
                      </div>
                    </nav>
                                
                    <nav class="fixed-bottom shadow-top" id="details-fixed-footer" style="display:none;">
                      <div class="container py-2"><div class="d-grid "><input id="add-to-cart-form:j_idt706:j_idt750:vt" type="hidden" name="add-to-cart-form:j_idt706:j_idt750:vt" value="6187f8a258f5827727189c9c7195a308"><input id="add-to-cart-form:j_idt706:j_idt750:ft" type="hidden" name="add-to-cart-form:j_idt706:j_idt750:ft" value="412d90f2a08a28a44498013a32756f92"><input id="add-to-cart-form:j_idt706:j_idt750:rt" type="hidden" name="add-to-cart-form:j_idt706:j_idt750:rt" value="1478">
                                
                      <a href="#" class="add-to-cart details-cart-button btn btn-primary btn-lg d-flex justify-content-center tp-button w-100" onclick="global.checkTarpit(this, event)" title="Leg in de winkelwagen">
                        <i class="fas fa-cog fa-spin d-none"></i>
                        <span class="cart-btn-text">
                          <img src="/mobile/javax.faces.resource/icons/Warenkorb-white.svg.xhtml?ln=pix" alt="Leg in de winkelwagen" width="20" height="20"><span class="mx-2">Leg in de winkelwagen</span>
                        </span>
                      </a><input id="add-to-cart-form:j_idt706:j_idt750:add-to-cart-btn" type="submit" name="add-to-cart-form:j_idt706:j_idt750:add-to-cart-btn" value="" class="d-none add-to-cart" onclick="mojarra.ab(this,event,'action','@form','@form messages tle-add-to-cart-modal:add-to-cart-modal-dialog j_idt1053:add-to-cart-form',{'p':'1866555'});return false"></div>
                      </div>
                    </nav><input type="hidden" name="javax.faces.ViewState" id="j_id1:javax.faces.ViewState:1" value="4047639016788517193:-5808830147699557092" autocomplete="off">
                </form>
                                
                                  <div class="row justify-content-around pt-3" id="additional-feature-section">
                                    <div id="product-wishlist" class="col-auto">
                <form id="wishlist-form" name="wishlist-form" method="post" action="/mobile/details.xhtml" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="wishlist-form" value="wishlist-form">
                <a id="wishlist-form:add-btn" href="#" rel="nofollow" title="Aan je verlanglijst toevoegen" onclick="mojarra.ab(this,event,'click','@this','@form');return false" class="icon-click-size text-center text-dark">
                                          <img src="/mobile/javax.faces.resource/icons/Herz_nicht_gesetzt.svg.xhtml?ln=pix" alt="Aan je verlanglijst toevoegen" width="20" height="20"><span class="ml-2 d-none d-lg-inline">Aan je verlanglijst toevoegen</span>
                                          <input type="hidden" name="p" value="1866555"></a><input type="hidden" name="javax.faces.ViewState" id="j_id1:javax.faces.ViewState:2" value="4047639016788517193:-5808830147699557092" autocomplete="off">
                </form>
                                    </div>
                <form id="compare-form" name="compare-form" method="post" action="/mobile/details.xhtml" class="col-auto" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="compare-form" value="compare-form">
                <a href="#" onclick="jsf.util.chain(this,event,'global.disableAfterTimeout(this.id);','mojarra.jsfcljs(document.getElementById(\\'compare-form\\'),{\\'compare-form:j_idt832\\':\\'compare-form:j_idt832\\',\\'pc_a\\':\\'1866555\\'},\\'compare\\')');return false" class="icon-click-size text-center text-dark" target="compare"><img src="/mobile/javax.faces.resource/icons/Vergleichen.svg.xhtml?ln=pix" alt="Vergelijken" height="20" width="20"><span class="ml-2 d-none d-lg-inline">Vergelijken</span></a><input type="hidden" name="javax.faces.ViewState" id="j_id1:javax.faces.ViewState:3" value="4047639016788517193:-5808830147699557092" autocomplete="off">
                </form>
                <form id="j_idt839" name="j_idt839" method="post" action="/mobile/details.xhtml" class="col-auto" enctype="application/x-www-form-urlencoded">
                <input type="hidden" name="j_idt839" value="j_idt839">
                <a id="j_idt839:j_idt841" href="#" rel="nofollow" onclick="mojarra.ab(this,event,'action',0,'share_modal');return false" class="icon-click-size text-center text-dark" data-bs-target=".product-share-modal" data-bs-toggle="modal"><img src="/mobile/javax.faces.resource/icons/Share.svg.xhtml?ln=pix" alt="Delen" height="20" width="20"><span class="ml-2 d-none d-lg-inline">Delen</span>
                                        <input type="hidden" name="p" value="1866555"></a><input type="hidden" name="javax.faces.ViewState" id="j_id1:javax.faces.ViewState:4" value="4047639016788517193:-5808830147699557092" autocomplete="off">
                </form>
                                  </div>
                                </div>
                              </div>
                            </div>
                """;
        }

        when(httpRequestService.getHtml(psc1.getUrl())).thenReturn(alternateiPhone14Page);

        Price price = scraperService.scrapePrice(psc1);

        assertEquals(1739.00, price.getAmount());
        assertEquals(rc1.getId(), price.getRetailerCompany().getId());
        assertEquals(p1.getId(), price.getProduct().getId());


    }

}