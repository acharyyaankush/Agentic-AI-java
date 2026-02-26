package com.example.agent;

import java.util.HashMap;
import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.Annotations.Schema;
import com.google.adk.tools.FunctionTool;
import com.google.adk.web.AdkWebServer;

public class StockTicker {
    public static void main(String[] args) {
        AdkWebServer.start(
            LlmAgent.builder()
                .name("stock_agent")
                .instruction("""
                    You are a stock exchange ticker expert.
                    When asked about the stock price of a company,
                    use the `lookup_stock_ticker` tool to find the information.
                """)
                .model("gemini-2.5-flash")
                .tools(FunctionTool.create(StockTicker.class, "lookupStockTicker"))
                .build()
        );
    }

    @Schema(
        name = "lookup_stock_ticker",
        description = "Lookup stock price for a given company or ticker"
    )
    public static Map<String, String> lookupStockTicker(
        @Schema(name = "company_name_or_stock_ticker", description = "The company name or stock ticker")
        String ticker) {

        // Custom logic to return a mock stock price
        Map<String, String> result = new HashMap<>();
        
        if (ticker.equalsIgnoreCase("GOOGL") || ticker.toLowerCase().contains("google")) {
            result.put("stock_price", "175.50");
        } else if (ticker.equalsIgnoreCase("AAPL") || ticker.toLowerCase().contains("apple")) {
            result.put("stock_price", "190.25");
        } else {
            // Default mock price for any other company
            result.put("stock_price", "120.00"); 
        }
        
        return result;
    }
}