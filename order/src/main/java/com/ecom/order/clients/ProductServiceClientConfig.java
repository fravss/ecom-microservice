package com.ecom.order.clients;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.util.Optional;


@Configuration
public class ProductServiceClientConfig {



    @Bean
    public ProductServiceClient productServiceInterface(RestClient.Builder restClientBuilder) {
        RestClient restClient = restClientBuilder
                .baseUrl("http://product-service")
                .defaultStatusHandler(HttpStatusCode::is4xxClientError,
                        ((request, response) -> Optional.empty()))
                .build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory
                .builderFor(adapter)
                .build();
        return factory.createClient(ProductServiceClient.class);
    }
}
