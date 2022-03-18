package com.ujiuye.user.feign;

import com.ujiuye.dongyimaicommon.entity.Result;
import com.ujiuye.user.pojo.TbUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("dym-user")
@RequestMapping("/user")
public interface UserFeign {

    @RequestMapping("/load/{username}")
    public Result<TbUser> findUser (@PathVariable String username);

    @RequestMapping("/points/add")
    public Result  addPoints(@RequestParam("points") Integer points);
}
