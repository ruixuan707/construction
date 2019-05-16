package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.common.bean.IpUtis;
import com.monco.core.entity.Message;
import com.monco.core.entity.User;
import com.monco.core.manager.UserManager;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/4/26 12:03
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    MessageService messageService;

    @PostMapping
    public ApiResult save(@RequestBody Message message) {
        User user = UserManager.get();
        if (user != null) {
            message.setNickName(user.getNickName());
        }
        message.setClickNumber(ConstantUtils.NUM_0);
        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        message.setIpAddress(IpUtis.getIpAddr(request));
        messageService.save(message);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult update(@RequestBody Message message) {
        messageService.save(message);
        return ApiResult.ok();
    }

    @GetMapping("detail")
    public ApiResult getOne(@RequestParam Long id) {
        Message message = messageService.find(id);
        if (message.getClickNumber() == null) {
            message.setClickNumber(ConstantUtils.NUM_0);
        }
        message.setClickNumber(message.getClickNumber() + 1);
        messageService.save(message);
        return ApiResult.ok(message);
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        Message message = messageService.find(id);
        message.setDataDelete(ConstantUtils.DELETE);
        messageService.save(message);
        return ApiResult.ok();
    }


    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          Message message, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 消息类型
        if (message.getMessageType() != null) {
            queryParam = new QueryParam("messageType", MatchType.equal, message.getMessageType());
            params.add(queryParam);
        }
        if (message.getCategory() != null) {
            queryParam = new QueryParam("category", MatchType.equal, message.getCategory());
            params.add(queryParam);
        }
        Page<Message> result = messageService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }


}
