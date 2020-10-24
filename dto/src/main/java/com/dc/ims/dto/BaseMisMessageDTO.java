package com.dc.disims.Message;

import com.dc.common.enums.MessageDetailTypeEnum;
import com.dc.common.enums.MessageTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * Created by lvdanchen on 20/9/14.
 */
@Data
@Builder
public abstract class MisMessage {

    private Long id;

    private Long stime;//server response timestamp

    private Long ctime;//client send timestamp

    private Long rtime;//server receive timestamp

    private String clientToken;//token provide by client

    private Long fromUserId;

    private Long toUserId;

    private MessageDetailTypeEnum detailType;

    private MessageTypeEnum  messageType;


    /**
     * generate api document
     */
    public static  void toApiDoc(){





    }
}
