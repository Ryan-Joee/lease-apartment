package com.ryan.lease.web.admin.controller.apartment;


import com.ryan.lease.common.result.Result;
import com.ryan.lease.web.admin.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@Tag(name = "文件管理")
@RequestMapping("/admin/file")
@RestController
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam MultipartFile file) {

        try {
            String url = fileService.upload(file);
            /*
            返回值：将文件保存到Minio后，minio提供的URL
            当新增房间信息时：对于上传的图片信息而言==> 保存到数据库中的是图片的URL
            所以上传图片的接口要返回图片的URL
            */
            return Result.ok(url);
        } catch (Exception e) {
            return Result.fail();
        }

    }

}
