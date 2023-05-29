package com.itacorubi.data.collection;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class StockDataPipeline {
    
    static class FetchStockDataFn extends DoFn<String, String> {
        @ProcessElement
        public void processElement(@Element String symbol, OutputReceiver<String> out) {
            HttpClient client = HttpClient.newHttpClient();
            String apiKey = "7HDJXNHFYP5FDSAV"; // Replace with your actual API key
            String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            try {
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                out.output(response.body());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Pipeline pipeline = Pipeline.create();

        // Starting data. In real code, this might be a list of stock symbols or other data.
        pipeline.apply(Create.of("EPAM"))
                .apply(ParDo.of(new FetchStockDataFn()))
                .apply(ParDo.of(new DoFn<String, Void>() {
                    @ProcessElement
                    public void processElement(@Element String data) {
                        System.out.println(data);
                    }
                }));

        pipeline.run().waitUntilFinish();
    }
}
