package com.github.liuzhuoming23.jwtback.app.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 账户信息
 *
 * @author liuzhuoming
 */
@ApiModel
@Data
public class Account {

    @ApiModelProperty(hidden = true)
    private Integer id;
    @ApiModelProperty(name = "username", value = "账户名", required = true, dataType = "String")
    @NotEmpty
    @Size(min = 6, max = 16)
    private String username;
    @ApiModelProperty(name = "password", value = "密码", required = true, dataType = "String")
    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;
    @ApiModelProperty(hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addDateTime;
    @ApiModelProperty(hidden = true)
    private Integer enable = 0;
}
