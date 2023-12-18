package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};

/**
 * @Copyright Copyright © ${.now?string("yyyy")} ${author} . All rights reserved.
 * @Desc ${table.comment!}mapper 层
 * @ProjectName ${cfg.project_name}
 * @Date ${.now}
 * @Author ${author}
 */
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
