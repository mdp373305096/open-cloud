package com.opencloud.api.gateway.actuator;

import com.opencloud.api.gateway.actuator.event.GatewayRefreshRemoteApplicationEvent;
import com.opencloud.common.model.ResultBody;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.Selector;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.cloud.bus.endpoint.AbstractBusEndpoint;
import org.springframework.context.ApplicationEventPublisher;

/**
 * 自定义网关刷新端点
 * @author liuyadu
 */
@Endpoint(
        id = "refresh-gateway"
)
public class GatewayRefreshBusEndpoint extends AbstractBusEndpoint {

    public GatewayRefreshBusEndpoint(ApplicationEventPublisher context, String id) {
        super(context, id);
    }

    /**
     * 支持灰度发布
     * /actuator/refresh-gateway?destination = customers：**
     *
     * @param destination
     */
    @WriteOperation
    public ResultBody busRefreshWithDestination(@Selector String destination) {
        this.publish(new GatewayRefreshRemoteApplicationEvent(this, this.getInstanceId(), destination));
        return ResultBody.success("刷新成功",null);
    }

    /**
     * 刷新所有
     */
    @WriteOperation
    public ResultBody busRefresh() {
        this.publish(new GatewayRefreshRemoteApplicationEvent(this, this.getInstanceId(), (String) null));
        return ResultBody.success("刷新成功",null);
    }
}
