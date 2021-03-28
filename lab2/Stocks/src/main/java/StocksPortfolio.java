import java.util.ArrayList;

public class StocksPortfolio {
    private String name;
    private IStockMarket marketService;
    private ArrayList<Stock> stocks = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IStockMarket getMarketService() {
        return marketService;
    }

    public void setMarketService(IStockMarket marketService) {
        this.marketService = marketService;
    }

    public Double getTotalValue(){
        double total = 0;
        for (Stock s: stocks) {
            total += s.getQuantity() * marketService.getPrice(s.getName());
        }
        return total;
    }

    public void addStock(Stock stock){
        stocks.add(stock);
    }
}
