package com.ujiuye.gateway.filters;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Configuration
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final String TOKEN = "Authorization";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = request.getURI().getPath();
        //如果是登录或者注册直接放行
        if (path.startsWith("/api/user/login") || path.startsWith("/api/user/add"))
            return chain.filter(exchange);
        //获取 token
        String tokenStr = "";
        if (request.getQueryParams().getFirst(TOKEN)!=null)
            tokenStr = request.getQueryParams().getFirst(TOKEN);
        if (request.getHeaders().getFirst(TOKEN) != null)
            tokenStr = request.getHeaders().getFirst(TOKEN);
        if (request.getCookies().getFirst(TOKEN) != null)
            tokenStr = request.getCookies().getFirst(TOKEN).getValue();
        if (StringUtils.isEmpty(tokenStr)){
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return response.setComplete();
        }
        System.out.println(tokenStr);
        //处理token
        try {

            if (!tokenStr.startsWith("bearer") || !tokenStr.startsWith("Bearer"))
                tokenStr = "bearer " + tokenStr;
//            Claims claims = JwtUtil.parseJWT(tokenStr);
            request.mutate().header(TOKEN,tokenStr);
            System.out.println(tokenStr);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return response.setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
