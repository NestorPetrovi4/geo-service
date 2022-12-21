import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImplMock;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImplMock;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class TestMessageSender {
    GeoService geoServiceImplMock;
    LocalizationService localizationServiceImplMock;
    Map<String, String> headers;

    @BeforeEach
    public void initTest() {
        geoServiceImplMock = new GeoServiceImplMock();
        localizationServiceImplMock = new LocalizationServiceImplMock();
        headers = new HashMap<>();
    }

    @Test
    public void senderServiceTestRussia() {
        MessageSenderImpl messageSender = new MessageSenderImpl(geoServiceImplMock, localizationServiceImplMock);
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.123.12.19");
        String result = "Добро пожаловать";
        Assertions.assertEquals(messageSender.send(headers), result);
    }

    @Test
    public void senderServiceTestUSA() {
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.123.12.19");
        MessageSender messageSender = Mockito.mock(MessageSender.class);
        Mockito.when(messageSender.send(headers)).thenReturn("USA");
        Assertions.assertEquals(messageSender.send(headers), "USA");
    }

    @Test
    public void checkByIpTest() {
        Location resultLocation = new Location("Moscow", Country.RUSSIA, null, 0);
        Assertions.assertEquals(resultLocation.getCity(), geoServiceImplMock.byIp("172.123.12.19").getCity());
    }

    @Test
    public void checkLocaleTest() {
        final String result = "Добро пожаловать";
        Assertions.assertEquals(result, localizationServiceImplMock.locale(Country.RUSSIA));
    }

}
