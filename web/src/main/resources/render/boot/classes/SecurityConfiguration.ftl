package ${package};
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

@Override
protected void configure(AuthenticationManagerBuilder auth) throws Exception {
<#if !users.isEmpty()>
auth.inMemoryAuthentication()
</#if>

<#list users as user>
.withUser("${user.username}").password("${user.password}").roles("${user.securityRoles.name()}").and()
</#list>
<#if !users.isEmpty()>
;
</#if>
}

@Override
protected void configure(HttpSecurity http) throws Exception {

http.httpBasic().and().
csrf().disable();
}

}
