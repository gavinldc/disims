package com.dc.disims.action;

import com.alibaba.fastjson.JSONObject;
import com.dc.ims.dto.BaseClientMessageDTO;
import io.netty.channel.Channel;

/**
 * Created by lvdanchen on 20/9/22.
 */
public interface IMessageAction {


    public void dispatch(JSONObject jsonObject, Channel channel);


}
