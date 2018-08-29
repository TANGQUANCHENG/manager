package cn.decentchina.manager.system.cache;

import cn.decentchina.manager.system.config.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 唐全成
 * @date 2018-06-06
 */
@Slf4j
@Component
public class RedisSessionDAO  extends EnterpriseCacheSessionDAO {

    /**
     * session 在redis过期时间是60分钟60*60
     */
    private static int expireTime = 3600;

    private static String prefix = "shiro-session:";

    @Resource
    public RedisTemplate<String, Object> redisTemplate;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    // 创建session，保存到数据库
    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = super.doCreate(session);

        log.info("create session:{}",session.getId());
        redisTemplate.opsForValue().set(prefix + sessionId.toString(), session);
        return sessionId;
    }

    // 获取session
    @Override
    protected Session doReadSession(Serializable sessionId) {
        log.debug("get session:{}", sessionId);
        // 先从缓存中获取session，如果没有再去数据库中获取
        Session session = super.doReadSession(sessionId);
        if (session == null) {
            session = (Session) redisTemplate.opsForValue().get(prefix + sessionId.toString());
        }
        return session;
    }

    // 更新session的最后一次访问时间
    @Override
    protected void doUpdate(Session session) {
        super.doUpdate(session);
        String key = prefix + session.getId().toString();
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, session);
        }
        redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
    }

    // 删除session
    @Override
    protected void doDelete(Session session) {
        log.debug("delete session:{}", session.getId());
        super.doDelete(session);
        redisTemplate.delete(prefix + session.getId().toString());
    }

    /**
     * 获取当前所有激活的用户（redis中登录过的session）
     *
     * @return 所有在线用户名
     */
    public List<String> getActiveAdmin() {
        List<String> sessions = new ArrayList<>(10);
        Set<String> keys = stringRedisTemplate.keys("*");
        keys.forEach(key->{
            if(key.indexOf(Constants.CUSTOME_REALM)>0){
                String userInfo=key.split(":")[2];
                sessions.add(userInfo);
            }
        });
        return sessions;
    }


}
