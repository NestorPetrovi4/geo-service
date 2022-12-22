import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class TestMessageSender {
    GeoService geoService;
    LocalizationService localizationService;
    Map<String, String> headers;

    @BeforeEach
    public void initTest() {
        geoService = Mockito.mock(GeoService.class);
        localizationService = Mockito.mock(LocalizationService.class);
        headers = new HashMap<>();
    }

    @Test
    public void senderServiceTestRussiaIp() {
        Mockito.when(geoService.byIp(Mockito.startsWith("172."))).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Assertions.assertEquals(messageSender.send(headers), "Добро пожаловать");
    }

    @Test
    public void senderServiceTestUsaIp() {
        Mockito.when(geoService.byIp(Mockito.startsWith("96."))).thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.123.12.19");
        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Assertions.assertEquals(messageSender.send(headers), "Welcome");
    }

    @Test
    public void geoServiceByIpPositiveRussiaTest() {
        Mockito.when(geoService.byIp(Mockito.startsWith("172."))).thenReturn(new Location("Moscow", Country.RUSSIA, "Lenina", 15));
        Assertions.assertEquals(Country.RUSSIA, geoService.byIp("172.123.12.19").getCountry());
    }

    @Test
    public void localizationServiceLocalPositiveRussiaUsaTest() {
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");
        Assertions.assertTrue("Welcome".equals(localizationService.locale(Country.USA)) || "Добро пожаловать".equals(localizationService.locale(Country.RUSSIA)));
    }

}
