package cn.iocoder.yudao.module.system.dal.redis.dd;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.DINGTALK_ACCESS_TOKEN;

/**
 * @author zrl
 * @date 2024/10/31
 */
@Repository
public class DingTalkAccessTokenRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String get() {
        return stringRedisTemplate.opsForValue().get(DINGTALK_ACCESS_TOKEN);
    }


    public void set(String token, Long expiresIn){
        stringRedisTemplate.opsForValue().set(DINGTALK_ACCESS_TOKEN, token, expiresIn - 100, TimeUnit.SECONDS);
    }

}
