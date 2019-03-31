package com.bear.common.entity.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author panda.
 * @version 1.0.
 * @since 2018-11-25 15:29.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredential implements Serializable {

    private static final long serialVersionUID = -958701617717204974L;

    private String username;
    /**
     * @see com.bear.common.constants.CredentialType
     */
    private String type;
    private Long userId;

}