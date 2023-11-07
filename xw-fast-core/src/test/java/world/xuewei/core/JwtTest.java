package world.xuewei.core;

import org.junit.Test;
import world.xuewei.fast.core.util.JwtUtil;

import java.util.HashMap;

/**
 * @author XUEW
 * @since 2023/11/7 16:54
 */
public class JwtTest {

    @Test
    public void test1(){
        System.out.println(JwtUtil.getToken(new HashMap<>()));
    }
}
