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
        public ${attribute.getDataType().getName()} ${attribute.getName()};
        </#list>
}

