package com.ray.monitor.filesyn;

import com.google.common.base.Splitter;
import com.ray.monitor.core.MonitorCache;
import com.ray.monitor.core.repository.TempRepository;
import com.ray.monitor.exception.FileReadException;
import com.ray.monitor.model.SensorInfo;
import com.ray.monitor.model.TempInfo;
import com.ray.monitor.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
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

    private static final int BUFFER_SIZE = 1024;
    private static final String CONTENT_PATTERN =
            "\\w+\\n+\\w+\\n+\\w+\\n+(\\d{4}/\\d{2}/\\d{2} \\d{1,2}:\\d{1,2}:\\d{1,2}){1}\\n+(\\d+,\\w*,\\d+(.\\d)*\\n*){1,}";

    private static Pattern pattern = Pattern.compile(CONTENT_PATTERN,Pattern.DOTALL);

    @Autowired
    private MonitorCache monitorCache;

    @Autowired
    private TempRepository tempRepository;

   // @Scheduled(cron = "0/10 * * * * ?")
    public void synData(){
        log.info("=====================deal with txt file.");
        boolean hasError = false;
        try {
            String result = readToString("D:/date.txt");
            List<String> stringList = Splitter.on("\n").splitToList(result);

            long areaId = Long.parseLong(stringList.get(0));
            String mpName = stringList.get(1);
            String terminalName = stringList.get(2);
            Date date = DateUtil.getDate(stringList.get(3));

            List<TempInfo> tempInfoList = new ArrayList<>();
            for(int i=4; i<stringList.size(); i++){
                List<String> tempList = Splitter.on(",").splitToList(stringList.get(i));
                SensorInfo sensorInfo = monitorCache.getSnesor(areaId,mpName,terminalName,tempList.get(0));

                TempInfo tempInfo = new TempInfo();
                tempInfo.setTemperature(Double.valueOf(tempList.get(2)));
                tempInfo.setState(tempList.get(1));
                tempInfo.setGenTime(date);
                tempInfo.setSensorInfo(sensorInfo);

                tempInfoList.add(tempInfo);
            }
            tempRepository.save(tempInfoList);
        } catch (FileReadException e) {
            log.error("Data txt problem:",e);
            hasError = true;
        } catch (NumberFormatException|ParseException e) {
            log.error("Error occurs when read date:",e);
            hasError = true;
        } catch (ExecutionException e) {
            log.error("Error occurs when deal with date:",e);
            hasError = true;
        } finally {
            //todo
        }


    }

    public static String readToString(String fileName) throws FileReadException {
        String encoding = "UTF-8";
        File file = new File(fileName);
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

}
