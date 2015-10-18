package ${package};

<#list imports as import>
import ${import.getPackageName()};
</#list>

<#list annotations as annotation>
${annotation.getName()}
</#list>
public interface ${interfaceName} extends CrudRepository<${modelName}, Long>{
}

