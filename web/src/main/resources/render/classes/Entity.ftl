package ${package};

<#list imports as import>
import ${import.getPackageName()};
</#list>

<#list annotations as annotation>
${annotation.getName()}
</#list>
public class ${className} extends AbstractEntity{

        <#list attributes as attribute>
                <#list attribute.getAnnotations() as annotation>
        ${annotation.getName()}
                </#list>
                <#if attribute.getAttributeType().getId() == 1>
        public ${attribute.getDataType().getName()} ${attribute.getName()};
                <#else>
               <#if attribute.getRelationType().getId() == 1>
        public List<${attribute.getChild().getName()}> ${attribute.getName()};
               </#if>
               <#if attribute.getRelationType().getId() == 4>
        public List<${attribute.getChild().getName()}> ${attribute.getName()};
               </#if>
               <#if attribute.getRelationType().getId() == 2>
        public ${attribute.getChild().getName()} ${attribute.getName()};
               </#if>
               <#if attribute.getRelationType().getId() == 3>
        public ${attribute.getChild().getName()} ${attribute.getName()};
               </#if>
               </#if>
        </#list>
}

