
package com.pgh.kaleidoscope.resource.endpoint;

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.pgh.kaleidoscope.core.oss.QiniuTemplate;
import com.pgh.kaleidoscope.core.oss.model.KaleidoscopeFile;
import com.pgh.kaleidoscope.core.oss.model.OssFile;
import com.pgh.kaleidoscope.core.tool.api.CommonResult;
import com.pgh.kaleidoscope.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 对象存储端点
 *
 * @author Chill
 */
@RestController
@AllArgsConstructor
@RequestMapping("/oss/endpoint")
@Api(value = "对象存储端点", tags = "对象存储端点")
public class OssEndpoint {

	private QiniuTemplate qiniuTemplate;

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return Bucket
	 */
	@SneakyThrows
	@PostMapping("/make-bucket")
	public CommonResult makeBucket(@RequestParam String bucketName) {
		qiniuTemplate.makeBucket(bucketName);
		return CommonResult.success("创建成功");
	}

	/**
	 * 创建存储桶
	 *
	 * @param bucketName 存储桶名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-bucket")
	public CommonResult removeBucket(@RequestParam String bucketName) {
		qiniuTemplate.removeBucket(bucketName);
		return CommonResult.success("删除成功");
	}

	/**
	 * 拷贝文件
	 *
	 * @param fileName       存储桶对象名称
	 * @param destBucketName 目标存储桶名称
	 * @param destFileName   目标存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/copy-file")
	public CommonResult copyFile(@RequestParam String fileName, @RequestParam String destBucketName, String destFileName) {
		qiniuTemplate.copyFile(fileName, destBucketName, destFileName);
		return CommonResult.success("操作成功");
	}

	/**
	 * 获取文件信息
	 *
	 * @param fileName 存储桶对象名称
	 * @return InputStream
	 */
	@SneakyThrows
	@GetMapping("/stat-file")
	public CommonResult<OssFile> statFile(@RequestParam String fileName) {
		return CommonResult.data(qiniuTemplate.statFile(fileName));
	}

	/**
	 * 获取文件相对路径
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-path")
	public CommonResult<String> filePath(@RequestParam String fileName) {
		return CommonResult.data(qiniuTemplate.filePath(fileName));
	}


	/**
	 * 获取文件外链
	 *
	 * @param fileName 存储桶对象名称
	 * @return String
	 */
	@SneakyThrows
	@GetMapping("/file-link")
	public CommonResult<String> fileLink(@RequestParam String fileName) {
		return CommonResult.data(qiniuTemplate.fileLink(fileName));
	}

	/**
	 * 上传文件
	 *
	 * @param file 文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file")
	public CommonResult<KaleidoscopeFile> putFile(@RequestParam MultipartFile file) {
		KaleidoscopeFile kaleidoscopeFile = qiniuTemplate.putFile(file.getOriginalFilename(), file.getInputStream());
		return CommonResult.data(kaleidoscopeFile);
	}

	/**
	 * 上传文件
	 *
	 * @param fileName 存储桶对象名称
	 * @param file     文件
	 * @return ObjectStat
	 */
	@SneakyThrows
	@PostMapping("/put-file-by-name")
	public CommonResult<KaleidoscopeFile> putFile(@RequestParam String fileName, @RequestParam MultipartFile file) {
		KaleidoscopeFile kaleidoscopeFile = qiniuTemplate.putFile(fileName, file.getInputStream());
		return CommonResult.data(kaleidoscopeFile);
	}

	/**
	 * 删除文件
	 *
	 * @param fileName 存储桶对象名称
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-file")
	public CommonResult removeFile(@RequestParam String fileName) {
		qiniuTemplate.removeFile(fileName);
		return CommonResult.success("操作成功");
	}

	/**
	 * 批量删除文件
	 *
	 * @param fileNames 存储桶对象名称集合
	 * @return R
	 */
	@SneakyThrows
	@PostMapping("/remove-files")
	public CommonResult removeFiles(@RequestParam String fileNames) {
		qiniuTemplate.removeFiles(Func.toStrList(fileNames));
		return CommonResult.success("操作成功");
	}

}
