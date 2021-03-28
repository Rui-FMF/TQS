import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StocksPortfolioTest {
    @Mock IStockMarket market;
    @InjectMocks StocksPortfolio portfolio;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
    }

    @Test
    void getTotalValue() {

        when(market.getPrice("Amazon")).thenReturn(10.0);
        when(market.getPrice("Facebook")).thenReturn(4.25);

        portfolio.addStock(new Stock("Amazon", 5));
        portfolio.addStock(new Stock("Facebook", 2));


        double test_result = portfolio.getTotalValue();
        double real_result = 10.0*5 + 4.25*2;

        //assertEquals(real_result, test_result); //Junit assert
        assertThat(test_result, is(real_result)); //Hamcrest assert
        verify(market, times(2)).getPrice(anyString());
    }
}