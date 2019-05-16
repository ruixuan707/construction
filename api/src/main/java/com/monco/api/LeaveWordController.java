package com.monco.api;

import com.monco.common.bean.ApiResult;
import com.monco.common.bean.ConstantUtils;
import com.monco.common.bean.IpUtis;
import com.monco.core.entity.LeaveWord;
import com.monco.core.manager.UserManager;
import com.monco.core.query.MatchType;
import com.monco.core.query.OrderQuery;
import com.monco.core.query.QueryParam;
import com.monco.core.service.LeaveWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: monco
 * @Date: 2019/5/5 02:55
 * @Description:
 */
@Slf4j
@RestController
@RequestMapping("leave-word")
public class LeaveWordController {

    @Autowired
    LeaveWordService leaveWordService;

    @PostMapping("post")
    public ApiResult save(@RequestBody LeaveWord leaveWord) {
        if (UserManager.get() != null) {
            if (StringUtils.isEmpty(UserManager.get().getNickName())) {
                leaveWord.setNickName(UserManager.get().getUsername());
            } else {
                leaveWord.setNickName(UserManager.get().getNickName());
            }
        }
        //获取用户ip地址
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        leaveWord.setIpAddress(IpUtis.getIpAddr(request));
        leaveWord.setPassStatus(ConstantUtils.NUM_0);
        leaveWordService.save(leaveWord);
        return ApiResult.ok();
    }

    @PutMapping("reply")
    public ApiResult reply(@RequestBody LeaveWord leaveWord) {
        if (UserManager.get() != null) {
            if (StringUtils.isEmpty(UserManager.get().getNickName())) {
                leaveWord.setReplyName(UserManager.get().getUsername());
            } else {
                leaveWord.setReplyName(UserManager.get().getNickName());
            }
        }
        leaveWordService.save(leaveWord);
        return ApiResult.ok();
    }

    @GetMapping
    public ApiResult getOne(@RequestParam Long id) {
        LeaveWord leaveWord = leaveWordService.find(id);
        return ApiResult.ok(leaveWord);
    }

    @DeleteMapping
    public ApiResult delete(@RequestParam Long id) {
        LeaveWord leaveWord = leaveWordService.find(id);
        leaveWord.setDataDelete(ConstantUtils.DELETE);
        leaveWordService.save(leaveWord);
        return ApiResult.ok();
    }

    @PutMapping
    public ApiResult update(@RequestBody LeaveWord leaveWord) {
        leaveWordService.save(leaveWord);
        return ApiResult.ok();
    }

    @GetMapping("list")
    public ApiResult list(@RequestParam(value = "currentPage", defaultValue = "1") Integer currentPage,
                          @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize,
                          LeaveWord leaveWord, OrderQuery orderQuery) {
        List<QueryParam> params = new ArrayList<>();
        QueryParam queryParam = new QueryParam("dataDelete", MatchType.equal, ConstantUtils.UN_DELETE);
        params.add(queryParam);
        // 留言类型
        if (leaveWord.getWordType() != null) {
            queryParam = new QueryParam("wordType", MatchType.equal, leaveWord.getWordType());
            params.add(queryParam);
        }
        // 通过状态
        if (leaveWord.getPassStatus() != null) {
            queryParam = new QueryParam("passStatus", MatchType.equal, leaveWord.getPassStatus());
            params.add(queryParam);
        }
        Page<LeaveWord> result = leaveWordService.findPage(pageSize, currentPage, params, orderQuery.getOrderType(), orderQuery.getOrderField());
        return ApiResult.ok(result);
    }
}
