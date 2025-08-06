package uitls;

/**
 * @author 陈梦
 * @Package: uitls
 * @Description:分页工具
 * @date 2025/8/7 1:12
 */
public class PageUtils {
    public static int getStartPage(int pageNo, int pageSize){
        return (pageNo - 1) * pageSize;
    }

}
