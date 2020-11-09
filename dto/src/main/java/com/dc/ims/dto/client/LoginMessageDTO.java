package com.dc.ims.dto.client;

import com.dc.common.util.MD5;
import com.dc.ims.dto.BaseClientMessageDTO;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by lvdanchen on 20/9/21.
 */
@Data
@ToString(callSuper = true)
public class LoginMessageDTO extends BaseClientMessageDTO{


    @NotEmpty
    private String auth;
    @Size(min=6,max=10,message = "password 非法定义")
    private String pwd;
    @Size(min=6,max=10,message = "clientId 非法定义")
    private String clientId;
    @NotEmpty
    private String ip;

    @Override
    public boolean validate() throws Exception {
        return auth.equals(MD5.md5(clientId.concat(pwd).concat(ip)));
    }
}
