package info.lmovse.springcloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

import static com.netflix.zuul.context.RequestContext.getCurrentContext;

public class RequestInfoFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestInfoFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        LOGGER.info("===> Receive request from: {}", request.getRemoteHost());
        return null;
    }
}
