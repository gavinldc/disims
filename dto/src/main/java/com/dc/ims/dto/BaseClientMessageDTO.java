package com.dc.ims.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Created by lvdanchen on 20/9/21.
 */
@Data
@ToString
public abstract class BaseClientMessageDTO implements IMessageDTO {


    private BusinessTypeEnum businessType;

    private Long ctime;//client send time;





    static public enum BusinessTypeEnum{
        LOGIN,PING,LOGIC
    }


    @Override
    public boolean validate() throws Exception {
        return true;
    }
}
