package com.github.liuhzuoming23.jwtback.app.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * 账户信息
 *
 * @author gnimouhzuil
 * @date 2019/5/23 10:42
 */
@Data
public class Account {

    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    @Size(min = 6, max = 16)
    private String password;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date addDateTime;
}
