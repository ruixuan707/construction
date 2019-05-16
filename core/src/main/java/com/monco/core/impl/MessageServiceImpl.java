package com.monco.core.impl;

import com.monco.core.entity.Message;
import com.monco.core.service.MessageService;
import org.springframework.stereotype.Service;

/**
 * @Auther: monco
 * @Date: 2019/4/26 12:02
 * @Description:
 */
@Service
public class MessageServiceImpl extends BaseServiceImpl<Message, Long> implements MessageService {
}
