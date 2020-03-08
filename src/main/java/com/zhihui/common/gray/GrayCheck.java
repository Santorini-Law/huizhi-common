package com.zhihui.common.gray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhihui.common.gray.api.IGrayCheck;
import com.zhihui.common.gray.model.GrayFeatureDetail;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author LDZ
 * @date 2020-02-28 15:37
 */
public class GrayCheck implements IGrayCheck {

    /**
     * 配置文件路径
     */
    private static final String CONFIG_FILE_NAME_PREFIX = "/com/zhihui/common/gray/";

    /**
     * 配置文件名称
     */
    private String configFileName = "grayConfig.json";

    /**
     * 默认线程池
     */
    private static final String DEFAULT_THREAD_POOL_NAME = "DEFAULT";

    /**
     * 配置
     */
    private Map<String, GrayFeatureDetail> configMap = new HashMap<>();

    /**
     * 初始化
     */
    public void init() {
        try (InputStream inputStream = GrayCheck.class.getResourceAsStream(CONFIG_FILE_NAME_PREFIX + configFileName);) {
            List<GrayFeatureDetail> grayFeatureDetails = new Gson().fromJson(new InputStreamReader(inputStream, UTF_8.name()), new TypeToken<List<GrayFeatureDetail>>() {
            }.getType());

            for (GrayFeatureDetail config : grayFeatureDetails) {
                configMap.put(config.getGrayName(), config);
            }
        } catch (IOException e) {
            throw new RuntimeException("初始化灰度配置失败", e);
        }
    }


    public static void addGrayFeatureConfig(String s) {
        String filePath = GrayCheck.class.getClassLoader().getResource("grayConfig.json").getPath();
        BufferedWriter sensitiveWordsWriter = null;
        try {
            sensitiveWordsWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath)));
            sensitiveWordsWriter.write(s);
            sensitiveWordsWriter.flush();
            sensitiveWordsWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        GrayFeatureDetail grayFeatureDetail = new GrayFeatureDetail();
        grayFeatureDetail.setGrayId(200L);
        grayFeatureDetail.setGrayName("测试");
        grayFeatureDetail.setGraySwitch(1);
        grayFeatureDetail.setWhiteList(Arrays.asList(3385L, 2134L, 5430L));
        grayFeatureDetail.setBlackList(Arrays.asList(33851L, 21341L, 54301L));
        grayFeatureDetail.setGrayIntervalCell(20);
        grayFeatureDetail.setGrayIntervalFloor(80);
        grayFeatureDetail.setCreateTime(LocalDateTime.now());

        List<GrayFeatureDetail> grayFeatureDetails = new ArrayList<>();
        grayFeatureDetails.add(grayFeatureDetail);

        String s = new Gson().toJson(grayFeatureDetails);
        addGrayFeatureConfig(s);
        System.out.println(s);
    }


    public void setConfigFileName(String configFileName) {
        this.configFileName = configFileName;
    }
}
