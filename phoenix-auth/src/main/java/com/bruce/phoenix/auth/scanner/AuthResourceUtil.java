package com.bruce.phoenix.auth.scanner;

import com.bruce.phoenix.auth.model.dto.ResourceDTO;
import com.dangdang.ddframe.rdb.sharding.id.generator.IdGenerator;
import com.dangdang.ddframe.rdb.sharding.id.generator.self.IPIdGenerator;

import java.text.MessageFormat;

/**
 * @Copyright Copyright © 2024 fanzh . All rights reserved.
 * @Desc
 * @ProjectName phoenix
 * @Date 2024/3/7 10:48
 * @Author Bruce
 */
public class AuthResourceUtil {

    public static IdGenerator idGenerator = new IPIdGenerator();

    public static String genDDL(ResourceDTO res, boolean autoIncrement) {
        String ddl;
        if (autoIncrement) {
            ddl = MessageFormat
                    .format(
                            "insert into resource " +
                                    "(code, name, parent_code, method, url, icon, order_num, type, is_enable, create_user, create_time, update_user, update_time, is_delete)" +
                                    " values " +
                                    "(''{0}'',''{1}'',''{2}'',{3},''{4}'',''{5}'',{6},{7},''Y'',''admin'',now(),''admin'',now(),''N'');",
                            res.getCode(), res.getName(), res.getParentCode(), res.getMethod(), res.getUrl(), res.getIcon(), res.getOrderNum(), res.getType());

        } else {
            ddl = MessageFormat
                    .format(
                            "insert into resource " +
                                    "(id, code, name, parent_code, method, url, icon, order_num, type, is_enable, create_user, create_time, update_user, update_time, is_delete)" +
                                    " values " +
                                    "(''{0}'',''{1}'',''{2}'',''{3}'',{4},''{5}'',''{6}'',{7},{8},''Y'',''admin'',now(),''admin'',now(),''N'');",
                            String.valueOf(idGenerator.generateId().longValue()), res.getCode(), res.getName(), res.getParentCode(), res.getMethod(), res.getUrl(), res.getIcon(), res.getOrderNum(), res.getType());
        }
        return ddl;

    }

}
