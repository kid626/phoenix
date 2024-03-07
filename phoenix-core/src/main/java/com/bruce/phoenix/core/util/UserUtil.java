package com.bruce.phoenix.core.util;

import com.bruce.phoenix.core.model.security.UserAuthentication;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/5 16:34
 * @Author Bruce
 */
@UtilityClass
public class UserUtil {


    public static UserAuthentication getCurrentUser() {
        return (UserAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }


    public static void setCurrentUser(UserAuthentication user) {
        SecurityContextHolder.getContext().setAuthentication(user);
    }

    public static void clearCurrentUser(){
        SecurityContextHolder.clearContext();
    }

}
