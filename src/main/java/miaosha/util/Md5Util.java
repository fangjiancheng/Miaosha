package miaosha.util;

import org.springframework.util.DigestUtils;

/**
 * Created by fjc on 16-12-8.
 */
public class Md5Util {

        public static String getMd5(long seckillId) {
            //    用于混淆md5
            final String salt = "adf%d7f9asd%%(*&:>?@asd";
            String base=seckillId+"/"+salt;
            return DigestUtils.md5DigestAsHex(base.getBytes());
        }
}
