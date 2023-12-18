package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};

/**
 * @Copyright Copyright © ${.now?string("yyyy")} ${author} . All rights reserved.
 * @Desc ${table.comment!}service 层
 * @ProjectName ${cfg.project_name}
 * @Date ${.now}
 * @Author ${author}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

}
</#if>
