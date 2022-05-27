package com.projectTakeAway.Anima.controller;

import com.projectTakeAway.Anima.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@SuppressWarnings("all")

/**
 * @author CodemyMind
 * @Date 2022/5/18 2:49 PM
 * Description：             文件的上传和下载功能
 */

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {

    @Value("${Anima.path}")
    private String basePath;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping("/upload")
    // 参数名不能随便起，要和发送给浏览器的Form Data中的name相同
    // file是个临时文件，需要转存到指定位置，否则本次请求完成后就会 删除掉此临时文件
    public R<String> upload(MultipartFile file) throws IOException {
        log.info(file.toString());

        // 原始文件名
        String originalFilename = file.getOriginalFilename();

        // 使用UUID重新生成文件名，防止文件名称重复造成覆盖
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String fileName = UUID.randomUUID().toString() + suffix;

        // 创建一个目录对象
        File dir = new File(basePath);
        // 判断当前目录是否存在
        if (!dir.exists()) {
            // 不存在，则需要创建
            dir.mkdirs();
        }

        // 将临时文件转存到指定位置
        file.transferTo(new File(basePath + fileName));

        return R.success(fileName);
    }

    /**
     * 文件下载（准确来说是将上传的图片回显在浏览器上）
     * @param response
     * @param name
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response, String name) throws IOException{
        // 输入流，通过输入流读取文件内容
        FileInputStream fis = new FileInputStream(new File(basePath + name));

        // 输出流，通过输出流将文件写回浏览器，在浏览器里展示图片
        ServletOutputStream ops = response.getOutputStream();

        // 设置响应回去的格式
        response.setContentType("image/jpeg");

        int len = 0;
        byte[] bytes = new byte[1024];
        while ((len = fis.read(bytes)) != -1) {
            ops.write(bytes, 0, len);
            ops.flush();
        }
        // 关闭资源
        fis.close();
        ops.close();
    }
}
