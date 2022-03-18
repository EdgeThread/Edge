package com.ujiuye.userauth.config;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.user.feign.UserFeign;
import com.ujiuye.user.pojo.TbUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Result<TbUser> result = userFeign.findUser(username);
        TbUser user = result.getData();
        if (result != null && result.getData()!= null ){
            String qx = "user,admin,vip";
            List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(qx);
            return new User(username,user.getPassword(),grantedAuthorities);
        }
        return null;
    }
}
