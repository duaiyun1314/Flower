package com.andy.flower;

/**
 * Created by andy on 16-6-6.
 */
public class Constants {

    public static final String Authorization = "Authorization";

    //user
    public static final String IS_LOGIN = "is_login";
    public static final String LOGINTIME = "loginTime";
    public static final String USERNAME = "userName";
    public static final String USERID = "userID";
    public static final String USERACCOUNT = "userAccount";
    public static final String USERPASSWORD = "userPassword";
    public static final String USERHEADKEY = "userHeadKey";
    public static final String USEREMAIL = "userEmail";
    //token
    public static final String TOKENACCESS = "TokenAccess";
    public static final String TOKENREFRESH = "TokenRefresh";
    public static final String TOKENTYPE = "TokenType";
    public static final String TOKENEXPIRESIN = "TokeExpiresIn";

    //board info
    public static final String BOARDTILTARRAY = "boardTitleArray";
    public static final String BOARDIDARRAY = "boardIdArray";


    public static final String USERNAME_NULL = "用户名为空";
    public static final String PASSWORD_NULL = "密码为空";

    public static final String EMAIL_INVALID = "邮箱格式不正确";

    private static final String mBasic = "Basic ";
    private static final String mClientInfo = "MWQ5MTJjYWU0NzE0NGZhMDlkODg6Zjk0ZmNjMDliNTliNDM0OWExNDhhMjAzYWIyZjIwYzc=";
    public static final String mClientInto = mBasic + mClientInfo;


    //获得用户画板列表详情的操作符
    public static final String OPERATEBOARDEXTRA = "recommend_tags";

    //,
    public static final String SEPARATECOMMA = ",";

    //root url
    public static final String ImgRootUrl = "http://img.hb.aicdn.com/";
    public static final String BannerImgUrl = "http://hbfile.b0.upaiyun.com";
    public static final String Register_Url = "http://www.huaban.com";

    public static final String[] Categories_ID = new String[]{"all", "home", "diy_crafts", "photography", "food_drink", "travel_places", "illustration", "design", "apparel", "modeling_hair", "wedding_events", "desire", "beauty", "pets", "kids", "architecture", "film_music_books", "tips", "art", "men", "fitness", "quotes", "people", "geek", "data_presentation", "games", "cars_motorcycles", "education", "sports", "funny", "industrial_design", "anime"};
    public static final String[] Categories_NAMES = new String[]{"首页", "家居/家装", "手工/布艺", "摄影", "美食", "旅行", "插画/漫画", "平面", "女装/搭配", "造型/美妆", "婚礼", "礼物", "美女", "宠物", "儿童", "建筑/设计", "电影/图书", "生活百科", "人文艺术", "男士/风尚", "健身/舞蹈", "美图", "明星", "极客", "数据图", "游戏", "汽车/摩托", "教育", "运动", "搞笑", "工业设计", "动漫"};
    public static final int PAGE_COUNT_LIMIT = 20;
    public static final int CATEGORY_CACHE_COUNT = 2;
    public static final float IMAGE_MAXHEIGHT_SCALE = 0.7f;

    public static final String GENERAL_IMG_SUFFIX = "_fw320sf";
    public static final String BIG_IMG_SUFFIX = "_fw658";
    public static final String SMALL_IMG_SUFFIX = "_sq75sf";

    public static final String UNLIKEOPERATOR = "unlike";
    public static final String LIKEOPERATOR = "like";

    public static final String IMAGE_PATH = "flower/";
}