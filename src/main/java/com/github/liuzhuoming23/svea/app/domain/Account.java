package com.github.liuzhuoming23.svea.app.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 账户信息
 *
 * @author liuzhuoming
 */
@Data
public class Account {

    private Integer id;
    @NotEmpty
    @Size(min = 6, max = 16)
    private String username;
    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;
    @NotNull
    @Min(0)
    private Integer roleId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addDatetime;
    private Integer enable;
}
