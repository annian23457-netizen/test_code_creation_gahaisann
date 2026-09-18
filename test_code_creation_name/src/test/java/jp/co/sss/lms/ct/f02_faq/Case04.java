package jp.co.sss.lms.ct.f02_faq;

import static jp.co.sss.lms.ct.util.WebDriverUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

import java.time.Duration;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.tethering.model.Accepted;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazonaws.services.gamelift.model.AcceptanceType;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

/**
 * 結合テスト よくある質問機能
 * ケース04
 * @author holy
 */
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("ケース04 よくある質問画面への遷移")
public class Case04 {

	/** 前処理 */
	@BeforeAll
	static void before() {
		createDriver();
	}

	/** 後処理 */
	@AfterAll
	static void after() {
		closeDriver();
	}

	@Test
	@Order(1)
	@DisplayName("テスト01 トップページURLでアクセス")
	void test01() {
		// TODO ここに追加
//		URLを取得
		webDriver.get("http://localhost:8080/lms/");
		//		タイトルが一致かどうか確認
		assertEquals("ログイン | LMS", webDriver.getTitle());
		//		スクショとる
		getEvidence(new Object() {
		}, "テスト04.1");
	}
		
	

	@Test
	@Order(2)
	@DisplayName("テスト02 初回ログイン済みの受講生ユーザーでログイン")
	void test02() {
		// TODO ここに追加
//		ログインIDを取得
		WebElement loginIdInput = webDriver.findElement(By.id("loginId"));
		//　　　ログインIDをクリック
		loginIdInput.click();
		//　　　ログインIDの値を入力
		loginIdInput.sendKeys("StudentAA01");

		//		パスワードを取得
		WebElement passwordInput = webDriver.findElement(By.id("password"));
		//		パスワードをクリック
		passwordInput.clear();
		//		パスワードの値を入力
		passwordInput.sendKeys("Aa123456789");
		//ボタンをクリック
		webDriver.findElement(By.className("btn-primary")).click();
		//		エラーメッセージが一致かどうか確認
		assertEquals("コース詳細 | LMS",webDriver.getTitle());
		//		スクショとる
		getEvidence(new Object() {
		}, "テスト04.2");
	}
	

	@Test
	@Order(3)
	@DisplayName("テスト03 上部メニューの「ヘルプ」リンクからヘルプ画面に遷移")
	void test03() {
	    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
	   
	    WebElement menuToggle = webDriver.findElement(By.className("dropdown-toggle"));
	    menuToggle.click();

	    
	    WebElement helpLink = wait.until(
	        ExpectedConditions.elementToBeClickable(By.linkText("ヘルプ"))
	    );


	    helpLink.click();
	    assertEquals("ヘルプ | LMS", webDriver.getTitle());

	    getEvidence(new Object() {
	    }, "テスト04.3");
	}
	
	
	@Test
	@Order(4)
	@DisplayName("テスト04 「よくある質問」リンクからよくある質問画面を別タブに開く")
	void test04() {
	    WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

	    // 1. ヘルプ画面が表示されていることを確認
	    wait.until(ExpectedConditions.titleIs("ヘルプ | LMS"));

	    // 2. ★クリック前のウィンドウ（元のタブ）の識別子を取得しておく
	    String originalWindow = webDriver.getWindowHandle();

	    // 3. 「よくある質問」リンクを取得してクリック
	    WebElement question = wait.until(
	        ExpectedConditions.elementToBeClickable(By.linkText("よくある質問"))
	    );
	    question.click();

//	    // 4. ★新しいタブが開くまで待機し、操作対象を新しいタブへ切り替える
//	    wait.until(ExpectedConditions.numberOfWindowsToBe(2));
//	    for (String windowHandle : webDriver.getWindowHandles()) {
//	        if (!originalWindow.contentEquals(windowHandle)) {
//	            webDriver.switchTo().window(windowHandle);
//	            break;
//	        }
//	    }

	    // 5. 切り替え後の新しいタブでタイトルを確認
	    wait.until(ExpectedConditions.titleIs("よくある質問 | LMS"));
	    assertEquals("よくある質問 | LMS", webDriver.getTitle());

	    // 6. 新しいタブのスクリーンショットを取得
	    getEvidence(new Object() {
	    }, "テスト04.4");
	}
}
