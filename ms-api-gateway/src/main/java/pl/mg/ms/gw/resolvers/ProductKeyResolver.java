package pl.mg.ms.gw.resolvers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
@Slf4j
public class ProductKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        log.info("Request from {}", Objects.requireNonNull(exchange.getRequest().getLocalAddress().getHostName()));
        return Mono.just(Objects.requireNonNull(exchange.getRequest().getLocalAddress().getHostName()));
    }
}
