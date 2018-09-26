package com.qjd.rry;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;
import com.pingplusplus.model.ChargeCollection;
import com.qjd.rry.async.WeihouTask;
import com.qjd.rry.convert.OrderConvert;
import com.qjd.rry.entity.*;
import com.qjd.rry.enums.CategoryEnums;
import com.qjd.rry.enums.ProgramEnums;
import com.qjd.rry.repository.*;
import com.qjd.rry.service.WeiHouService;
import com.qjd.rry.utils.SignUtil;
import com.qjd.rry.utils.TimeTransUtil;
import com.qjd.rry.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RryApplicationTests {

    private static final String CREATE_WEIHOU_USER_URL = "http://e.vhall.com/api/vhallapi/v2/user/register?name={name}&head={head}&third_user_id={third_user_id}&pass={pass}&auth_type={auth_type}&app_key={app_key}&signed_at={signed_at}&sign={sign}";


    @Autowired
    WeihouTask weihouTask;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    ColumnRepository columnRepository;

    @Autowired
    CourseColumnRelationRepository courseColumnRelationRepository;

    @Autowired
    ArtExamRepository artExamRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserAuthsRepository userAuthsRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    WeiHouService weiHouService;

    @Value("${weihou.appkey}")
    private String WH_AppKey;

    @Value("${weihou.secretkey}")
    private String WH_SecretKey;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    IncomeRepository incomeRepository;

    @Value("${pay.apiKey}")
    private String apiKey;

    @Autowired
    TxnRepository txnRepository;

    @Autowired
    VipCardRepository vipCardRepository;

    /**
     * 增加课程
     */
    @Test
    public void createCourse() {
        List<String> coverList = new ArrayList<>();
        coverList.add("http://file.caixinet.com/24_1527595373242_image.png");
        coverList.add("http://file.caixinet.com/23_1527575664918_1527575643351.jpg");
        coverList.add("http://file.caixinet.com/23_1527574734489_1527574715961.jpg");
        coverList.add("http://file.caixinet.com/24_1527485600144_image.png");
        coverList.add("http://pic18.photophoto.cn/20110303/0008020624850648_b.jpg");
        coverList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1528083941&di=9ecc124a3aef463735a99753c7d65dd1&imgtype=jpg&er=1&src=http%3A%2F%2Fdynamic1.icourses.cn%2Fcoursepic%2F2013%2F0424%2F5866_480.jpg");
        coverList.add("http://file.caixinet.com/24_1526025878514_image.png");
        coverList.add("http://file.caixinet.com/24_1526024171768_image.png");
        coverList.add("http://file.caixinet.com/24_1526023760562_image.png");
        coverList.add("http://file.caixinet.com/24_1525774886869_image.png");
        coverList.add("http://file.caixinet.com/24_1525758572696_image.png");
        coverList.add("http://file.caixinet.com/24_1525749030421_image.png");
        List<Course> courseList = Lists.newArrayList();
        for (int i = 0; i < 100000; i++) {
            Course course = new Course();
            course.setCover(coverList.get(3));
            course.setTitle("标题" + i);
            course.setCreateTime(new Date());
            course.setPlayType(0);
            course.setPrice(new BigDecimal(i * 1.0));
            course.setUpdateTime(new Date());
            course.setRemark("备注" + i);
            course.setUserId(i);
            course.setStartTime(new Date());
            courseList.add(course);
        }
        courseRepository.saveAll(courseList);
    }

    @Test
    public void createColumn() {
        List<Columns> columnsList = new LinkedList<>();
        for (int i = 70000; i < 100000; i++) {
            Columns column = new Columns();
            column.setCover("专栏封面" + i);
            column.setIntro("专栏介绍" + i);
            column.setName("专栏名称" + i);
            column.setUserId(i);
            column.setCreateTime(new Date());
            column.setUpdateTime(new Date());
            column.setPrice(new BigDecimal(100));
            column.setProportion(new BigDecimal(20));
            columnsList.add(column);
        }
        columnRepository.saveAll(columnsList);
    }

    @Test
    public void testest() {
        Integer seq = categoryRepository.findAllByPCode(CategoryEnums.USER.getCode()).stream().map(Category::getSequence).max(Comparator.naturalOrder()).get();
        System.out.println(seq);
    }

    @Test
    public void createCC() {
        CourseColumnRelation courseColumnRelation;
        for (int i = 0; i < 10; i++) {
            courseColumnRelation = new CourseColumnRelation();
            courseColumnRelation.setColumnId(1);
            courseColumnRelation.setCourseId(i);
            courseColumnRelation.setCreateTime(new Date());
            courseColumnRelation.setUpdateTime(new Date());
            courseColumnRelationRepository.save(courseColumnRelation);
        }

        for (int i = 0; i < 10; i++) {
            courseColumnRelation = new CourseColumnRelation();
            courseColumnRelation.setColumnId(i);
            courseColumnRelation.setCourseId(11);
            courseColumnRelation.setCreateTime(new Date());
            courseColumnRelation.setUpdateTime(new Date());
            courseColumnRelationRepository.save(courseColumnRelation);
        }

    }

    @Test
    public void test() throws Exception {
        List<Category> categoryList = categoryRepository.findAllByPCode(CategoryEnums.COURSE_COVER.getCode());
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(categoryList);
            String purl = categoryList.get(0).getPurl();
            System.out.println(purl);
        }
    }

    @Test
    public void getStringCurrentTime() throws Exception {
        String url = "http://e.vhall.com/api/vhallapi/v2/user/get-child-list";
        List<Integer> weihouUserIdList = Lists.newArrayList();
        List<Integer> ourUserIdList = userRepository.findAllByWeiHouUserIdIsNotNull().stream().map(User::getWeiHouUserId).collect(Collectors.toList());
        Map<String, Object> map = Maps.newHashMap();
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("limit", 500);
        map.put("signed_at", TimeTransUtil.getUnixTimeStamp());
        map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
        JSONObject result = restTemplate.postForObject(url, map, JSONObject.class);
        if (result.getInteger("code") == 200) {
            JSONObject J1 = result.getJSONObject("data");
            J1.remove("total");
            for (int i = 0; i < J1.size(); i++) {
                JSONObject jsonObject = J1.getJSONObject(String.valueOf(i));
                Integer userId = jsonObject.getInteger("user_id");
                weihouUserIdList.add(userId);
            }
        }
        weihouUserIdList.stream().filter(id -> !ourUserIdList.contains(id)).forEach(System.out::println);
    }

    @Test
    public void getDifferenceUser() {
        List<Integer> userIdList = userAuthsRepository.findAllByRoles(null).stream().map(UserAuths::getId).collect(Collectors.toList());
        Integer userId;
        Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_USER.getMessage());
        List<Role> roleList = Lists.newArrayList(role);
        UserAuths userAuths;
        Integer rId = 1;
        for (int i = 0; i < userIdList.size(); i++) {
            userId = userIdList.get(i);
            String url = "insert into maoyao.user_auths_roles(user_auths_id,roles_id) value(" + userId + "," + rId + ")";
            System.out.println(url);
            jdbcTemplate.execute(url);
            /*userAuths = userAuthsRepository.getOne(userId);
            userAuths.setRoles(roleList);
            userAuthsRepository.save(userAuths);*/
        }
    }

    @Test
    public void getInvalidCourse() {
        List<Integer> courseIdList = incomeRepository.findDistinctResourceId();
        Course course;
        List<Integer> list = Lists.newArrayList();
        for (Integer id : courseIdList) {
            course = courseRepository.findFirstById(id);
            if (course == null)
                list.add(id);
        }
        list.forEach(System.out::println);
    }

    @Test
    public void test345() {
        List<Map<String, Object>> list = Lists.newArrayList();
        Map<String, Object> resultMap = Maps.newHashMap();
        resultMap.put("id", "1");
        resultMap.put("createTime", 1535007685203L);
        resultMap.put("dateFrom", 123457);
        resultMap.put("dateThru", 123458);
        list.add(resultMap);
        Map<String, Object> resultMap1 = Maps.newHashMap();
        resultMap1.put("id", "2");
        resultMap1.put("createTime", 1535009137894L);
        resultMap1.put("dateFrom", 123477);
        resultMap1.put("dateThru", 122488);
        list.add(resultMap1);
        Map<String, Object> resultMap2 = Maps.newHashMap();
        resultMap2.put("id", "3");
        resultMap2.put("createTime", 1535009489007L);
        resultMap2.put("dateFrom", 128477);
        resultMap2.put("dateThru", 122480);
        list.add(resultMap2);
        list.stream().sorted(Comparator.comparingLong(map -> (Long) map.get("createTime")));
        list.forEach(System.out::println);
    }

    @Test
    public void getUnionid() throws Exception {
        Map<String, Object> map = new HashMap<>();
        map.put("third_user_id", 60);
        map.put("auth_type", 2);//授权类型,1为验证帐号和密码(目前只通过帐号和密码验证),2为appkey/secretkey验证方式
        map.put("app_key", WH_AppKey);
        map.put("name", "小宇宇");
        map.put("head", "http://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK2ia4icQsmWcBQ92kq5TpibtCocUd6VF1G0YiaDicaibn1SBJzyr7p6v4icKVauiadTIDicgibPkPjticlQuTFw/132");
        String time = TimeTransUtil.getNowTimeStampMs();
        map.put("signed_at", time);
        map.put("sign", SignUtil.generateSign(map, WH_SecretKey));
        JSONObject json = restTemplate.getForEntity("http://e.vhall.com/api/vhallapi/v2/user/update?name={name}&head={head}&third_user_id={third_user_id}&auth_type={auth_type}&app_key={app_key}&signed_at={signed_at}&sign={sign}", JSONObject.class, map).getBody();
        System.out.println(json.getInteger("code"));
    }

    @Test
    public void handlerData() {
       /* Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_USER.getMessage());
        //Role role = roleRepository.findFirstByName(ProgramEnums.ROLE_USER.getMessage());
        List<Role> roleList = Lists.newArrayList(role);
        userAuthsRepository.findAllByIdentityTypeAndRolesIn(3, roleList).forEach(u -> System.out.println(u.getId()));
        //userAuthsRepository.findAllByRolesIn(roleList).forEach(u -> System.out.println(u.getId()));*/
        List<Map<String, Integer>> list = userAuthsRepository.findUserAuthsRole();
        for (int i = 0; i < list.size(); i++) {
            Integer uId = list.get(i).get("user_auths_id");
            Integer rId = list.get(i).get("roles_id");
            String url = "insert into maoyao.user_auths_roles(user_auths_id,roles_id) value(" + uId + "," + rId + ")";
            System.out.println(url);
            jdbcTemplate.execute(url);
            //userAuthsRepository.insert(uId,rId);
        }
        System.out.println(list.size());
    }


    @Test
    public void objectToInteger() {
        Map<String, Object> map = new HashMap<>();
        map.put("test1", 122.122);
        Integer i = null;
        String s1 = map.get("test1").toString();
        int size = s1.indexOf(".");
        if (size == -1) {
            i = (Integer) map.get("test1");
        } else {
            i = Integer.parseInt(s1.substring(0, size));
        }
        System.out.println(i);
    }

    /**
     * 插入艺考名师假数据
     */
    @Test
    public void addArtExam() {
        ArtExam artExam;
        for (int i = 0; i < 10; i++) {
            artExam = new ArtExam();
            artExam.setContext("招生简章内容html" + i);
            artExam.setCreateTime(new Date());
            artExam.setPurl("招生简章图片url" + i);
            artExam.setTitle("招生简章标题" + i);
            artExam.setType(1);
            artExam.setUpdateTime(new Date());
            artExamRepository.save(artExam);
        }
        for (int i = 0; i < 10; i++) {
            artExam = new ArtExam();
            artExam.setContext("艺考名师内容html" + i);
            artExam.setCreateTime(new Date());
            artExam.setPurl("艺考名师图片url" + i);
            artExam.setTitle("艺考名师标题" + i);
            artExam.setType(2);
            artExam.setUpdateTime(new Date());
            artExamRepository.save(artExam);
        }
        for (int i = 0; i < 4; i++) {
            artExam = new ArtExam();
            artExam.setContext("模块内容html" + i);
            artExam.setCreateTime(new Date());
            artExam.setPurl("模块图片url" + i);
            artExam.setTitle("模块标题" + i);
            artExam.setType(3);
            artExam.setUpdateTime(new Date());
            artExamRepository.save(artExam);
        }
    }

    @Test
    public void addCatogory() {
        Category category;
        category = new Category();
        category.setCode(CategoryEnums.USER.getCode());
        category.setName(CategoryEnums.USER.getMessage());
        categoryRepository.save(category);
        category = new Category();
        category.setCode(CategoryEnums.MODULE.getCode());
        category.setName(CategoryEnums.MODULE.getMessage());
        categoryRepository.save(category);
        category = new Category();
        category.setCode(CategoryEnums.VIP.getCode());
        category.setName(CategoryEnums.VIP.getMessage());
        category.setRemark("vip权益介绍");
        categoryRepository.save(category);
        category = new Category();
        category.setCode(CategoryEnums.PROMOTION.getCode());
        category.setName(CategoryEnums.PROMOTION.getMessage());
        category.setRemark("推广说明");
        categoryRepository.save(category);
        for (int i = 1; i < 10; i++) {
            category = new Category();
            category.setCode("00100" + i);
            category.setName("艺术家标签" + i);
            category.setPCode(CategoryEnums.USER.getCode());
            category.setPurl("图片" + i);
            categoryRepository.save(category);
        }
        for (int i = 1; i < 5; i++) {
            category = new Category();
            category.setCode("00200" + i);
            category.setName("模块名称" + i);
            category.setPCode(CategoryEnums.MODULE.getCode());
            categoryRepository.save(category);
        }

        category = new Category();
        category.setCode(CategoryEnums.NULL_VIP.getCode());
        category.setName(CategoryEnums.NULL_VIP.getMessage());
        category.setPCode(CategoryEnums.VIP.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode(CategoryEnums.NORMAL_VIP.getCode());
        category.setName(CategoryEnums.NORMAL_VIP.getMessage());
        category.setPCode(CategoryEnums.VIP.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode(CategoryEnums.SUPER_VIP.getCode());
        category.setName(CategoryEnums.SUPER_VIP.getMessage());
        category.setPCode(CategoryEnums.VIP.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode(CategoryEnums.TRIAL_VIP.getCode());
        category.setName(CategoryEnums.TRIAL_VIP.getMessage());
        category.setPCode(CategoryEnums.VIP.getCode());
        categoryRepository.save(category);

        category = new Category();
        category.setCode("003001001");
        category.setContext1("1");
        category.setContext2("365");
        category.setTimeType(1);
        category.setPCode(CategoryEnums.NORMAL_VIP.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode("003001002");
        category.setContext1("6");
        category.setContext2("258");
        category.setTimeType(2);
        category.setPCode(CategoryEnums.NORMAL_VIP.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode("003001003");
        category.setContext1("1");
        category.setContext2("48");
        category.setTimeType(2);
        category.setPCode(CategoryEnums.NORMAL_VIP.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode("003003001");
        category.setContext1("3");
        category.setContext2("1");
        category.setTimeType(3);
        category.setPCode(CategoryEnums.TRIAL_VIP.getCode());
        categoryRepository.save(category);

        category = new Category();
        category.setCode("003003001");
        category.setContext1("3");
        category.setContext2("365");
        category.setTimeType(3);
        category.setPCode(CategoryEnums.TRIAL_VIP.getCode());
        categoryRepository.save(category);

        category = new Category();
        category.setCode("005001");
        category.setContext1("1");
        category.setContext2("5");
        category.setPCode(CategoryEnums.PROMOTION.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode("005001");
        category.setContext1("5");
        category.setContext2("23");
        category.setPCode(CategoryEnums.PROMOTION.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode("005001");
        category.setContext1("10");
        category.setContext2("40");
        category.setPCode(CategoryEnums.PROMOTION.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode("005001");
        category.setContext1("20");
        category.setContext2("70");
        category.setPCode(CategoryEnums.PROMOTION.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode("005001");
        category.setContext1("50");
        category.setContext2("150");
        category.setPCode(CategoryEnums.PROMOTION.getCode());
        categoryRepository.save(category);
        category = new Category();
        category.setCode(CategoryEnums.PROPORTION.getCode());
        category.setContext1("0.2");
        category.setContext2("0.3");
        category.setName(CategoryEnums.PROPORTION.getMessage());
        categoryRepository.save(category);
        category = new Category();
        category.setCode("006");
        category.setContext1("6");
        category.setName("banner最大数量");
        categoryRepository.save(category);
        category = new Category();
        category.setCode("007");
        category.setContext1("www.taobao.com");
        category.setName("商城链接");
        categoryRepository.save(category);
    }

    @Test
    public void createRole() {
        Role role1 = Role.builder().name("ROOT_USER").createTime(new Date()).updateTime(new Date()).build();
        roleRepository.save(role1);
        Role role2 = Role.builder().name("ROLE_AGENTS").createTime(new Date()).updateTime(new Date()).build();
        roleRepository.save(role2);
        Role role3 = Role.builder().name("ROLE_ADMIN").createTime(new Date()).updateTime(new Date()).build();
        roleRepository.save(role3);
        Role role4 = Role.builder().name("ROLE_ROOT").createTime(new Date()).updateTime(new Date()).build();
        roleRepository.save(role4);
    }

    /**
     * 艺术家分类
     */
   /* @Test
    public void createArtCategory() {
        Category category;
        for (int i = 1; i < 10; i++) {
            category = new Category();
            category.setCode("00100" + i);
            category.setName("艺术家标签" + i);
            category.setPCode(CategoryEnums.USER.getCode());
            category.setPurl("图片" + i);
            categoryRepository.save(category);
        }
        for (int i = 1; i < 5; i++) {
            category = new Category();
            category.setCode("00200" + i);
            category.setName("模块名称" + i);
            category.setPCode(CategoryEnums.MODULE.getCode());
            categoryRepository.save(category);
        }
    }*/
    @Test
    public void testupdate() throws Exception {
        weihouTask.createAndSetDefaultRecord(372242599);
       /*List<String> list=Lists.newArrayList();
       for (int i = list.size() - 1; i >= 0; i--) {
           
       }*/
    }

    @Test
    public void hanlderPing1() throws RateLimitException, APIException, ChannelException, InvalidRequestException, APIConnectionException, AuthenticationException {
        Pingpp.apiKey = "sk_live_nbzDWP4q1GOCuzLC4SfnDyDO";
        Date now = new Date();
        Map<String, Object> chargeParams = Maps.newHashMap();
        chargeParams.put("limit", 100);
        chargeParams.put("app[id]", "app_9iXL44KCyTW9mrbX");
        ChargeCollection chargeCollection = Charge.list(chargeParams);
        chargeCollection.getData().forEach(charge -> {
            Txn txn = OrderConvert.ChargeToTxn(charge);
            txn.setCreateTime(now);
            txn.setUpdateTime(now);
            txnRepository.save(txn);
            System.out.println(charge.getId());
        });
        System.out.println(chargeCollection.getData().size());
    }

    @Test
    public void hanlderPing2() throws RateLimitException, APIException, ChannelException, InvalidRequestException, APIConnectionException, AuthenticationException {
        Pingpp.apiKey = "sk_live_nbzDWP4q1GOCuzLC4SfnDyDO";
        Date now = new Date();
        Map<String, Object> chargeParams = Maps.newHashMap();
        String id = "ch_j1qf54SGKW54X5arnPub1qnL";
        Integer size = 100;
        chargeParams.put("limit", 100);
        chargeParams.put("app[id]", "app_9iXL44KCyTW9mrbX");
        while (size == 100) {
            chargeParams.put("starting_after", id);
            ChargeCollection chargeCollection = Charge.list(chargeParams);
            for (int i = 0; i < chargeCollection.getData().size(); i++) {
                Charge charge = chargeCollection.getData().get(i);
                Txn txn = OrderConvert.ChargeToTxn(charge);
                txn.setCreateTime(now);
                txn.setUpdateTime(now);
                txnRepository.save(txn);
                System.out.println(charge.getId());
                id = charge.getId();
            }
            size = chargeCollection.getData().size();
        }

       /* chargeParams.put("starting_after", "ch_jbDOeTbPyzz9nPWbf1PCiD08");
        ChargeCollection chargeCollection = Charge.list(chargeParams);
        chargeCollection.getData().forEach(charge -> {
            Txn txn = OrderConvert.ChargeToTxn(charge);
            txn.setCreateTime(now);
            txn.setUpdateTime(now);
            txnRepository.save(txn);
            System.out.println(charge.getId());
        });
        System.out.println(chargeCollection.getData().size());*/
    }

    @Test
    public void test234() throws Exception {

        List<Integer> ls=Lists.newArrayList(1,2,3,4,5,6,7);
        ls.forEach(s->{
            System.out.println(1);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(2);
        });
    }


}
