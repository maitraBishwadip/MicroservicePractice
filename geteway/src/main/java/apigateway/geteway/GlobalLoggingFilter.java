package apigateway.geteway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j

public class GlobalLoggingFilter implements GlobalFilter, Ordered {
    @Override
    //Prefilter
    public Mono<Void> filter(ServerWebExchange exchange , GatewayFilterChain chain)
    {
        log.info("Logging from Global: {}",  exchange.getRequest().getURI().getPath());
        return chain.filter(exchange);

        //post filter

    }


    @Override
    public int getOrder() {
        return 5;
    }
}
