package com.loyayz.simple.auth.core.identity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author loyayz (loyayz@foxmail.com)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthIdentity implements Serializable {
    private static final long serialVersionUID = -1L;

    private String id;
    /**
     * 类型
     */
    private String type;
    /**
     * 名称
     */
    private String name;
    /**
     * 角色
     */
    private List<AuthIdentityRole> roles = new ArrayList<>();

    public List<AuthIdentityRole> getRoles() {
        if (this.roles == null) {
            return Collections.emptyList();
        }
        return roles;
    }

}
