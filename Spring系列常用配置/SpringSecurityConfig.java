package com.whpu.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *@author xxh
 *@date 2020/4/8
 *@discription: SpringSecurity的配置类
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig  extends WebSecurityConfigurerAdapter{

//  @Override
  protected void configure(HttpSecurity security) throws Exception {
    security.authorizeRequests()//开启校验请求
      .antMatchers("/login","/")
      .permitAll()//允许访问index.html
      .antMatchers("/css/**","/images/**","/js/**","/lib/**")//放行登陆页面需要的静态资源
      .permitAll()
      .and()
      .authorizeRequests()
      .anyRequest()
      .authenticated()//其他未设置的请求都需要校验，如果校验不通过403
      .and()
      .formLogin()//如果校验没有通过跳转到指定页面，没有指定提供默认登陆表单，如果不配置这个403
      .loginPage("/login")		// 校验失败跳转指定登录页面（如果没有指定会访问SpringSecurity自带的登录页）
      .loginProcessingUrl("/do/login")	// 指定提交登录表单设置的请求地址
      .usernameParameter("username")			// 定制登录账号的请求参数名 也就是form表单中账号input标签name值
      .passwordParameter("password")			// 定制登录密码的请求参数名,Spring5以后页面获取的password是经过加密的
      .defaultSuccessUrl("/login/success")		// 登录成功后前往的地址
      .and()
      .logout()
      .logoutUrl("/my/logout")
      .logoutSuccessUrl("/index")
      .and()
      .csrf()
      .disable();
    ;


  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
      .withUser("xxx")
      .password("$2a$10$DVLAg3KFYsQYGZetYchPfezS.I.nX1zaBKbl.0OvSITiwsBvP3tSe")
      .roles("admin")
      .authorities("update")
      ;
  }
  @Bean
  public PasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

}
