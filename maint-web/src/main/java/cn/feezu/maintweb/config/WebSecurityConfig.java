package cn.feezu.maintweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cn.feezu.wzc.common.auth.security.JwtAuthenticationEntryPoint;
import cn.feezu.wzc.common.auth.security.JwtAuthenticationTokenFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;
    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
        	//swagger
        	.antMatchers("swagger-ui.html")
            .antMatchers("/swagger**")
            .antMatchers("/swagger**/**")
            .antMatchers("/webjars/**")
            .antMatchers("/v2/api-docs")
            
            //登录
            .antMatchers("/web-api/login")
            .antMatchers("/login/valid.json")
            .antMatchers("/","/static/**")
            
            //创建/取消 工单
            .antMatchers("/web-api/order/create")
            .antMatchers("/web-api/{id:.+}/cancel")
            .antMatchers("/web-api/**/cancel")
            .antMatchers("/test/*")
            
            .antMatchers("/manage/shutdown")//关闭
            ;
           
        
    }
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	   httpSecurity.formLogin().loginPage("/login.html")
    	   .failureForwardUrl("/login.html")
    	   .failureUrl("/login.html")
    	   .defaultSuccessUrl("/index.html").permitAll()
           .and()
           .logout().logoutSuccessUrl("/login.html").invalidateHttpSession(true);
        
    	   httpSecurity
        .csrf().disable() // 由于使用的是JWT，我们这里不需要csrf
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
        // 基于token，所以不需要session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .authorizeRequests()
        //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        // 允许对于网站静态资源的无授权访问
        .antMatchers(HttpMethod.GET, "/login.html").permitAll()//TODO 待优化
        .antMatchers(HttpMethod.POST, "/login/valid.json").permitAll()
        .and().authorizeRequests().antMatchers("/**","/**.html","/*-api/**").authenticated();
        // 对于获取token的rest api要允许匿名访问
        //.antMatchers("/auth/**").permitAll()
        // 除上面外的所有请求全部需要鉴权认证
       // .anyRequest().authenticated();
        httpSecurity.headers().frameOptions().disable();
        httpSecurity.httpBasic();
        // 添加JWT filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
        httpSecurity.headers().contentTypeOptions().disable();
        //TODO 开启https跳转 httpSecurity.portMapper().http(80).mapsTo(443);
     
    }
}
