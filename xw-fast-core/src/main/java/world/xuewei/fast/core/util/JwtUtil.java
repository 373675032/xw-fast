package world.xuewei.fast.core.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * JWT工具类
 *
 * @author XUEW
 */
public class JwtUtil {

    /**
     * 密钥
     */
    private static String SECRET = "";

    static {
        // 读取根目录下的秘钥文件
        String target = "jwt_secret.txt";
        StringBuilder stringBuilder = new StringBuilder();
        try (Scanner scanner = new Scanner(new ClassPathResource(target).getInputStream(), StandardCharsets.UTF_8.name())) {
            while (scanner.hasNextLine()) {
                stringBuilder.append(scanner.nextLine());
            }
            SECRET = stringBuilder.toString();
        } catch (IOException e) {
            // 未放在秘钥文件，随机生成秘钥
            SECRET = UUID.randomUUID().toString();
        }

    }

    /**
     * 获取Token，默认过期时间为1天
     */
    public static String getToken(Map<String, String> map) {
        return getToken(map, Calendar.DATE, 1);
    }

    /**
     * 获取Token，指定过期时间
     */
    public static String getToken(Map<String, String> map, Integer calenderType, Integer count) {
        // 创建令牌的过期时间
        Calendar instance = Calendar.getInstance();
        instance.add(calenderType, count);
        // 创建令牌建造者，封装请求参数
        JWTCreator.Builder builder = JWT.create();
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        // 设置过期时间
        builder.withExpiresAt(instance.getTime());
        // 设置加密算法和密钥
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 验证Token的合法性
     * TokenExpiredException Token过期
     * SignatureVerificationException 密钥不匹配
     * AlgorithmMismatchException 算法不匹配
     * JWTDecodeException Token异常
     */
    public static void validate(String token) {
        JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * 获取Token中包含的信息
     */
    public static Map<String, String> getValidationsObjects(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        Map<String, Claim> claims = verify.getClaims();
        // 转换为标准的Map
        Map<String, String> result = new HashMap<>();
        claims.forEach((k, v) -> {
            result.put(k, v.asString());
        });
        return result;
    }

}
