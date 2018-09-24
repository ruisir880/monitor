package com.ray.monitor.filesyn;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.repository.TempRepository;
import com.ray.monitor.core.service.MailService;
import com.ray.monitor.exception.FileReadException;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

/**
 * Created by rui on 2018/9/1.
 */
@Component
public class QuartzService {
    private static Logger log = LoggerFactory.getLogger(QuartzService.class);

    private static String PROPERTIES_NAME  = "path.properties";

    private static final int BUFFER_SIZE = 1024;
    private static final String CONTENT_PATTERN =
            "\\w+\\n+\\w+\\n+\\w+\\n+(\\d{4}/\\d{2}/\\d{2} \\d{1,2}:\\d{1,2}:\\d{1,2}){1}\\n+(\\d+,\\w*,\\d+(.\\d)*\\n*){1,}";

    private static Pattern pattern = Pattern.compile(CONTENT_PATTERN,Pattern.DOTALL);

   // @Value("${syn.file.path}")
    private static String FILE_PATH ;
   // @Value("${syn.file.failed.path}")
    private static String FAILED_PATH ;
    //@Value("${syn.file.successed.path}")
    private static String SUCCESSED_PATH ;

    @Autowired
    private Environment env;

    @Autowired
    private MonitorCache monitorCache;

    @Autowired
    private TempRepository tempRepository;

    @Autowired
    private MailService mailService;
    @PostConstruct
    public void init(){
        FILE_PATH = env.getProperty("syn.file.path");
        FAILED_PATH = env.getProperty("syn.file.failed.path");
        SUCCESSED_PATH = env.getProperty("syn.file.successed.path");
    }

    @Scheduled(cron = "0/10 * * * * ?")
    public void synData(){
        File rootDir = new File(FILE_PATH);
        if(!rootDir.exists()){
            log.warn("file not exist:" + FILE_PATH);
            return;
        }
        for(File file : rootDir.listFiles()){
            if(file.isDirectory()){
                continue;
            }
            dealWithData(file);
        }

    }

    private void dealWithData(File file){
        boolean hasError = false;
        log.info("=====================deal with txt file:"+file.getPath());
        long areaId = 0;
        String mpName = null;
        String terminalName = null;
        String sensorName = null;
        try {
            String result = readToString(file);
            if(!pattern.matcher(result).matches()){
                throw new FileReadException(String.format("File %s content is not legal.",file.getPath()));
            }
            List<String> stringList = Splitter.on("\n").splitToList(result);

            areaId = Long.parseLong(stringList.get(0));
            mpName = stringList.get(1);
            terminalName = stringList.get(2);
            Date date = DateUtil.getDate(stringList.get(3));

            List<TempInfo> tempInfoList = new ArrayList<>();
            List<String> warnMsgList = new ArrayList<>();
            for(int i=4; i<stringList.size(); i++){
                List<String> tempList = Splitter.on(",").splitToList(stringList.get(i));
                sensorName = tempList.get(0);
                SensorInfo sensorInfo = monitorCache.getSnesor(areaId,mpName,terminalName,sensorName);

                TempInfo tempInfo = new TempInfo();
                tempInfo.setTemperature(Double.valueOf(tempList.get(2)));
                tempInfo.setState(tempList.get(1));
                tempInfo.setGenTime(date);
                tempInfo.setSensorInfo(sensorInfo);

                tempInfoList.add(tempInfo);
                if(tempInfo.getTemperature() > sensorInfo.getThresholdValue()) {
                    warnMsgList.add(String.format("传感器：%s,温度%s,阈值：%s;", sensorInfo.getSensorId(), tempInfo.getTemperature(), sensorInfo.getThresholdValue()));
                }
            }
            sendEmail(areaId,mpName,warnMsgList);
            tempRepository.save(tempInfoList);
        } catch (FileReadException e) {
            log.error("Data txt problem:",e);
            hasError = true;
        } catch (NumberFormatException|ParseException e) {
            log.error("Error occurs when read date:",e);
            hasError = true;
        } catch (ExecutionException e) {
            log.error(String.format("Can not get sensor, areaId:%s, siteId:%s, terminalId:%s ,sensorId:%s",
                    areaId,mpName,terminalName, sensorName),e);
            hasError = true;
        }catch (Exception e) {
            log.error("upexpected error:",e);
            hasError = true;
        }finally {
            String targetPathStr = hasError? FAILED_PATH : SUCCESSED_PATH;
            try {
                Path targetPath = Paths.get(targetPathStr,file.getName());
                Path sourcePath = Paths.get(file.getPath());
                Files.deleteIfExists(targetPath);
                Files.move(sourcePath,targetPath);
            } catch (IOException e) {
                log.error("Error occurs when move file:"+ file.getPath(),e);
            }
        }
    }


    private String readToString(File file) throws FileReadException {
        String encoding = "UTF-8";
        byte[] filecontent = new byte[BUFFER_SIZE];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
            String result = new String(filecontent, encoding).trim();
           // return result.replaceAll("\r","\n").replaceAll("\n+","\n");
            return result.replaceAll("(\r*\n+)+","\n");
        } catch (Exception e) {
            throw new FileReadException(e);
        }
    }

    private void sendEmail(long areaId, String mpName, List<String> warnMsgList){
        try {
            List<String> emailList = monitorCache.getEmailList(areaId,mpName);
            if(emailList.size()>0) {
                mailService.sendSimpleEmail(emailList.toArray(new String[emailList.size()]), Joiner.on("\n").join(warnMsgList));
            }
        }catch (Exception e){
            log.warn("===================================Send email failed==================================");
            log.warn("Error occurs when send email",e);
        }
    }

}
