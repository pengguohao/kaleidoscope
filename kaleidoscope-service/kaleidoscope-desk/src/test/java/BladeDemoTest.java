import org.junit.Test;
import org.junit.runner.RunWith;
import com.pgh.kaleidoscope.core.test.KaleidoscopeBootTest;
import com.pgh.kaleidoscope.core.test.KaleidoscopeSpringRunner;
import com.pgh.kaleidoscope.desk.DeskApplication;
import com.pgh.kaleidoscope.desk.service.INoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Blade单元测试
 *
 * @author Chill
 */
@RunWith(KaleidoscopeSpringRunner.class)
@SpringBootTest(classes = DeskApplication.class)
@KaleidoscopeBootTest(appName = "blade-desk", profile = "test", enableLoader = true)
public class BladeDemoTest {

	@Autowired
	private INoticeService noticeService;

	@Test
	public void contextLoads() {
		int count = noticeService.count();
		System.out.println("notice数量：[" + count + "] 个");
	}

}
