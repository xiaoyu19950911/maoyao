package com.qjd.rry.security.jwt;

import com.qjd.rry.repository.UserRepository;
import com.qjd.rry.service.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtTokenUtil jwtUtils;

    @Autowired
    TokenAuthenticationService tokenAuthenticationService;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /*@Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UsernamePasswordAuthenticationToken authentication=tokenAuthenticationService.getAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.setStatus(HttpServletResponse.SC_OK);
        filterChain.doFilter(request, response);
    }*/


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        /**
         * 第一种校验
         */
       /* String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "
            String username = jwtUtils.getUsernameFromToken(authToken);
            com.qjd.rry.entity.User user = userRepository.findUserByToken(authToken);
            logger.info("checking authentication " + username);
            if (user != null && SecurityContextHolder.getContext().getAuthentication() == null&&username!=null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtUtils.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }else {
                    request.setAttribute("exception","invalidToken");
                }
            } else if (user == null) {
                request.setAttribute("exception","invalidToken");
            }
        }else {
            request.setAttribute("exception","invalidToken");
        }
        chain.doFilter(request, response);*/

        /**
         * 第二种校验
         */
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            request.setAttribute("exception","invalidToken");
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication==null)
            request.setAttribute("exception","invalidToken");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || authHeader.isEmpty()) {
            request.setAttribute("exception","invalidToken");
            //throw new CommunalException(-1,"Token为空");
        }
        String authToken = authHeader.substring(tokenHead.length());
        // parse the token.
        String username = jwtUtils.getUsernameFromToken(authToken);
        String tokenType=jwtUtils.getTokenTypeFromToken(authToken);
            if (username != null&&tokenType.equals("token")) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            }

            /*catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            throw new CommunalException(-1,"Token已过期");
        } catch (UnsupportedJwtException e) {
            logger.error("Token格式错误: {} " + e);
            throw new CommunalException(-1,"Token格式错误");
        } catch (MalformedJwtException e) {
            logger.error("Token没有被正确构造: {} " + e);
            throw new CommunalException(-1,"Token没有被正确构造");
        } catch (SignatureException e) {
            logger.error("签名失败: {} " + e);
            throw new CommunalException(-1,"签名失败");
        } catch (IllegalArgumentException e) {
            logger.error("非法参数异常: {} " + e);
            throw new CommunalException(-1,"非法参数异常");
        }*/

        return null;
    }
}
