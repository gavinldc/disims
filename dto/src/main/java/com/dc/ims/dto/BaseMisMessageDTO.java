package com.dc.ims.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Created by lvdanchen on 20/9/14.
 */
@Data
@Builder
public abstract class BaseMisMessageDTO {

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

    static public enum MessageDetailTypeEnum {

        TEXT,IMAGE,VOICE,VIDEO,EXPRESSION

    }

    static public enum MessageTypeEnum {

        GROUP,PRIVATE


    }
}
